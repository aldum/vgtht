package pw.aldum
package vgtht

final class ParsedDataTest extends TestSuite with TestData:
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

    expect(
      ParsedData.apply(file1.lines.nn) === goodMap,
      ParsedData.apply(file2.lines.nn) === goodMap,
      ParsedData.apply(file3.lines.nn) === r2,
    )

  }
