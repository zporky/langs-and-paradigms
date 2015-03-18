import scala.io.Source
import statemachine.scala.StateMachine._

/**
 * Application entry point.
 */
object Main {

  var input : String = null

  def main(args : Array[String]): Unit = {
    for(ln <- Source.stdin.getLines()) println(process(ln))
  }

}
