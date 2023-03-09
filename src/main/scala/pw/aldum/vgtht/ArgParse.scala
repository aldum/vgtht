package pw.aldum.vgtht

import java.nio.file.Path
import java.nio.file.Files

object ArgParse:
  def validate(
      inRaw: String,
      outRaw: String,
      profileRaw: String,
    ): Either[Seq[AppError], Args] =
    val in = Path.of(inRaw).nn
    val out = Path.of(outRaw).nn
    val inExists = Files.isDirectory(in)
    val outExists = Files.isDirectory(out)
    val profile = profileRaw match
      case "default" => Profile.Default
      case p => Profile.Named(p) // TODO check if profile exists

    if inExists && outExists
    then Right(Args(in, out, profile))
    else
      Left(
        Seq(
          // TODO
        )
      )
