package org.brewpot.service

import org.brewpot.web.Views
import org.brewpot.models.Brew

object RecipeService {

  def recipe = {
    val bs = Seq(
      Brew("0", "Underyul", "Dobbelbock", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("0", "Overyul", "Special Yul", 1.080, 7.62, 60, 40, "kareblak"),
      Brew("0", "Obama Honey Ale", "Honey Ale", 1.055, 5.5, 30, 37, "obama"),
      Brew("0", "Mjød", "Mead", 1.070, 6.78, 43, 40, "kareblak")
    )
    Views.recipes(bs)
  }

}
