package net.stoerr.dotty.annealing

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

case class Point(x: Int, y: Int) {
  def distance(p: Point): Double = math.sqrt((p.x - x) * (p.x - x) + (p.y - y) * (p.y - y))
}

/** Settings for the Layout process. aspectratio is width to height of the graph; if the width should be smaller than the height use values < 1. */
case class LayoutSettings(var xdist: Double = 200, var ydist: Double = 100, var aspectratio: Double = math.sqrt(2), var freespace: Double = 2)

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 29.09.12
 */
class Layout(graph: UndirectedGraph, settings: LayoutSettings) {

  val xpoints = math.round(math.sqrt(graph.size * settings.freespace) * settings.ydist / settings.xdist * settings.aspectratio).toInt
  val ypoints = math.round(math.sqrt(graph.size * settings.freespace) * settings.xdist / settings.ydist / settings.aspectratio).toInt

  val points = new ArrayBuffer[Point] {
    for (i <- 0 until xpoints; j <- 0 until ypoints) this += Point(i, j)
  }

  val positions = new mutable.HashMap[String, Point]
  val contents = new mutable.HashMap[Point, String]

  private def scale(p: Point): (Double, Double) = ((settings.xdist * (p.x + 0.5)), (settings.ydist * (p.y + 0.5)))

  def position(node: String): (Double, Double) = scale(positions(node))

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

