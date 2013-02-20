package org.brewpot

import org.json4s.native.JsonParser._
import scala.Some
import jsonpicklers.{Failure, Success}
import org.brewpot.Calculations._

trait Parseable[A] {
  def parseBody(a: String): Option[A]
  def unapply(a: String): Option[A] = parseBody(a)
}

object Parseable {
  def apply[A](implicit parser: Parseable[A]) = parser

  implicit object AbvInputParser extends Parseable[AbvInput] {
    def parseBody(json: String): Option[AbvInput] =
      parseOpt(json).flatMap { p =>
        AbvInput.json.unpickle(p) match {
          case Success(v, _) => Some(v)
          case Failure(m, _) => None
        }
      }
  }

  implicit object OgInputParser extends Parseable[OgInput] {
    def parseBody(json: String): Option[OgInput] =
      parseOpt(json).flatMap { p =>
        OgInput.json.unpickle(p) match {
          case Success(v, _) => Some(v)
          case Failure(m, l) => println(l + " " + m); None
        }
      }
  }
}

