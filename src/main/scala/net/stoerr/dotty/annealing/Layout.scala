package net.stoerr.dotty.annealing

import scala.collection.mutable
import scala.util.Random
import scala.collection.mutable.ArrayBuffer

case class Point(x: Int, y: Int) {
  def distance(p: Point): Double = math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y))
}

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class Layout(graph: UndirectedGraph, xmax: Int, ymax: Int) {

  val gridsize = math.sqrt(graph.size * 1.5).toInt

  val points = new ArrayBuffer[Point] {
    for (i <- 0 until gridsize; j <- 0 until gridsize) this += Point(i, j)
  }

  val positions = new mutable.HashMap[String, Point]
  val contents = new mutable.HashMap[Point, String]

  private def scale(p: Point): Point =
    Point((xmax * 1.0 / gridsize * (p.x + 0.5)).toInt, (ymax * 1.0 / gridsize * (p.y + 0.5)).toInt)

  def position(node: String): Point = scale(positions(node))

  protected val rnd = new Random(42)

  protected def randompoint() = points(rnd.nextInt(points.size)) // Point(rnd.nextInt(gridsize), rnd.nextInt(gridsize))

  def put(node: String, p: Point) {
    positions += node -> p
    contents += p -> node
  }

  def move(node: String, from: Point, to: Point) {
    contents remove from
    put(node, to)
  }

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

