package org.brewpot

import jsonpicklers._, Picklers._

object Calculations {

  def abv(in: AbvInput) = AbvOutput(
    0.7608 * (in.og - in.fg) / (1.775 - in.og) * in.fg / 0.794
  )

  def og(in: OgInput) = OgOutput(
    in.fermentables.foldLeft(0.0) {(a, f) =>
      a + (f.grams * (f.gravity - 1))
    } * 8.345404 * in.efficiency / in.wortVolume / 1000 + 1
  )

  def attenuation(in: AbvInput) = AttenuationOutput(
    (in.og - in.fg) / (in.og - 1)
  )

  case class AbvInput(og: Double, fg: Double)
  object AbvInput {
    val json = wrap(apply)(unapply(_).get) {
      ("og" :: double) ~
      ("fg" :: double)
    }
  }

  case class AbvOutput(abv: Double)
  object AbvOutput {
    val json = wrap(apply)(unapply(_).get) {
      ("abv" :: double)
    }
  }

  case class FermableInput(gravity: Double, grams: Int)
  object FermableInput {
    val json = wrap(apply)(unapply(_).get) {
      ("gravity" :: double) ~
      ("grams" :: int)
    }
  }

  case class OgInput(fermentables: List[FermableInput], efficiency: Double, wortVolume: Double)
  object OgInput {
    val json = wrap(apply)(unapply(_).get) {
      ("fermentables" :: FermableInput.json.*) ~
      ("efficiency" :: double) ~
      ("wort_volume" :: double)
    }
  }

  case class OgOutput(og: Double)
  object OgOutput {
    val json = wrap(apply)(unapply(_).get) {
      ("og" :: double)
    }
  }

  case class AttenuationOutput(attenuation: Double)
  object AttenuationOutput {
    val json = wrap(apply)(unapply(_).get) {
      ("attenuation" :: double)
    }
  }
}

