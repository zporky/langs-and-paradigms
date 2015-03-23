import statemachine.scala.StateMachine._

import scala.io.Source

object Main {

  def main(args : Array[String]): Unit = {
    for(ln <- Source.stdin.getLines()) println(process(ln))
  }

}
