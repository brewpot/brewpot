package org.brewpot

trait PlanModule extends ViewPlan with FormulaPlan {
  def intent = views orElse formula
}

import directives._, Directives._
import unfiltered.request._
import unfiltered.response._
import org.brewpot.Calculations._

trait ViewPlan extends CommonDirectives with ViewServices {
  def views = Path.Intent {
    case Seg("view" :: "recipebuilder" :: Nil)                  => recipeBuilder
    case Seg("view" :: "ingredients" :: "fermentables" :: Nil)  => fermentablesOverview
    case Seg("view" :: "ingredients" :: "hops" :: Nil)          => hopsOverview
    case Seg("grain" :: Nil ) => GrainsNegotiator.all
  }
}

trait FormulaPlan extends CommonDirectives with CalcServices {
  def formula = Path.Intent {
    case "/calc/abv"          => for { o <- parse[AbvInput] } yield abv(o)
    case "/calc/og"           => for { o <- parse[OgInput] } yield og(o)
    case "/calc/attenuation"  => for { o <- parse[AbvInput] } yield attenuation(o)
  }
}

trait CommonDirectives {

  implicit val eqRequestContentType = Directive.Eq {
    (R: RequestContentType.type, value: String) => when{ case R(`value`) => value } orElse UnsupportedMediaType
  }

  def parse[A : Parseable] = for {
    _ <- POST
    _ <- RequestContentType === "application/json"
    j <- request[Any].flatMap(r => success(Body.string(r)))
    c <- getOrElse(Parseable[A].parseBody(j), BadRequest)
  } yield (c)

  val htmlGet = for {
    _ <- GET
    _ <- Accepts.Html
  } yield ()
}