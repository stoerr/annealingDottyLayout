package net.stoerr.dotty.annealing

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class AnnealingLayout(graph: UndirectedGraph, xmax: Int, ymax: Int) extends Layout(graph, xmax, ymax) {

  /** Distance of the node from its neighbors when it is at realposition except neighbors at ignoredposition */
  def neighborSumDistance(node: String, realposition: Point, ignoredposition: Point): Double =
    (graph.neighbors(node) map (positions(_)) filter (ignoredposition != _) map (realposition distance _) fold 0.0)(_ + _)

  def improvement(node: String, oldpos: Point, newpos: Point): Double =
    neighborSumDistance(node, oldpos, newpos) - neighborSumDistance(node, newpos, oldpos)

  var worseningSwitchProbability = 0.0

  def takeit(improvement: Double): Boolean = if (improvement > 0) true
  else rnd.nextDouble() < worseningSwitchProbability

  val runseconds = 10

  {
    var swapsdone = true
    val step = math.pow(10.0 * graph.size * graph.size, -1.0 / runseconds)
    worseningSwitchProbability = step
    for (i <- 1 to runseconds if swapsdone) {
      println(worseningSwitchProbability)
      swapsdone = runOneSecond()
      worseningSwitchProbability *= step
    }
  }


  def runOneSecond(): Boolean = {
    var swaps = 0
    val begin = System.currentTimeMillis()
    while (System.currentTimeMillis() - begin < 1000) {
      val p1 = randompoint()
      val p2 = randompoint()
      (contents.get(p1), contents.get(p2)) match {
        case (Some(n1), Some(n2)) =>
          if (n1 != n2 && takeit(improvement(n1, p1, p2) + improvement(n2, p2, p1))) {
            put(n1, p2)
            put(n2, p1)
            swaps += 1
          }
        case (Some(n1), None) =>
          if (takeit(improvement(n1, p1, p2))) {
            move(n1, p1, p2)
            swaps += 1
          }
        case (None, Some(n2)) =>
          if (takeit(improvement(n2, p2, p1))) {
            move(n2, p2, p1)
            swaps += 1
          }
        case (None, None) =>
      }
    }
    println("Swaps: " + swaps)
    swaps != 0
  }

}
