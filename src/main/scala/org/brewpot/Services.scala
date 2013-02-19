package org.brewpot

import unfiltered.response.{ResponseString, PlainTextContent, HttpResponse}
import org.brewpot.Calculations.AbvCalc

object Services {
  def abv(in: AbvCalc) = PlainTextContent ~> ResponseString(Calculations.abv(in).toString)
}
