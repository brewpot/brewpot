package org.brewpot.twitter

object TwitterImage {

  val toBigger  = to("_bigger")_
  val toNormal  = to("_normal")_
  val toMini    = to("_mini")_
  val toDefault = to("")_

  private def to(size: String)(uri: String): String = {
    val _index = uri.lastIndexOf('_')
    val regex = """(_[a-z]+)?(.[^.]*)\Z""".r
    uri.substring(0, _index) + regex.replaceFirstIn(uri.substring(_index), size+"$2")
  }

}