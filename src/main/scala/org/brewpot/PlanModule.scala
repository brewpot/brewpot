package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan{
  def intent = index orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._


trait PagePlan extends CommonDirectives
  with ServiceModule
  with HtmlWrapperModule {

  def index = Intent {
    case "/" =>
      for {
        _ <- getHtml
      } yield Ok ~> HtmlContent ~> Html5(greet)
  }
}

trait FormulaPlan extends CommonDirectives {
  def formula = Intent {
    case "/formula/calc/abv" =>
      for {
        o <- postJson[AbvCalc]
      } yield (ResponseString(o.toString))
  }
}

trait CommonDirectives {
  def postJson[A : Parser] = for {
    _ <- POST
    _ <- contentType("application/json")
    j <- commit(Body.string _)
    c <- getOrElse(Parser[A].parse(j), BadRequest)
  } yield (c)

  val getHtml = for {
    _ <- GET
    _ <- Accepts.Html
  } yield ()

}

