package org.brewpot

object model {

  sealed trait Fermentable { val potential: Double }

  case class Grain(potential: Double) extends Fermentable
  case class Adjunct(potential: Double) extends Fermentable
  case class Fruit(potential: Double) extends Fermentable
  case class Sugar(potential: Double) extends Fermentable

  type Gram = Int
  case class FermentableIngredient(weight: Gram, fermentable: Fermentable)
  case class MaltBill(fermentableIngredients: Seq[FermentableIngredient])


}