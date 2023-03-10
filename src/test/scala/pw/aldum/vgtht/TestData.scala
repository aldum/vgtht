package pw.aldum
package vgtht

trait TestData:
  val goodMap: ParsedData = Map(
    1 -> Map(2 -> 1, 3 -> 2),
    2 -> Map(4 -> 3),
  )
  val badMap: ParsedData = Map(
    2 -> Map(4 -> 2)
  )
  val m2: ParsedData = Map(
    3 -> Map(3 -> 1)
  )

  val r1 = Map(
    1 -> Map(2 -> 1, 3 -> 2),
    2 -> Map(4 -> 5),
  )
  val r2 = Map(
    1 -> Map(2 -> 1, 3 -> 2),
    2 -> Map(4 -> 3),
    3 -> Map(3 -> 1),
  )
  val file1 =
    """|1,2
      |1,3
      |1,3
      |2,4
      |2,4
      |2,4
      """.stripMargin
  val file2 =
    s"""|1 \t2
        |1\t3
        |1,3
        |2,4
        |2,4
        |2,4
      """.stripMargin
  val file3 =
    s"""|1,2
        |1,3
        |2,4
        |3,3
        |2,4
        |1,3
        |2,4
      """.stripMargin
