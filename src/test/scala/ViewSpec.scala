import org.brewpot.models.Brew
import org.brewpot.web.Views
import org.specs2.mutable.Specification

class ViewSpec extends Specification {

  "The view object" should {
    "turn a bunch of brews into an xml for recipes" in {
      val b1 = Brew("0", "foo", "bar", 1.080, 7.42, 20, 60, "poo")
      val bs = Seq(b1)
      val nodes = Views.recipes(bs).nodes
      nodes must \\("td") \> b1.id
      nodes must \\("td") \> b1.name
      nodes must \\("td") \> b1.style
      nodes must \\("td") \> b1.OG.toString
      nodes must \\("td") \> "%s %%".format(b1.ABV)
      nodes must \\("td") \> b1.EBC.toString
      nodes must \\("td") \> b1.IBU.toString
      nodes must \\("td") \> b1.user
    }
  }

}
