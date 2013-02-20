package org.brewpot

import unfiltered.response._
import org.brewpot.Calculations.AbvCalc
import org.brewpot.Bootstrap._
import unfiltered.response.Html5
import unfiltered.response.ResponseString

trait CalcServices {
  def abv(in: AbvCalc) = PlainTextContent ~> ResponseString(Calculations.abv(in).toString)
}

trait ViewServices extends ViewModule {
  lazy val greetSvc = Ok ~> HtmlContent ~> Html5(wrap(greet))
}
