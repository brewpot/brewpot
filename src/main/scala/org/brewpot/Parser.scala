package org.brewpot

trait Parser[A] {
  def parse(a: String): Option[A]
  def unapply(a: String): Option[A] = parse(a)
}

object Parser {
  def apply[A](implicit parser: Parser[A]) = parser
}
