package org.brewpot.service

import org.brewpot.web.Views
import org.brewpot.models.Brew

object RecipeService {

  def recipes = {
    val bs = Seq(
      Brew("0", "Underyul", "Dobbelbock", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("1", "Overyul", "Special Yul", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("2", "Obama Honey Ale", "Honey Ale", 1.055, 5.5, 30, 37, "obama"),
      Brew("3", "Mjød", "Mead", 1.070, 6.78, 43, 40, "kareblak"),
      Brew("4", "Underyul", "Dobbelbock", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("5", "Overyul", "Special Yul", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("6", "Obama Honey Ale", "Honey Ale", 1.055, 5.5, 30, 37, "obama"),
      Brew("7", "Mjød", "Mead", 1.070, 6.78, 43, 40, "kareblak"),
      Brew("8", "Underyul", "Dobbelbock", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("9", "Overyul", "Special Yul", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("10", "Obama Honey Ale", "Honey Ale", 1.055, 5.5, 30, 37, "obama"),
      Brew("11", "Mjød", "Mead", 1.070, 6.78, 43, 40, "kareblak")
    )
    Views.recipes(bs)
  }

}
