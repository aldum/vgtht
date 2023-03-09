package pw.aldum
package vgtht

import scala.util.Try

//                Map[key, Map[value, occ]]
type ParsedData = Map[Int, Map[Int, Int]]

extension (d: ParsedData)
  def merge(other: ParsedData): ParsedData =
    def mapMerge[K, V](
        m1: Map[K, V],
        m2: Map[K, V],
      )(
        combine: (V, V) => V
      ) =
      val (present, missing) = m2.partition((k, _) => m1.contains(k))
      missing ++ m1.map {
        case (k, v2) =>
          m2.get(k) match
            case None     => k -> v2
            case Some(v1) => k -> combine(v1, v2)
      }

    mapMerge(d, other)((m1, m2) => mapMerge(m1, m2)(_ + _))

object ParsedData:
  private def parseInt(s: String): Option[Int] =
    val sanitized = s.trim.nn
    if sanitized.isEmpty
    then Some(0)
    else Try(sanitized.toInt).toOption

  private def parseTuple(v1: String, v2: String = ""): Option[(Int, Int)] =
    (parseInt(v1), parseInt(v2)) match
      case (Some(a), Some(b)) => Some(a, b)
      case _                  => None

  def parseLine(line: String): Option[(Int, Int)] =
    line match
      case s"$k,$v" => parseTuple(k, v)
      case t if t.contains('\t') =>
        val tokens = t.split('\t')
        tokens.length match
          case 2 =>
            val Array(k, v) = tokens
            parseTuple(k, v)
          case 1 =>
            val Array(k) = tokens
            parseTuple(k)
          case _ => None

      case _ => None

  def updateOccurrences(
      accMap: ParsedData,
      cur: Option[(Int, Int)],
    ) =
    cur match
      case None => accMap
      case Some((k, v)) =>
        accMap.merge(Map(k -> Map(v -> 1)))

  def apply(lines: Iterator[String]): ParsedData =
    lines
      .map(parseLine)
      .foldLeft[ParsedData](Map.empty)(updateOccurrences)

  def apply(stream: java.util.stream.Stream[String]): ParsedData =
    import scala.jdk.CollectionConverters.*
    apply(stream.iterator.nn.asScala)
