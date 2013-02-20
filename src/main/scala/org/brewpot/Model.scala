package org.brewpot

object model {

  sealed trait Fermentable { val potential: Double }

  case class Grain(name: String, potential: Double) extends Fermentable
  case class Adjunct(potential: Double) extends Fermentable
  case class Fruit(potential: Double) extends Fermentable
  case class Sugar(potential: Double) extends Fermentable

  type Gram = Int
  case class MaltBill(fermentableIngredients: Seq[(Gram, Fermentable)])


}