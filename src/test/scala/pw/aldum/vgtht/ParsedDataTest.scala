package pw.aldum
package vgtht

final class ParsedDataTest extends TestSuite:
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

  test("merge") {
    // assert((goodMap.merge(badMap)) == r1)
    // assert((goodMap.merge(m2)) == r2)
    expect(
      goodMap.merge(badMap) === r1,
      goodMap.merge(m2) === r2,
    )
  }

  test("line parser") {
    forAll { (i1: Int, i2: Int) =>
      expect(
        // commas
        ParsedData.parseLine(s"$i1,$i2") === Some((i1, i2)),
        ParsedData.parseLine(s"$i1 ,$i2") === Some((i1, i2)),
        ParsedData.parseLine(s"$i1, $i2") === Some((i1, i2)),
        ParsedData.parseLine(s",$i2") === Some((0, i2)),
        ParsedData.parseLine(s" ,$i2") === Some((0, i2)),
        ParsedData.parseLine(s"$i1,") === Some((i1, 0)),
        ParsedData.parseLine(s"$i1, ") === Some((i1, 0)),
        // tabs
        ParsedData.parseLine(s"$i1\t$i2") === Some((i1, i2)),
        ParsedData.parseLine(s"$i1 \t$i2") === Some((i1, i2)),
        ParsedData.parseLine(s"$i1\t $i2") === Some((i1, i2)),
        ParsedData.parseLine(s"\t$i2") === Some((0, i2)),
        ParsedData.parseLine(s" \t$i2") === Some((0, i2)),
        ParsedData.parseLine(s"$i1\t") === Some((i1, 0)),
        ParsedData.parseLine(s"$i1\t ") === Some((i1, 0)),
        // bad
        ParsedData.parseLine("") === None,
        ParsedData.parseLine(";") === None,
        ParsedData.parseLine("1") === None,
        ParsedData.parseLine("a") === None,
        ParsedData.parseLine("a,b") === None,
        ParsedData.parseLine(s"a\tb") === None,
      )
    }
  }

  test("string iterator parser") {
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

    expect(
      ParsedData.apply(file1.lines.nn) === goodMap,
      ParsedData.apply(file2.lines.nn) === goodMap,
      ParsedData.apply(file3.lines.nn) === r2,
    )

  }
