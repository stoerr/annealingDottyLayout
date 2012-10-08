package net.stoerr.dotty.annealing

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
    val xmax = 8427 // points
    val ymax = 5959 // points
    val graph = new UndirectedGraph
    dottygraph.edges foreach (edge => graph.addEdge(edge.from, edge.to))
    val layout = new AnnealingLayout(graph, xmax, ymax) // includes layout computation
    // val layoutednodes = graph.nodes map (dottygraph.nodes....  add pos="x,y" attribute to every node.
    // val result = DottyGraph(dottygraph.directed, dottygraph.name, layoutednodes, dottygraph.edges)
  }
}
