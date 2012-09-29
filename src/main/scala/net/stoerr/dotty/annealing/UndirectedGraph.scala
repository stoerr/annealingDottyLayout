package net.stoerr.dotty.annealing

import scala.collection.mutable

/**
 * Undirected Graph with Strings as nodes.
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class UndirectedGraph {

  val neighborMap = new mutable.HashMap[String, mutable.ArrayBuffer[String]]

  def addEdge(n1 : String, n2 : String) : Unit = {
    giveNeighbors(n1) += n2
    giveNeighbors(n2) += n1
  }

  private def giveNeighbors(n : String) : mutable.ArrayBuffer[String] = {
    neighborMap.get(n) match {
      case Some(neighbors) => neighbors
      case None => {
        val neighbors = new mutable.ArrayBuffer[String]()
        neighborMap.put(n, neighbors)
        neighbors
      }
    }
  }

}
