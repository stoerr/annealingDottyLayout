package net.stoerr.dotty.annealing

import net.stoerr.dotty.parser.{NodeDef, DottyGraph}

/**
 * Functionality to convert DottyGraph from and to Layout
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 10.10.12
 */
trait LayoutXDotty extends Layout {

  private def posattr(name: String) = {
    val (x, y) = position(name)
    "pos" -> (x + "," + y)
  }

  private def additionalAttributes = List(NodeDef("graph", Map("splines" -> "true")))

  def extendDottyGraphWithPositions(graph: DottyGraph): DottyGraph = {
    val existingnodedefs = graph.nodes map {
      node => NodeDef(node.name, node.attrib + posattr(node.name))
    }
    val newnodedefs = (positions.keySet -- graph.nodes.map(_.name)) map {
      name => NodeDef(name, Map(posattr(name)))
    }
    DottyGraph(graph.directed, graph.name, existingnodedefs ++ newnodedefs ++ additionalAttributes, graph.edges)
  }

}

object LayoutXDotty {

  def undirectedGraphFromDottyGraph(dottygraph: DottyGraph): UndirectedGraph = {
    val graph = new UndirectedGraph
    dottygraph.edges foreach (edge => graph.addEdge(edge.from, edge.to))
    graph
  }

}