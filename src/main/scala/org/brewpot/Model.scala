package org.brewpot

object model {

  import jsonpicklers.Picklers._

  sealed trait Fermentable { val potential: Double }

  object Grain {
    val json = wrap(apply)(unapply(_).get){
      ("name" :: string) ~
      ("potential" :: double)
    }
  }
  case class Grain(name: String, potential: Double) extends Fermentable
  case class Adjunct(potential: Double) extends Fermentable
  case class Fruit(potential: Double) extends Fermentable
  case class Sugar(potential: Double) extends Fermentable

  type Gram = Int
  case class MaltBill(fermentableIngredients: Seq[(Gram, Fermentable)])


}