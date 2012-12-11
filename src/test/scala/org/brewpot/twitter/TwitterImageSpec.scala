package org.brewpot.twitter

import org.specs2.mutable.Specification
import TwitterImage._

class TwitterImageSpec extends Specification {

  "A TwitterImageSpec" should {
    "Convert from non-default to a mini image" in {
      val act = toMini("https://si0.twimg.com/profile_images/1438634086/avatar_normal.png")
      val exp = "https://si0.twimg.com/profile_images/1438634086/avatar_mini.png"
      act === exp
    }
    "Convert from default to a mini image" in {
      val act = toMini("https://si0.twimg.com/profile_images/1438634086/avatar.png")
      val exp = "https://si0.twimg.com/profile_images/1438634086/avatar_mini.png"
      act === exp
    }
    "Convert to a normal image" in {
      val act = toNormal("https://si0.twimg.com/profile_images/1438634086/avatar_mini.jpg")
      val exp = "https://si0.twimg.com/profile_images/1438634086/avatar_normal.jpg"
      act === exp
    }
    "Convert to a bigger image" in {
      val act = toBigger("https://si0.twimg.com/profile_images/1438634086/avatar_normal.gif")
      val exp = "https://si0.twimg.com/profile_images/1438634086/avatar_bigger.gif"
      act === exp
    }
    "Convert to a default image" in {
      val act = toDefault("https://si0.twimg.com/profile_images/1438634086/avatar_normal.png")
      val exp = "https://si0.twimg.com/profile_images/1438634086/avatar.png"
      act === exp
    }
  }

}
