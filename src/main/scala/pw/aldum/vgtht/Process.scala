package pw.aldum
package vgtht

import java.io.PrintWriter
import java.nio.file.{ Files, Path }
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.io.Source
import scala.jdk.CollectionConverters.*

object Process:
  extension [A](a: A) def |>[B](f: (A) => B): B = f(a)

  def listFiles(path: Path): Iterator[Path] =
    Files
      .walk(path)
      .nn
      .iterator
      .nn
      .asScala
      .filter(_.toString.endsWith(".csv"))

  def readFile(path: Path): ParsedData =
    val lines = Source
      .fromFile(path.toFile.nn)
      .getLines
    ParsedData(lines)

  def readInput(path: Path): ParsedData =
    listFiles(path)
      .map(readFile)
      .foldLeft[ParsedData](Map.empty)(ParsedData.merge)

  def pick(d: ParsedData): Map[Int, Int] =
    for
      (k, m) <- d
      opt    <- m.find((v, occ) => occ % 2 == 1).map(_._1)
    // TODO validate if there's a result
    yield (k, opt)

  def writeFile(out: Path)(content: String) =
    val file = Files.createFile(out).nn.toFile
    new PrintWriter(file):
      write(content)
      close

  def writeResult(out: Path)(data: ParsedData): Unit =
    val ts =
      DateTimeFormatter
        .ofPattern("MM-dd_HH-mm-ss")
        .nn
        .format(LocalDateTime.now())
    val filename = s"result_$ts"
    val filePath = out.resolve(filename).nn

    writeFile(filePath) {
      pick(data)
        .map((k, v) =>
          println(s"$k $v")
          s"$k,$v"
        )
        .mkString(s"\n")
    }

  def apply(args: Args) =
    readInput(args.in) |> writeResult(args.out)
