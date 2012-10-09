package net.stoerr.dotty.transformer

import java.io.{FileOutputStream, OutputStreamWriter}
import net.stoerr.dotty.parser.{EdgeDef, NodeDef, DottyGraph, DottyParser}
import scala.collection.mutable
import scala.io.Source
import scala.util.parsing.input.CharSequenceReader

/**
 * Removes names from a dotty file such that we can publish it without disclosing
 * too much information about the project it is from.
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 08.10.12
 */
object DottyAnonymizer extends DottyParser {

  val randomchars = "-llanfairpwllgwyngyllgogerychwyrndrobwllantisiliogogogoch"

  def main(args: Array[String]) {
    val dottyfilecontent = Source.fromFile(args(0)).mkString
    val dottygraph = dottyfile(new CharSequenceReader(dottyfilecontent)) match {
      case Success(t, _) => t
      case NoSuccess(msg, _) => throw new IllegalArgumentException(
        "Could not parse '" + dottyfilecontent + "': " + msg)
    }
    val nodes = dottygraph.edges.map(_.from).toSet ++ dottygraph.edges.map(_.to).toSet
    val nodemap = new mutable.HashMap[String, String]()
    var counter = 1
    for (node <- nodes) {
      nodemap += node -> ("n" + counter + randomchars.substring(0, node.length - 3))
      counter += 1
    }
    val outgraph = DottyGraph(dottygraph.directed, dottygraph.name, dottygraph.nodes.map(nodedef => NodeDef(nodemap(nodedef.name), nodedef.attrib)),
      dottygraph.edges.map(edgedef => EdgeDef(nodemap(edgedef.from), nodemap(edgedef.to), edgedef.attrib)))
    val out = new OutputStreamWriter(new FileOutputStream(args(1)))
    out.write(outgraph.toString())
    out.close()
    println("anonymized " + args(0) + " to " + args(1))
  }

}
