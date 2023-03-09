package pw.aldum.vgtht

import AppError.ParseError

import java.nio.file.Path
import java.nio.file.Files

object CSVParse:
  def parse(input: Path): Either[ParseError, ParsedData] =
    ???
