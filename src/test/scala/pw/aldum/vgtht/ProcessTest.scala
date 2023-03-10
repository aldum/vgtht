package pw.aldum
package vgtht

import java.nio.file.Files

final class ProcessTest extends TestSuite with TestData:
  val goodPicked = Map(1 -> 2, 2 -> 4)
  val badPicked  = Map.empty
  val m2Picked   = Map(3 -> 3)
  val r1Picked   = goodPicked
  val r2Picked   = goodPicked ++ m2Picked

  val tmp = Files.createTempDirectory("files").nn
  val in  = tmp.resolve("in").nn
  val out = tmp.resolve("out").nn

  Files.createDirectory(in)
  Files.createDirectory(out)
  val f1 = in.resolve("file1.csv").nn
  val f2 = in.resolve("t.csv").nn
  Process.writeFile(f1)(file1)
  Process.writeFile(f2)(s"a,b\n5,5")

  test("listFiles") {
    expect(
      Process.listFiles(in).toSet === Set(f1, f2)
    )
  }
  test("readFile") {
    expect(
      Process.readFile(f1) === goodMap,
      Process.readFile(f2) === Map(5 -> Map(5 -> 1)),
    )
  }
  test("readInput") {
    expect(
      Process.readInput(in) === goodMap.merge(Process.readFile(f2))
    )
  }
  test("pick") {
    // assert(Process.pick(goodMap) === goodPicked)
    expect(
      Process.pick(goodMap) === goodPicked,
      Process.pick(badMap) === badPicked,
      Process.pick(m2) === m2Picked,
      Process.pick(r1) === r1Picked,
      Process.pick(r2) === r2Picked,
    )
  }
