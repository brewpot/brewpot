package org.brewpot

import org.json4s.native.JsonParser._
import scala.Some
import jsonpicklers.{Failure, Success}
import org.brewpot.Calculations.AbvCalc

trait Parseable[A] {
  def parseBody(a: String): Option[A]
  def unapply(a: String): Option[A] = parseBody(a)
}

object Parseable {
  def apply[A](implicit parser: Parseable[A]) = parser

  implicit object AbvCalcParser extends Parseable[AbvCalc] {
    def parseBody(json: String): Option[AbvCalc] =
      parseOpt(json).flatMap { p =>
        AbvCalc.json.unpickle(p) match {
          case Success(v, _) => Some(v)
          case Failure(m, _) => None
        }
      }
  }
}

