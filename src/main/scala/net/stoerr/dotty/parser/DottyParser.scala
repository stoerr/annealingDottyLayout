package net.stoerr.dotty.parser

import scala.util.parsing.combinator.JavaTokenParsers


/**
 * Parses a simple fragment of the <a href="http://www.graphviz.org/content/dot-language">Dotty File</a> format. We
 * do not allow subgraphs, strict graphs, multiple edges per line, comments, omission of semicolons,
 * common graph / node / edge features and some more small features. Valid are for example:
 * <code> graph "hello world" { hello [label="Foo", shape=box]; hello -- world [color = blue]; } <code>
 * or <code> digraph {  size="1,1"; a -> b; c -> d; } </code>
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 04.10.12
 */
trait DottyParser extends JavaTokenParsers {

  def id: Parser[String] = ident | (stringLiteral ^^ (s => s.substring(1, s.length - 1)))

  def dottyfile: Parser[DottyGraph] = ("graph" ^^^ (false) | "digraph" ^^^ (true)) ~ (id.? <~ "{") ~
    graphdefline.* <~ "}" ^^ {
    case graphtype ~ id ~ lines => DottyGraph(graphtype, id,
      lines filter (_.isInstanceOf[NodeDef]) map (_.asInstanceOf[NodeDef]),
      lines filter (_.isInstanceOf[EdgeDef]) map (_.asInstanceOf[EdgeDef]))
  }

  def graphdefline: Parser[GraphContentDef] = (edgedef | nodedef)

  def nodedef: Parser[NodeDef] = id ~ attributes.? <~ ";".? ^^ {
    case id ~ attr => NodeDef(id, attr.getOrElse(Map()))
  }

  def edgedef: Parser[EdgeDef] = id ~ (("--" | "->") ~> id) ~ attributes.? <~ ";".? ^^ {
    case from ~ to ~ attr => EdgeDef(from, to, attr.getOrElse(Map()))
  }

  def attributes: Parser[Map[String, String]] = "[" ~> attrasignments <~ "]"

  def attrasignments: Parser[Map[String, String]] = id ~ ("=" ~> id) ~ (",".? ~> attrasignments.?) ^^ {
    case id ~ value ~ others => Map(id -> value) ++ others.getOrElse(Map())
  }

}
