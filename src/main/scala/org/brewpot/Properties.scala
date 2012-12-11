package org.brewpot

object Properties {

  def get(property: String) =
    util.Properties.envOrNone(property).getOrElse(throw new IllegalArgumentException("Missing env variable for %s".format(property)))
}
