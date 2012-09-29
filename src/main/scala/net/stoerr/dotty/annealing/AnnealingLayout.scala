package net.stoerr.dotty.annealing

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class AnnealingLayout(graph: UndirectedGraph, xmax: Int, ymax: Int) extends Layout(graph, xmax, ymax) {

  /** Distance of the node from its neighbors when it is at realposition */
  def neighborSumDistance(node: String, realposition: Point): Double =
    (graph.neighbors(node) map (positions(_)) map (realposition distance _) fold 0.0)(_ + _)

  def improvement(node: String, oldpos: Point, newpos: Point): Double =
    neighborSumDistance(node, oldpos) - neighborSumDistance(node, newpos)

  var worseningSwitchProbability = 0.0

  def takeit(improvement: Double): Boolean = if (improvement > 0) true
  else rnd.nextDouble() < worseningSwitchProbability

  for (i <- 0 to 5000000) {
    val p1 = randompoint()
    val p2 = randompoint()
    (contents.get(p1), contents.get(p2)) match {
      case (Some(n1), Some(n2)) =>
        if (takeit(improvement(n1, p1, p2) + improvement(n2, p2, p1))) {
          put(n1, p2)
          put(n2, p1)
        }
      case (Some(n1), None) =>
        if (takeit(improvement(n1, p1, p2))) move(n1, p1, p2)
      case (None, Some(n2)) =>
        if (takeit(improvement(n2, p2, p1))) move(n2, p2, p1)
      case (None, None) =>
    }
  }

}
