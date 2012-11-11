package org.brewpot

import javax.swing.plaf.OptionPaneUI

object models {

  case class Recipe(id: Option[String], name: String, style: Option[String], OG: Option[Double], ABV: Option[Double], EBC: Option[Int], IBU: Option[Int], user: String)

}