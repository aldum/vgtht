package pw.aldum.vgtht

import java.nio.file.{ Files, Path }

import scala.io.Source
import scala.jdk.CollectionConverters.*

object Process:
  def readInput(path: Path) =
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

  def apply(args: Args) =
    readInput(args.in)
