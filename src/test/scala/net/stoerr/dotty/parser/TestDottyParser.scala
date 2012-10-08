package net.stoerr.dotty.parser

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.parsing.input.CharSequenceReader

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 04.10.12
 */
class TestDottyParser extends DottyParser with FlatSpec with ShouldMatchers {

  private def parsing[T](s: String)(implicit p: Parser[T]): T = {
    //wrap the parser in the phrase parse to make sure all input is consumed
    val phraseParser = phrase(p)
    //we need to wrap the string in a reader so our parser can digest it
    val input = new CharSequenceReader(s)
    phraseParser(input) match {
      case Success(t, _) => t
      case NoSuccess(msg, _) => throw new IllegalArgumentException(
        "Could not parse '" + s + "': " + msg)
    }
  }

  private def assertFail[T](input: String)(implicit p: Parser[T]) {
    evaluating(parsing(input)(p)) should produce[IllegalArgumentException]
  }

  "The nodedef parser" should "parse node definitions" in {
    implicit val parser = nodedef
    parsing("nodename;") should equal(NodeDef("nodename"))
    parsing("\"string id\";") should equal(NodeDef("string id"))
    parsing("node[bla=blu];") should equal(NodeDef("node", Map("bla" -> "blu")))
    parsing("\"node\"[bla=\"blu\"];") should equal(NodeDef("node", Map("bla" -> "blu")))
  }

  "The edgedef parser" should "parse edge definitions" in {
    implicit val parser = edgedef
    parsing("a -> b;") should equal(EdgeDef("a", "b"))
    parsing("\"a\" -- \"b\" [bla=blu, blef=\"hu\"]") should
      equal(EdgeDef("a", "b", Map("bla" -> "blu", "blef" -> "hu")))
  }

  "The graph parser" should "yield an empty graph for empty definitions" in {
    implicit val parser = dottyfile
    parsing("graph{}") should equal(DottyGraph(directed = false, name = None))
    parsing("digraph{}") should equal(DottyGraph(directed = true, name = None))
    parsing("digraph bla {}") should equal(DottyGraph(directed = true, name = Some("bla")))
  }

  it should "put all details of a file together" in {
    implicit val parser = dottyfile
    var file =
      """
        |digraph test {
        |x[bla=blu];
        |a->"b";
        |c->d [ha=hu, x="y"]
        |}
      """.stripMargin
    // println(parsing(file).toString())
    parsing(file).toString().replaceAll("\\s+", " ").trim should equal(
      """digraph test {
        |  x [bla="blu"];
        |  a -> b;
        |  c -> d [ha="hu", x="y"];
        |}
      """.stripMargin.replaceAll("\\s+", " ").trim)
  }

}
