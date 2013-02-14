package org.brewpot

import unfiltered.filter.Plan
import unfiltered.response._
import unfiltered.request._

import directives._, Directives._

import Cake.MainHandler._

object Routes extends Plan {

  def intent = Intent{
    case "/" =>
      for {
        _ <- GET
        _ <- Accepts.Html
      } yield Ok ~> HtmlContent ~> Html5(handle)
  }

}
