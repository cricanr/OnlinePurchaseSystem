package store

import bank.{BankAccount, OrangeBank}
import org.scalatest.{Matchers, WordSpec}

class StoreTest extends WordSpec with Matchers {
  implicit val bank: OrangeBank = new OrangeBank(Seq(
    BankAccount("abc", 1000),
    BankAccount("abcd", 100),
    BankAccount("abcde", 200)
  ))

  private def generateItems: Seq[Item] = {
    Seq(Item("soap", "shower soap", ItemType.Household, 4),
      Item("carpet", "Arabic carpet", ItemType.Household, 34),
      Item("audio cable", "stereo jack", ItemType.Electronic, 3),
      Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10)
    )
  }

  private def generateCustomerValid: Customer = {
    val orderItems = Seq(
      OrderItem(Item("soap", "shower soap", ItemType.Household, 4), 1),
      OrderItem(Item("carpet", "Arabic carpet", ItemType.Household, 34), 2),
      OrderItem(Item("audio cable", "stereo jack", ItemType.Electronic, 3), 5),
      OrderItem(Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10), 8)
    )

    val order = Order(orderItems)
    val cart = Cart(Some(order))

    Customer("Joe", "Smith", "20.01.2000", "joe", "898",
      Address("Street 1", "31C", "71912", "Belfast", "CatLand"),
      Some(cart), Some(CreditCard(CreditCardType.Visa, "joe", "abc", "982")))
  }

  private def generateCustomerInvalidCard: Customer = {
    val orderItems = Seq(
      OrderItem(Item("soap", "shower soap", ItemType.Household, 4), 1),
      OrderItem(Item("carpet", "Arabic carpet", ItemType.Household, 34), 2),
      OrderItem(Item("audio cable", "stereo jack", ItemType.Electronic, 3), 5),
      OrderItem(Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10), 8)
    )

    val order = Order(orderItems)
    val cart = Cart(Some(order))

    Customer("Joe", "Smith", "20.01.2000", "joe", "898",
      Address("Street 1", "31C", "71912", "Belfast", "CatLand"),
      Some(cart), None)
  }

  private def generateCustomerInvalidOrder: Customer = {
    val orderItems = Seq.empty

    val order = Order(orderItems)
    val cart = Cart(Some(order))

    Customer("Joe", "Smith", "20.01.2000", "joe", "898",
      Address("Street 1", "31C", "71912", "Belfast", "CatLand"),
      Some(cart), Some(CreditCard(CreditCardType.Visa, "joe", "abc", "982")))
  }

  private def generateCustomerInValid: Customer = {
    val orderItems = Seq(
      OrderItem(Item("soap", "shower soap", ItemType.Household, 4), 1),
      OrderItem(Item("carpet", "Arabic carpet", ItemType.Household, 34), 2),
      OrderItem(Item("audio cable", "stereo jack", ItemType.Electronic, 3), 5),
      OrderItem(Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10), 8)
    )

    val order = Some(Order(orderItems))
    val cart = Cart(order)

    Customer("Caat", "Smith", "20.01.2000", "miau", "898",
      Address("Street 1", "31C", "71912", "Belfast", "CatLand"),
      Some(cart), Some(CreditCard(CreditCardType.Visa, "joe", "abc", "982")))
  }

  private def generateStore: Store = {
    val customer = generateCustomerValid
    val storeInfo = StoreInfo("MangoShop", "www.mango-shop.com", generateItems, Seq(customer))
    new Store(storeInfo)
  }

  "A store" when {
    "calling purchase for a known given customer with a valid order" should {
      "return the purchase response successful" in {
        val store = generateStore
        val customer = generateCustomerValid
        store.purchase(customer) shouldBe PurchaseResult.PurchaseSuccessful
      }
    }

    "calling purchase for an unknown customer with a valid order" should {
      "return the purchase response failure" in {
        val store = generateStore
        val customer = generateCustomerInValid
        store.purchase(customer) shouldBe PurchaseResult.PurchaseFailure
      }
    }

    "calling purchase for a known given customer with an invalid order" should {
      "return the purchase response failure" in {
        val store = generateStore
        val customer = generateCustomerInvalidOrder
        store.purchase(customer) shouldBe PurchaseResult.PurchaseFailure
      }
    }

    "calling purchase for a known given customer with an invalid CC" should {
      "return the purchase response failure" in {
        val store = generateStore
        val customer = generateCustomerInvalidCard
        store.purchase(customer) shouldBe PurchaseResult.PurchaseFailure
      }
    }
  }
}
