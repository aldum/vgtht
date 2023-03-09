package pw.aldum
package vgtht

import java.nio.file.Path

enum AppError extends Exception:
  case FileError(e: String)
  case ProfileError(e: String)
  case ParseError(e: String)
  case DataError(e: String)

enum Profile:
  case Default
  case Named(p: String)

case class Args(
    in: Path,
    out: Path,
    profile: Profile,
  )
