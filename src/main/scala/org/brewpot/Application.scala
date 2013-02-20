package org.brewpot

import unfiltered.filter.Plan

class Application extends Plan {

  // assemble the cake
  object global extends PlanModule
    with StaticDataView
    with MongoDbDataProvider
    with HerokuConfiguration

  // assemble all intents
  def intent = global.intent
}