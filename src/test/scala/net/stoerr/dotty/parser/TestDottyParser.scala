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

  "The graph parser " should "yield an empty graph for empty" in {
    implicit val parser = dottyfile
    parsing("graph{}") should equal(DottyGraph(false, false, None))
  }

}
