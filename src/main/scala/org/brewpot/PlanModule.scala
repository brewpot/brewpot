package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan with BrewPlan {
  def intent = page orElse brew orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._
import org.brewpot.Calculations._

trait PagePlan extends CommonDirectives with ViewServices {

  def page = Intent {
    case "/" => for { _ <- getHtml } yield greetResponse
    case "/calc/abv" => for { _ <- getHtml } yield Ok
    case "/calc/og" => for { _ <- getHtml } yield calcOgResponse
    case "/calc/attenuation" => for { _ <- getHtml } yield Ok
  }
}

trait BrewPlan extends CommonDirectives with ViewServices {
  def brew = Intent {
    case "/ingredients/fermentables" => for { _ <- getHtml } yield grainsResponse
  }
}

trait FormulaPlan extends CommonDirectives with CalcServices {
  def formula = Intent {
    case "/calc/abv"          => for { o <- postJson[AbvInput] } yield abv(o)
    case "/calc/og"           => for { o <- postJson[OgInput] } yield og(o)
    case "/calc/attenuation"  => for { o <- postJson[AbvInput] } yield attenuation(o)
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

  val getNegotiate = for {
    _ <- GET
    _ <- Accepts.Json | Accepts.Html
  } yield ()

}

