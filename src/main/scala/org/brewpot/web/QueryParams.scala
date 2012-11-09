package org.brewpot.web

import util.control.Exception._

case class QueryParams(qp: Map[String, Seq[String]]) {

  def get(key: String) = qp.get(key)

  def getOrElse(key: String, orElse: Seq[String]) = qp.getOrElse(key, orElse)

  def foldLeft[B](acc: B)(f: (B, (String, Seq[String])) => B) = qp.foldLeft(acc)(f)

  def apply(key: String) = qp.apply(key)

  def mapFirst[B](key: String, f: (String) => Option[B]) = qp(key).headOption.flatMap(f)

  def first(s: String) = qp(s).headOption

  def firstInt(key: String) = mapFirst(key, d => allCatch.opt(d.toInt))

  def firstLong(key: String) = mapFirst(key, d => allCatch.opt(d.toLong))

  def firstDouble(key: String) = mapFirst(key, d => allCatch.opt(d.toDouble))

  def asServletRequestParameters: Map[String, Array[String]] = qp.mapValues(_.toArray[String])


}

