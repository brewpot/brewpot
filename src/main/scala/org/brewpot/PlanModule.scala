package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan with BrewPlan {
  def intent = page orElse ingredients orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._
import org.brewpot.Calculations._
import org.brewpot.model.Grain
import xml.NodeSeq
import org.json4s.JsonAST.JValue
import org.json4s.JsonAST.JNothing

trait PagePlan extends CommonDirectives with ViewServices {

  def page = Intent {
    case "/"                  => for { _ <- htmlGet } yield greetResponse
    case "/calc/abv"          => for { _ <- htmlGet } yield Ok
    case "/calc/og"           => for { _ <- htmlGet } yield calcOgResponse
    case "/calc/attenuation"  => for { _ <- htmlGet } yield Ok
  }
}

trait BrewPlan extends CommonDirectives with ViewServices with CalcServices {

  type Grains = Seq[Grain]

  def ingredients = Intent {
    case "/ingredients/fermentables" => for {
      x <- negotiate[Grains](GrainsNegotiator.html | GrainsNegotiator.json)
    } yield x(fetchGrains)
  }
}

trait FormulaPlan extends CommonDirectives with CalcServices {
  def formula = Intent {
    case "/calc/abv"          => for { o <- jsonPost[AbvInput] } yield abv(o)
    case "/calc/og"           => for { o <- jsonPost[OgInput] } yield og(o)
    case "/calc/attenuation"  => for { o <- jsonPost[AbvInput] } yield attenuation(o)
  }
}

trait CommonDirectives {
  def jsonPost[A : Parseable] = for {
    _ <- POST
    _ <- contentType("application/json")
    j <- commit(Body.string _)
    c <- getOrElse(Parseable[A].parseBody(j), BadRequest)
  } yield (c)

  val htmlGet = for {
    _ <- GET
    _ <- Accepts.Html
  } yield ()

  def negotiate[A](neg: Directive[Any, Any, A => ResponseFunction[Any]]) = for {
    _ <- GET
    fa <- neg
  } yield fa

}