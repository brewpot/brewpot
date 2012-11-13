package org.brewpot.web

import Snippets._
import org.brewpot.models._

object Views {

  def main(implicit auth: Boolean) = bootstrap(
    "Brewpot!",
    header("Brewpot!") ++
      <p>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
      </p>,
    auth
  )

  def recipes(brews: Seq[Recipe]) = bootstrap(
    "Recipes",
    header("Recipes") ++
      addRecipeModal ++
      <p>{recipebar}</p>
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
        </thead>
        {brews.sortBy(-_.id.getOrElse("0").toInt).map(x =>
          <tr onclick="input" data-toggle="modal" href="#addRecipeModal">
            <td class="hidden-phone">{x.id.getOrElse("-")}</td>
            <td>{if (x.name.size >= 16) x.name.substring(0, 13) + "..." else x.name}</td>
            <td>{x.style.getOrElse("-")}</td>
            <td>{x.OG.getOrElse(0)}</td>
            <td class="hidden-phone">{x.ABV.getOrElse(0)} %</td>
            <td class="hidden-phone">{x.EBC.getOrElse(0)}</td>
            <td class="hidden-phone">{x.IBU.getOrElse(0)}</td>
            <td>{x.user}</td>
          </tr>
        )}
      </table>,
      false
  )

}
