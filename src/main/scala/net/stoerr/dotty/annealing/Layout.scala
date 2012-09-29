package net.stoerr.dotty.annealing

import scala.collection.mutable
import scala.util.Random
import scala.collection.mutable.ArrayBuffer

case class Point(x: Int, y: Int)

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class Layout(graph: UndirectedGraph, xmax: Int, ymax: Int) {

  val gridsize = math.sqrt(graph.size * 1.5).toInt

  val points = new ArrayBuffer[Point]
  for (i <- 0 to gridsize; j <- 0 to gridsize) points += Point(i, j)

  val positions = new mutable.HashMap[String, Point]
  val contents = new mutable.HashMap[Point, String]

  private def scale(p: Point): Point =
    Point((xmax * 1.0 / gridsize * (p.x + 0.5)).toInt, (ymax * 1.0 / gridsize * (p.y + 0.5)).toInt)

  def position(node: String): Point = scale(positions.get(node).get)

  private val rnd = new Random

  private def randompoint() = Point(rnd.nextInt(gridsize), rnd.nextInt(gridsize))

  private def put(node: String, p: Point) {
    positions += node -> p
    contents += p -> node
  }

  {
    for (node <- graph.nodes) {
      var isput = false
      while (!isput) {
        val p = randompoint()
        contents.get(p) match {
          case None =>
            isput = true
            put(node, p)
          case Some(_) =>
        }
      }
    }
  }
}

