package org.brewpot.web


object URITools {

  def toHttps(uri: String) = uri.replaceFirst("http", "https")

  def link(uri: String) = toHttps(uri)

}
