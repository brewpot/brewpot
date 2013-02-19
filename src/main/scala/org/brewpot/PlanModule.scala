package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan{
  def intent = index orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._
import org.brewpot.Calculations.AbvCalc

trait PagePlan extends CommonDirectives with ViewServices {

  def index = Intent {
    case "/" => for { _ <- getHtml } yield greet
  }
}

trait FormulaPlan extends CommonDirectives with CalcServices {
  def formula = Intent {
    case "/calc/abv" => for { o <- postJson[AbvCalc] } yield abv(o)
  }
}

trait CommonDirectives {
  def postJson[A : Parseable] = for {
    _ <- POST
    _ <- contentType("application/json")
    j <- commit(Body.string _)
    c <- getOrElse(Parseable[A].parseBody(j), BadRequest)
  } yield (c)

  val getHtml = for {
    _ <- GET
    _ <- Accepts.Html
  } yield ()

}

