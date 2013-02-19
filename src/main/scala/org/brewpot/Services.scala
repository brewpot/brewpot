package org.brewpot

import unfiltered.response._
import org.brewpot.Calculations.AbvCalc
import org.brewpot.Bootstrap._
import unfiltered.response.Html5
import unfiltered.response.ResponseString

object CalcServices {
  def abv(in: AbvCalc) = PlainTextContent ~> ResponseString(Calculations.abv(in).toString)
}

object ViewService extends DataProviderModule {
  lazy val greet = Ok ~> HtmlContent ~> Html5(wrap(<h1>{greetTitle}</h1><p>{greetContent}</p>))
}
