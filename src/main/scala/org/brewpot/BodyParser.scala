package org.brewpot

trait BodyParser[A] {
  def parseBody(a: String): Option[A]
  def unapply(a: String): Option[A] = parseBody(a)
}

object BodyParser {
  def apply[A](implicit parser: BodyParser[A]) = parser
}
