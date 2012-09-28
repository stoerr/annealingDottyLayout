package net.stoerr.dotty.annealing

import swing._
import java.awt.Color

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 28.09.12
 */
object DisplayGraph extends SimpleSwingApplication {
  val ymax = 1000
  val xmax = 1414

  def top = new MainFrame {
    title = "Display the graph"
    minimumSize = new Dimension(xmax+100,ymax+100)
    contents = new Component {
      override def paintComponent(g: Graphics2D) = {
        super.paintComponent(g)
        g.setColor(Color.black)
        g.drawLine(0,0,xmax, ymax)
      }
    }
  }
}
