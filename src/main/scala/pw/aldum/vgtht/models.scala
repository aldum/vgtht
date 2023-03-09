package pw.aldum
package vgtht

enum AppError extends Exception:
  case FileError(e: String)
  case ProfileError(e: String)
  case ParseError(e: String)
  case DataError(e: String)

import java.nio.file.Path

enum Profile:
  case Default
  case Named(p: String)

case class Args(
    in: Path,
    out: Path,
    profile: Profile,
  )
