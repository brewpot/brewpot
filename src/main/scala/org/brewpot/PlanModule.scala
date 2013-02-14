package org.brewpot

import directives.Directives._
import unfiltered.request.{Accepts, GET, Seg}
import unfiltered.response.{HtmlContent, Html5, Ok}

trait PlanModule extends PagePlan {
  def intent = index
}

trait PagePlan extends ServiceModule with HtmlWrapperModule {

    def index = Intent {
      case "/" =>
        for {
          _ <- GetHtml
        } yield Ok ~> HtmlContent ~> Html5(greet)
    }

    val GetHtml = for {
      _ <- GET
      _ <- Accepts.Html
    } yield ()

}