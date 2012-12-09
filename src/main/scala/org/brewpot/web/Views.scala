package org.brewpot.web

import snippets._
import unfiltered.response.Html5
import org.brewpot.entities.{Recipe, User}

object views {

  def mainPage(user: Option[User], alert: Option[String]): Html5 = {
    bootstrap(
      "Brewpot!",
        <p>
          <h2>Features comming up:</h2>
          <ul>
            <li>Searchable recipe database</li>
            <li>Algorithms for all you OG, IBU, EBC etc. calculation needs</li>
            <li>Brewing guilds with any social aspects we come up with</li>
            <li> ... </li>
          </ul>
        </p>
    )(user)
  }

  def mainPage: Html5 = mainPage(None, None)

  def recipesPage(user: Option[User])(brews: Seq[Recipe]): Html5 = bootstrap(
    "Recipes",
      <p>
        {recipebar(user.isDefined)}
      </p>
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <th class="hidden-phone">Id</th>
            <th>Name</th>
            <th>Style</th>
            <th>OG</th>
            <th class="hidden-phone">ABV</th>
            <th class="hidden-phone">EBC</th>
            <th class="hidden-phone">IBU</th>
            <th>Brewer</th>
          </thead>{brews.sortBy(-_.id.toInt).map(x =>
          <tr>
            <td class="hidden-phone">{x.id}</td>
            <td>{if (x.data.name.size >= 16) x.data.name.substring(0, 12) + "..." else x.data.name}</td>
            <td>{x.data.style.getOrElse("-")}</td>
            <td>{x.data.OG.getOrElse(0)}</td>
            <td class="hidden-phone">{x.data.ABV.getOrElse(0)} %</td>
            <td class="hidden-phone">{x.data.EBC.getOrElse(0)}</td>
            <td class="hidden-phone">{x.data.IBU.getOrElse(0)}</td>
            <td>{x.user}</td>
          </tr>
        )}
        </table>
  )(user)

  def newRecipePage(user: Option[User]) = bootstrap(
    "Add recipe",
    recipeForm
  )(user)


}
