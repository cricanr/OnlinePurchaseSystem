package store

import org.scalatest.{Matchers, WordSpec}

class OrderTest extends WordSpec with Matchers {
  "An order" when {
    "calling getOrderTotalPrice" should {
      "calculate the order total price with multiple items" in {
        val orderItems = Seq(
          OrderItem(Item("soap", "shower soap", ItemType.Household, 4), 1),
          OrderItem(Item("carpet", "Arabic carpet", ItemType.Household, 34), 2),
          OrderItem(Item("audio cable", "stereo jack", ItemType.Electronic, 3), 5),
          OrderItem(Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10), 8)
        )

        val order = Order(orderItems)
        Order.getOrderTotalPrice(order) shouldBe 167
      }
    }

    "calling getOrderTotalPrice" should {
      "calculate the order total price with 1 item" in {
        val orderItems = Seq(
          OrderItem(Item("soap", "shower soap", ItemType.Household, 4), 1),
        )

        val order = Order(orderItems)
        Order.getOrderTotalPrice(order) shouldBe 4
      }
    }

    "calling getOrderTotalPrice" should {
      "calculate the order total price with no items" in {
        val orderItems = Seq.empty

        val order = Order(orderItems)
        Order.getOrderTotalPrice(order) shouldBe 0
      }
    }
  }
}
