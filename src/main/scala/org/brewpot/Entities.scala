package org.brewpot

import org.json4s.native.JsonParser._
import jsonpicklers._
import Picklers._

case class AbvCalc(og: Double, fg: Double)

object AbvCalc {
  val json = wrap(apply)(unapply(_).get) {
    ("og" :: double) ~
    ("fg" :: double)
  }

  implicit object AbvCalcParser extends BodyParser[AbvCalc] {
    def parseBody(json: String): Option[AbvCalc] =
      parseOpt(json).flatMap { p => AbvCalc.json.unpickle(p) match {
          case Success(v, _) => Some(v)
          case Failure(_, _) => None
        }
      }
  }
}

