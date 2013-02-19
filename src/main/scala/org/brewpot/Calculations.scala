package org.brewpot

import jsonpicklers.Picklers._
import org.json4s.native.JsonParser._
import jsonpicklers.{Failure, Success}

object Calculations {

  def abv(in: AbvCalc): Double = (in.og - in.fg) * 1.31


  case class AbvCalc(og: Double, fg: Double)

  object AbvCalc {
    val json = wrap(apply)(unapply(_).get) {
      ("og" :: double) ~
      ("fg" :: double)
    }

    implicit object AbvCalcParser extends BodyParser[AbvCalc] {
      def parseBody(json: String): Option[AbvCalc] =
        parseOpt(json).flatMap { p =>
          AbvCalc.json.unpickle(p) match {
            case Success(v, _) => Some(v)
            case Failure(m, _) => None
          }
        }
    }
  }
}

