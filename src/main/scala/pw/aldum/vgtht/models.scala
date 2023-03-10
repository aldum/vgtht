package pw.aldum
package vgtht

import java.nio.file.Path

enum AppError extends Exception:
  case FileError(e: String)
  case ProfileError(e: String)
  case ParseError(e: String)
  case DataError(e: String)

  override def toString(): String =
    this match
      case FileError(e)    => e.toString
      case ProfileError(e) => e.toString
      case ParseError(e)   => e.toString
      case DataError(e)    => e.toString

enum Profile:
  case Default
  case Named(p: String)

case class Args(
    in: Path,
    out: Path,
    profile: Profile,
  )
