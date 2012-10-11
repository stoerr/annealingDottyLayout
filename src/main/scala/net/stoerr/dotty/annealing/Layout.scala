package net.stoerr.dotty.annealing

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

case class Point(x: Int, y: Int) {
  def distance(p: Point): Double = math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y))
}

case class LayoutSettings(var xdist: Double = 2, var ydist: Double = 1.5, var aspectratio: Double = math.sqrt(2), var freespace: Double = 1.3)

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class Layout(graph: UndirectedGraph, settings: LayoutSettings) {

  val gridsize = math.sqrt(graph.size * settings.freespace).toInt

  val points = new ArrayBuffer[Point] {
    for (i <- 0 until gridsize; j <- 0 until gridsize) this += Point(i, j)
  }

  val positions = new mutable.HashMap[String, Point]
  val contents = new mutable.HashMap[Point, String]

  private def scale(p: Point): Point =
    Point((settings.xdist / gridsize * (p.x + 0.5)).toInt, (settings.ydist / gridsize * (p.y + 0.5)).toInt)

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

