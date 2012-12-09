package org.brewpot.web

import java.net.URL

object URITools {

  def toHttps(uri: String) = uri.replaceFirst("http", "https")

  def link(uri: String) = toHttps(uri)

}
