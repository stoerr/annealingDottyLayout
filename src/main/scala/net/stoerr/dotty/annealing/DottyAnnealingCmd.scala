package net.stoerr.dotty.annealing

import java.io.{FileOutputStream, PrintWriter}
import net.stoerr.dotty.parser.DottyParser
import scala.io.Source
import scala.util.parsing.input.CharSequenceReader

/**
 * Command line interface for the simulated annealing layout for dotty files. Usage:
 * ... inputfile outputfile
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 08.10.12
 */
object DottyAnnealingCmd extends DottyParser {
  def main(args: Array[String]) {
    val infile = args(0)
    val outfile = args(1)
    val input = Source.fromFile(infile).mkString
    val dottygraph = dottyfile(new CharSequenceReader(input)) match {
      case Success(t, _) => t
      case NoSuccess(msg, charseq) => throw new IllegalArgumentException(
        "Could not parse '" + input + "': " + msg)
    }
    // ??? What is the actual scaling factor? How can we wrap the labels?
    // Probably there should also be more rows than columns.
    val xmax = 8427 / 8 // 8427 points
    val ymax = 5959 / 8 // 5959 points
    val graph = LayoutXDotty.undirectedGraphFromDottyGraph(dottygraph)
    val layout = new AnnealingLayout(graph, xmax, ymax) with LayoutXDotty // includes layout computation
    val result = layout.extendDottyGraphWithPositions(dottygraph)
    val stream = new PrintWriter(new FileOutputStream(outfile))
    stream.println(result.toString())
    stream.close()
    // println(result.toString())
  }
}
