package org.brewpot

import unfiltered.filter.Plan
import unfiltered.request._
import org.brewpot.handlers._
import org.brewpot.auth.{AuthFlow, TwitterAuthProvider}

object plan extends Plan {

  lazy val twitterFlow = new AuthFlow(TwitterAuthProvider)

  def intent = {
    case req @ GET(Path(Seg(Nil))) => MainPageHandler.handleMain(req)

    case req @ GET(Path(Seg("recipes" :: Nil))) => RecipeHandler.handleRecipes(req)

    case req @ GET(Path(Seg("recipesinput" :: Nil))) => RecipeHandler.handleAddRecipeForm(req)

    case req @ POST(Path(Seg("recipesinput" :: Nil))) => RecipeHandler.handleSaveRecipe(req)

    case req @ Path(Seg("auth" :: "twitter" :: "login" :: Nil)) => twitterFlow.authToken(req)

    case req @ Path(Seg("auth" :: "twitter" :: "callback" :: Nil)) => twitterFlow.callback(req)

    case req @ Path(Seg("auth" :: "twitter" :: "logout" :: Nil)) => twitterFlow.logout(req)

  }

}
