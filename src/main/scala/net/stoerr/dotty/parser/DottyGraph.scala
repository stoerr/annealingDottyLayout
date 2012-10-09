package net.stoerr.dotty.parser

/**
 * Represents a <a href="http://www.graphviz.org/content/dot-language">Dotty File</a>
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 04.10.12
 */
case class DottyGraph(directed: Boolean, name: Option[String], nodes: List[NodeDef] = List(), edges: List[EdgeDef] = List()) {
  override def toString() = (if (directed) "digraph " else "graph ") + name.getOrElse("") + " {\n" +
    (nodes mkString ("")) + (edges map (_.toString(directed)) mkString ("")) + "}\n"
}

sealed trait GraphContentDef {
  def attributesAsString(attrib: Map[String, String]) = if (attrib.isEmpty) ""
  else {
    attrib map {
      case (x, y) => x + "=\"" + y + "\""
    } mkString(" [", ", ", "]")
  }
}

case class NodeDef(name: String, attrib: Map[String, String] = Map()) extends GraphContentDef {
  override def toString() = "  \"" + name + '"' + attributesAsString(attrib) + ";\n"
}

case class EdgeDef(from: String, to: String, attrib: Map[String, String] = Map()) extends GraphContentDef {
  def toString(directed: Boolean) = "  \"" + from + '"' + (if (directed) " -> " else " -- ") + '"' + to + '"' + attributesAsString(attrib) + ";\n"
}
