package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan{
  def intent = index orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._
import xml.NodeSeq


trait PagePlan extends CommonDirectives
  with ServiceModule
  with HtmlWrapperModule {

  def index = Intent {
    case "/" =>
      for {
        _ <- getHtml
      } yield Ok ~> HtmlContent ~> Html5(serve[Any, NodeSeq](Nil).get)
  }
}

trait FormulaPlan extends CommonDirectives {
  def formula = Intent {
    case "/calc/abv" =>
      for {
        o <- postJson[AbvCalc]
      } yield (o)
  }
}

trait CommonDirectives extends ServiceModule {
  def postJson[A : BodyParser] = for {
    _ <- POST
    _ <- contentType("application/json")
    j <- commit(Body.string _)
    c <- getOrElse(BodyParser[A].parseBody(j), BadRequest)
  } yield (ResponseString(serve[A, Double](c).get.toString))

  val getHtml = for {
    _ <- GET
    _ <- Accepts.Html
  } yield ()

}

