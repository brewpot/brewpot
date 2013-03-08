package org.brewpot

  trait Fermentable {
    val potential: Double
  }

  trait Ingredient {
    val weight: Int
  }

  trait BitteringSubstance {
    val alphaAcid: Double
  }

  case class FermentableIngredient(potential: Double, weight: Int) extends Fermentable with Ingredient

  case class BitteringIngredient(weight: Int, alphaAcid: Double) extends BitteringSubstance with Ingredient

  case class Recipe(efficiency: Double, volume: Double, fermentables: Seq[FermentableIngredient])

  case class Grain(name: String, potential: Double) extends Fermentable

  case class Adjunct(potential: Double) extends Fermentable

  case class Fruit(potential: Double) extends Fermentable

  case class Sugar(potential: Double) extends Fermentable

  case class Hop(name: String, alphaAcid: Double) extends BitteringSubstance