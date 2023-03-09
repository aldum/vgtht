package pw.aldum
package vgtht

import java.io.PrintWriter
import java.nio.file.{ Files, Path }
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.io.Source
import scala.jdk.CollectionConverters.*

object Process:
  extension [A](a: A) def |>[B](f: (A) => B): B = f(a)

  def readInput(path: Path): ParsedData =
    Files
      .walk(path)
      .nn
      .iterator
      .nn
      .asScala
      .filter(_.endsWith(".csv"))
      .map { p =>
        val lines = Source
          .fromFile(p.toFile.nn)
          .getLines
        ParsedData(lines)
      }
      .fold[ParsedData](Map.empty)(ParsedData.merge)

  def pick(d: ParsedData): Map[Int, Int] =
    for
      (k, m) <- d
      opt    <- m.find((v, occ) => occ % 2 == 1).map(_._1)
    // TODO validate if there's a result
    yield (k, opt)

  def writeResult(out: Path)(data: ParsedData): Unit =
    def writeFile(content: String) =
      val ts =
        DateTimeFormatter
          .ofPattern("MM-dd_HH:mm:ss")
          .nn
          .format(LocalDate.now())
      val filename = s"result_$ts"
      val file     = Files.createFile(out.resolve(filename)).nn.toFile
      new PrintWriter(file):
        write(content)
        close

    writeFile {
      pick(data)
        .map((k, v) => s"$k,$v")
        .mkString(s"\n")
    }

  def apply(args: Args) =
    readInput(args.in) |> writeResult(args.out)
