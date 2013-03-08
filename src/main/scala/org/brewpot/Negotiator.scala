package org.brewpot

import xml.NodeSeq
import unfiltered.request.Accepts
import unfiltered.response._
import org.brewpot.Bootstrap._
import org.json4s.JsonAST.JValue
import org.json4s.native.JsonMethods._
import directives._,Directives._
import unfiltered.response.Html5
import unfiltered.response.ResponseString

trait Negotiator[A] extends Negotiable[A] {
  def negotiateHtml(f: (A) => NodeSeq) = for {
    _ <- Accepts.Html
  } yield (a: A) => HtmlContent ~> Html5(wrap(f(a)))

  def negotiateJson(f: (A) => JValue) = for {
    _ <- Accepts.Json
  } yield (a: A) => JsonContent ~> ResponseString(compact(render(f(a))))
}

trait Negotiable[A] {
  def html: Directive[Any, Any, A => ResponseFunction[Any]]
  def json: Directive[Any, Any, A => ResponseFunction[Any]]
  def all = html | json
}
