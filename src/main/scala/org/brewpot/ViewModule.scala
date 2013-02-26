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
    <div class="container">
      <h1>Brew Calculator</h1>
      <p>Just give us whatever info you have, and we'll try to make something out of it</p>
      <form class="form-inline form-pad">
        <div class="input-append input-prepend">
          <span class="add-on">OG</span>
          <input type="text" class="input-mini" placeholder="1.050"/>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">FG</span>
          <input type="text" class="input-mini" placeholder="1.010"/>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">ABV</span>
          <input type="text" class="input-mini" placeholder="6.5"/>
          <span class="add-on">%</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">ABW</span>
          <input type="text" class="input-mini" placeholder="8.125"/>
          <span class="add-on">%</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Efficiency</span>
          <input type="text" class="input-mini" placeholder="75"/>
          <span class="add-on">%</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Attenuation</span>
          <input type="text" class="input-mini" placeholder="75"/>
          <span class="add-on">%</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Temperature</span>
          <input type="text" class="input-mini" placeholder="25"/>
          <span class="add-on">°C</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Calibration</span>
          <input type="text" class="input-mini" placeholder="20"/>
          <span class="add-on">°C</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">BG</span>
          <input type="text" class="input-mini" placeholder="1.060"/>
          <span class="add-on">°C</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Boil volume</span>
          <input type="text" class="input-mini" placeholder="23"/>
          <span class="add-on">L</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Wort volume</span>
          <input type="text" class="input-mini" placeholder="21"/>
          <span class="add-on">L</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Priming sugar</span>
          <input type="text" class="input-mini" placeholder="126"/>
          <span class="add-on">g</span>
        </div>
        <div class="input-append input-prepend">
          <span class="add-on">Beer type</span>
          <select>
            <option>British ale</option>
            <option>Belgian ale</option>
            <option>American beer</option>
            <option>Fruit lambic</option>
            <option>Porter and stout</option>
            <option>European lager</option>
            <option>Lambic</option>
            <option>Wheat beer</option>
          </select>
        </div>
        <div class="button-top">
          <button type="reset" class="btn">Reset</button>
          <button type="submit" class="btn">Calculate</button>
        </div>
      </form>
    </div>
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
