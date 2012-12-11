package org.brewpot.twitter

object TwitterImage {

  def toBigger(uri: String): String = to("_bigger")(uri)
  def toNormal(uri: String): String = to("_normal")(uri)
  def toMini(uri: String): String = to("_mini")(uri)
  def toDefault(uri: String): String = to("")(uri)

  private def to(size: String)(uri: String): String = {
    val regex = """(.*)_[a-z]+(.[a-z]{3,4})""".r
    val regex(a, b) = uri
    "%s%s%s".format(a, size, b)
  }

}
