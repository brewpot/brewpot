package org.brewpot

trait PlanModule extends PagePlan with FormulaPlan with BrewPlan {
  def intent = page orElse brew orElse formula
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
    case "/" => for { _ <- getHtml } yield greetResponse
    case "/calc/abv" => for { _ <- getHtml } yield Ok
    case "/calc/og" => for { _ <- getHtml } yield calcOgResponse
    case "/calc/attenuation" => for { _ <- getHtml } yield Ok
  }
}

trait BrewPlan extends CommonDirectives with ViewServices with CalcServices {

  type Grains = Seq[Grain]

  def brew = Intent {
    case "/ingredients/fermentables" => for {
      x <- getNegotiate[Grains](GrainsNegotiator.html | GrainsNegotiator.json)
    } yield x(fetchGrains)
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

  def getNegotiate[A](neg: Directive[Any, Any, A => ResponseFunction[Any]]) = for {
    _ <- GET
    fa <- neg
  } yield fa

}

case class Neg[A](directive: Directive[Any, Any, A => ResponseFunction[Any]])

