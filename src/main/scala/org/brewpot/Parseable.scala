package org.brewpot

import org.json4s.native.JsonParser._
import scala.Some
import jsonpicklers.{JsonObject, Failure, Success}
import org.brewpot.Calculations._

trait Parseable[A] {
  def parseBody(a: String): Option[A]
  def unapply(a: String): Option[A] = parseBody(a)
}

object Parseable {
  def apply[A](implicit parser: Parseable[A]) = parser

  implicit object AbvInputParser extends InputParser[AbvInput](AbvInput.json)

  implicit object OgInputParser extends InputParser[OgInput](OgInput.json)

  class InputParser[A](j:JsonObject[A]) extends Parseable[A] {
    def parseBody(json: String): Option[A] =
      parseOpt(json).flatMap { p =>
        j.unpickle(p) match {
          case Success(v, _) => Some(v)
          case Failure(m, l) => None
        }
      }
  }
}