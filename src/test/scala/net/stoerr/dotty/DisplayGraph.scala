package net.stoerr.dotty

import java.awt.{Graphics2D, Dimension}
import net.stoerr.dotty.annealing.{LayoutSettings, AnnealingLayout, UndirectedGraph}
import scala.swing.{Component, MainFrame, SimpleSwingApplication}

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 28.09.12
 */
object DisplayGraph extends SimpleSwingApplication {
  val ymax = 1000
  val xmax = 1414

  val gridsize = 10
  val graph = UndirectedGraph.makeSquare(gridsize)
  val layout = new AnnealingLayout(graph, new LayoutSettings(xdist = xmax / gridsize, ydist = ymax / gridsize))

  def top: MainFrame = new MainFrame {
    title = "Display the graph"
    minimumSize = new Dimension(xmax + 50, ymax + 50)
    contents = new Component {
      override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)
        for (node <- graph.nodes) {
          val nodep = layout.position(node)
          for (neighbor <- graph.neighbors(node)) {
            val neighborp = layout.position(neighbor)
            g.drawLine(nodep.x, nodep.y, neighborp.x, neighborp.y)
          }
        }
      }
    }
  }
}
