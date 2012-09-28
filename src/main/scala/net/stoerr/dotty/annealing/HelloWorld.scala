package net.stoerr.dotty.annealing
import swing._
import java.awt.Color
import java.awt.Dimension

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 28.09.12
 */
object HelloWorld extends SimpleSwingApplication {
  val ymax = 1000
  val xmax = 1414

  def top = new MainFrame {
    title = "Hello, World!"
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