package org.brewpot

import jsonpicklers.Picklers._

object Calculations {

  def abv(in: AbvCalc): Double = (in.og - in.fg) * 1.31

  case class AbvCalc(og: Double, fg: Double)

  object AbvCalc {
    val json = wrap(apply)(unapply(_).get) {
      ("og" :: double) ~
      ("fg" :: double)
    }
  }
}

