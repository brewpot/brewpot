package org.brewpot

import xml.NodeSeq
import unfiltered.request.Accepts
import unfiltered.response.{JsonContent, HtmlContent}
import org.brewpot.Bootstrap._
import org.json4s.JsonAST.JValue
import org.json4s.native.JsonMethods._
import unfiltered.response.Html5
import unfiltered.response.ResponseString
import directives._,Directives._

trait Negotiable[A] {
  def negotiateHtml(f: (A) => NodeSeq) = for {
    _ <- Accepts.Html
  } yield (a: A) => HtmlContent ~> Html5(wrap(f(a)))

  def negotiateJson(f: (A) => JValue) = for {
    _ <- Accepts.Json
  } yield (a: A) => JsonContent ~> ResponseString(compact(render(f(a))))
}