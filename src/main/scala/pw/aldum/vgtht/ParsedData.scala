package pw.aldum
package vgtht

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
      missing ++ present.map {
        case (k, v2) =>
          m1.get(k) match
            case None     => k -> v2
            case Some(v1) => k -> combine(v1, v2)
      }
    mapMerge(d, other)((m1, m2) => mapMerge(m1, m2)((v, o) => ???))
