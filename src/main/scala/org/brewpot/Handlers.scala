package org.brewpot

import xml.NodeSeq

trait Handlers {

  trait MainGetHandler extends GetHandler[NodeSeq] with Bootstrap {
    lazy val handle = wrap(
      <h1>Routes</h1>
        <table class="table table-bordered">
            <tr>
              <th>Resources</th>
              <th>Path</th>
            </tr>
            <tr>
              <td>this</td>
              <td>/</td>
            </tr>
        </table>
    )
  }

  trait Routing {
    this: GetHandler[NodeSeq] =>
    lazy val handleMain: Any = handle
  }

  trait GetHandler[B] {
    def handle: B
  }

}
