package net.stoerr.dotty.parser

import scala.util.parsing.combinator.JavaTokenParsers

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 04.10.12
 */
trait DottyParser extends JavaTokenParsers {

  val dottyfile: Parser[DottyGraph] = "strict".? ~ ("graph" | "digraph") ~ ident.? <~ "{" <~ "}" ^^ {
    case x ~ y ~ id => DottyGraph(x.isDefined, "digraph" == y, id)
  }

}
