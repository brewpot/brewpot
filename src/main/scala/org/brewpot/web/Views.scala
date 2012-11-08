package org.brewpot.web

import Snippets._
import org.brewpot.models._

object Views {

  def main = bootstrap(
    "Brewpot!",
    header("Brewpot!") ++
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
        <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?</p>
        <p>But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?</p>
  )

  def recipes(brews: Seq[Recipe]) = bootstrap(
    "Recipes",
    header("Recipes") ++
      <p>{recipebar}</p>
      <table class="table table-striped table-bordered table-hover">
        <thead>
            <th>Id</th>
            <th>Name</th>
            <th>Style</th>
            <th>OG</th>
            <th>ABV</th>
            <th>EBC</th>
            <th>IBU</th>
            <th>Brewer</th>
        </thead>
        {brews.sortBy(-_.id.toInt).map(x =>
        <tr>
          <td>{x.id}</td>
          <td>{x.name}</td>
          <td>{x.style.getOrElse("-")}</td>
          <td>{x.OG.getOrElse(0)}</td>
          <td>{x.ABV.getOrElse(0)} %</td>
          <td>{x.EBC.getOrElse(0)}</td>
          <td>{x.IBU.getOrElse(0)}</td>
          <td>{x.user}</td>
        </tr>)}
      </table>
  )

}
