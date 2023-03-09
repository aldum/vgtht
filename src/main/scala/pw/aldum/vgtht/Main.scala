package pw.aldum
package vgtht

import scala.util.{ Try, Failure, Success }

@main def Main(args: String*): Unit =
  Try {
    val Seq(in, out, profile) = args.take(3)
    ArgParse.validate(in, out, profile).map(Process.apply)
  } match
    case Failure(_: MatchError) =>
      println(
        """|Usage: <input> <output> <profile>
           |
           |  input:  path to CSV files
           | output:  path for output
           |profile:  profile to use for connecting to cloud
        """.stripMargin
      )
    case Failure(a: AppError) => println(a.toString)
    case Failure(_)           => println("Unexpected error")
    case Success(value)       => println("Done.")
