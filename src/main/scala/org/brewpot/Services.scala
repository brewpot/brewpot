package org.brewpot


import unfiltered.response._
import org.brewpot.Calculations._
import org.brewpot.Bootstrap._
import unfiltered.response.Html5
import unfiltered.response.ResponseString

import org.json4s.native.JsonMethods._
import org.json4s.JsonAST


trait CalcServices {
  def abv(in: AbvInput) = jsonResponse(AbvOutput.json.pickle(Calculations.abv(in)))
  def og(in: OgInput) = jsonResponse(OgOutput.json.pickle(Calculations.og(in)))
  def attenuation(in: AbvInput) = jsonResponse(AttenuationOutput.json.pickle(Calculations.attenuation(in)))

  private def jsonResponse(json: JsonAST.JObject): ResponseFunction[Any] =
    JsonContent ~> ResponseString(compact(render(json)))
}

trait ViewServices extends ViewModule with DataProviderModule {
  lazy val greetResponse = Ok ~> HtmlContent ~> Html5(wrap(htmlGreet))
  lazy val calcOgResponse = Ok ~> HtmlContent ~> Html5(wrap(htmlCalcOg))

  def grainsResponse = Ok ~> HtmlContent ~> Html5(wrap(htmlGrains(fetchGrains)))
}
