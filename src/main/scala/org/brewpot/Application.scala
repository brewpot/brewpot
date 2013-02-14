package org.brewpot

import unfiltered.filter.Plan

class Application extends Plan {

  object global extends PlanModule
    with StaticService
    with BootstrapWrapper
    with StaticDataProvider

  def intent = global.intent
}