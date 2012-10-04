package net.stoerr.dotty.parser

/**
 * Represents a <a href="http://www.graphviz.org/content/dot-language">Dotty File</a>
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 04.10.12
 */
case class DottyGraph(strict: Boolean, directed: Boolean, name: Option[String]) {
}
