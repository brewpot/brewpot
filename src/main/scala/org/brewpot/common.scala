package org.brewpot

import util.Properties

object common {

  trait EnvProperty {
    def property(property: String) = Properties.envOrNone(property)
      .getOrElse(throw new IllegalArgumentException("Missing env variable for %s".format(property)))
  }

}
