package net.stoerr.dotty.annealing

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Undirected Graph with Strings as nodes.
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class UndirectedGraph {

  val neighborMap = new mutable.HashMap[String, mutable.ArrayBuffer[String]]
  val nodes = new mutable.HashSet[String]

  def size: Int = nodes.size

  def neighbors(node: String): ArrayBuffer[String] = neighborMap(node)

  def addEdge(n1: String, n2: String) {
    giveNeighbors(n1) += n2
    giveNeighbors(n2) += n1
    nodes += n1
    nodes += n2
  }

  private def giveNeighbors(n: String): mutable.ArrayBuffer[String] =
    neighborMap.getOrElseUpdate(n, new mutable.ArrayBuffer[String]())

}

object UndirectedGraph {
  def makeLine(count: Int): UndirectedGraph = {
    val graph = new UndirectedGraph
    var last = "0"
    for (i <- 1 to count) {
      var next = "" + i
      graph.addEdge(last, next)
      last = next
    }
    graph
  }
}
