package org.brewpot

import unfiltered.filter.Plan

class Application extends Plan {

  // assemble the cake
  object global extends PlanModule
    with StaticService
    with BootstrapHtmlWrapper
    with StaticDataProvider

  // assemble all intents
  def intent = global.intent
}