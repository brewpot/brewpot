package org.brewpot

import xml.NodeSeq
import org.brewpot.model.{Hop, Grain}
import org.json4s.JsonAST.{JArray, JValue}

trait ViewModule {
  def htmlGreet: NodeSeq
  def htmlCalcOg: NodeSeq

  def htmlGrains(grains: Seq[Grain]): NodeSeq
  def jsonGrains(grains: Seq[Grain]): JValue

  def htmlHops(hops: Seq[Hop]): NodeSeq
  def jsonHops(hops: Seq[Hop]): JValue
}

trait StaticDataView extends ViewModule {
  val htmlGreet = {
    <h1>Welcome to Brewpot!</h1> ++
    <p>
      This will be the home of Brewpot. An application for social brewing.
      Create, clone or get inspired by other recipes, take control over your inventory
      or just hang around to see what people are brewing. Check back soon for incremental updates!!
    </p>
  }

  val htmlCalcOg = {
    <form class="form-inline">
      <label class="input">
        <b>OG</b> <input type="text" class="input-small" placeholder="1.050"/>
      </label>
      <label class="input">
        <b>FG</b> <input type="text" class="input-small" placeholder="1.010"/>
      </label>
      <button type="submit" class="btn">Calculate</button>
    </form>
  }

}

trait DynamicDataView extends ViewModule {
  def htmlGrains(grains: Seq[Grain]) = {
    <table class="table">
      <tr>
        <th>Name</th>
        <th>Potential</th>
      </tr>{grains.map(g =>
      <tr>
        <td>{g.name}</td>
        <td>{g.potential}</td>
      </tr>)}
    </table>
  }
  def htmlHops(hops: Seq[Hop]): NodeSeq = {
    <table class="table">
      <tr>
        <th>Name</th>
        <th>Alpha acid (%)</th>
      </tr>{hops.map(h =>
      <tr>
        <td>{h.name}</td>
        <td>{h.alphaAcid}</td>
      </tr>)}
    </table>
  }
  def jsonGrains(grains: Seq[Grain]): JValue = JArray(grains.map(Grain.json.pickle(_)).toList)
  def jsonHops(hops: Seq[Hop]): JValue = JArray(hops.map(Hop.json.pickle(_)).toList)

}
