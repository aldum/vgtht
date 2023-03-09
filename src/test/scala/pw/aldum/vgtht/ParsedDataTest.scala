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

  test("merge1") {
    assert((goodMap.merge(badMap)) == r1)
    // expect((goodMap.merge(badMap)) === r1)
  }
  test("merge2") {
    assert((goodMap.merge(m2)) == r2)
    // expect(goodMap.merge(m2) === r2)
  }
