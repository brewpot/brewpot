package org.brewpot


import unfiltered.response._
import org.brewpot.Calculations._
import org.brewpot.Bootstrap._
import unfiltered.response.Html5


import org.json4s.JsonAST
import org.json4s.native.JsonMethods._
import unfiltered.request._

import directives._, Directives._

trait CalcServices {
  def abv(in: AbvInput) = jsonResponse(AbvOutput.json.pickle(Calculations.abv(in)))
  def og(in: OgInput) = jsonResponse(OgOutput.json.pickle(Calculations.og(in)))
  def attenuation(in: AbvInput) = jsonResponse(AttenuationOutput.json.pickle(Calculations.attenuation(in)))

  private def jsonResponse(json: JsonAST.JObject): ResponseFunction[Any] =
    JsonContent ~> ResponseString(compact(render(json)))
}

trait ViewServices extends ViewModule with DataProviderModule with CommonDirectives {
  lazy val greetResponse = Ok ~> HtmlContent ~> Html5(wrap(htmlGreet))
  lazy val calcOgResponse = Ok ~> HtmlContent ~> Html5(wrap(htmlCalcOg))

  val getRecipeBuilder = for {
    _ <- GET
    _ <- Accepts.Html
  } yield recipeBuilderForm

  val postRecipe = for {
    _ <- POST
    _ <- RequestContentType === "application/json"
  } yield storeRecipe(Recipe(0.72, 23, Seq(FermentableIngredient(1, 6)))).getOrElse("#YOLO")

  val recipeBuilder = for {
    res <- getRecipeBuilder | postRecipe
  } yield Html5(wrap(res))

  val fermentablesOverview = for {
    _ <- GET
    f <- GrainsNegotiator.all
  } yield f(fetchGrains)

  val hopsOverview = for {
    _ <- GET
    f <- HopsNegotiator.all
  } yield f(fetchHops)


  object GrainsNegotiator extends Negotiator[Seq[Grain]] {
    def html = negotiateHtml(htmlGrains)
    def json = negotiateJson(jsonGrains)
  }

  object HopsNegotiator extends Negotiator[Seq[Hop]] {
    def html = negotiateHtml(htmlHops)
    def json = negotiateJson(jsonHops)
  }
}
