package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan{
  def intent = index orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._
import org.brewpot.Calculations.AbvCalc

trait PagePlan extends CommonDirectives {

  def index = Intent {
    case "/" =>
      for {
        _ <- getHtml
      } yield (ViewService.greet)
  }
}

trait FormulaPlan extends CommonDirectives {
  def formula = Intent {
    case "/calc/abv" =>
      for {
        o <- postJson[AbvCalc]
      } yield (Services.abv(o))
  }
}

trait CommonDirectives {
  def postJson[A : BodyParser] = for {
    _ <- POST
    _ <- contentType("application/json")
    j <- commit(Body.string _)
    c <- getOrElse(BodyParser[A].parseBody(j), BadRequest)
  } yield (c)

  val getHtml = for {
    _ <- GET
    _ <- Accepts.Html
  } yield ()

}

