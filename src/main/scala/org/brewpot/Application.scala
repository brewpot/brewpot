package org.brewpot

import unfiltered.filter.Plan

class Application extends Plan {

  // assemble the cake
  object global extends PlanModule
    with StaticDataView
    with DynamicDataView
    with MongoDbDataProvider

  // assemble all intents
  def intent = global.intent
}