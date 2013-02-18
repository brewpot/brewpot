package org.brewpot

case class AbvCalc(fg: Double, og: Double)

object AbvCalc {
  implicit object AbvCalcParser extends Parser[AbvCalc] {
    def parse(a: String): Option[AbvCalc] = None
  }
}