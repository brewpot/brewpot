package org.brewpot

import xml.NodeSeq

trait Handlers {

  trait MainGetHandler extends GetHandler[NodeSeq] with Bootstrap {
    lazy val handle = wrap(
      <h1>Routes</h1>
        <table class="table">
          <thead>
            <tr>
              <td>Title</td>
              <td>Path</td>
            </tr>
          </thead>
          <tr>
            <td>API</td>
            <td>/api</td>
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
