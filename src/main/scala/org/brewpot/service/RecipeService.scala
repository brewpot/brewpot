package org.brewpot.service

import org.brewpot.web.Views

object RecipeService {

  def recipe = {
    Views.recipes(Seq())
  }

}
