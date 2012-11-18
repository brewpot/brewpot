package org.brewpot.model


object styles {

  case class Style(typeDef: Char, styleName: String, category: Category) {
    override def toString = "%s%s. %s".format(category.typeDef, typeDef, styleName)
  }

  case class Category(typeDef: Int, name: String)

  object PaleLager extends Category('1', "Pale Lager")
  object ModernPaleLager extends Style('A', "Modern Pale Lager", PaleLager)
  object InternationalLager extends Style('B', "International Lager", PaleLager)
  object CheczPilsner extends Style('C', "Checz Pilsner", PaleLager)
  object GermanPilsner extends Style('D', "German Pilsner", PaleLager)
  object MunchenerHelles extends Style('E', "Münchener Helles", PaleLager)

  object DarkLager extends Category('2', "Dark Lager")
  object ModernDarkLager extends Style('A', "Modern Dark Lager", DarkLager)
  object Wiener extends Style('B', "Wiener", DarkLager)
  object Marzen extends Style('C', "Märzen", DarkLager)
  object MunchenerDunkel extends Style('D', "Münchener Dunkel", DarkLager)
  object DarkCheczLager extends Style('E', "Dark Checz Lager", DarkLager)
  object Schwarzbier extends Style('F', "Schwarzbier", DarkLager)
  object ClassicSmokeBeer extends Style('G', "Classic Smoke Beer", DarkLager)

}
