package purchaseSystem

import bank.{BankAccount, OrangeBank}
import store._

object PurchaseSystemMain extends App {
  println("Welcome to the Online Purchase system!")

  val items = Seq(Item("soap", "shower soap", ItemType.Household, 4),
    Item("carpet", "Arabic carpet", ItemType.Household, 34),
    Item("audio cable", "stereo jack", ItemType.Electronic, 3),
    Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10)
  )
  val orderItems = Seq(
    OrderItem(Item("soap", "shower soap", ItemType.Household, 4), 1),
    OrderItem(Item("carpet", "Arabic carpet", ItemType.Household, 34), 2),
    OrderItem(Item("audio cable", "stereo jack", ItemType.Electronic, 3), 5),
    OrderItem(Item("Appetite for destruction - GNR", "Music CD", ItemType.Multimedia, 10), 8)
  )

  val order = Order(orderItems)
  val cart = Cart(Some(order))

  val orderTotalPrice = Order.getOrderTotalPrice(order)

  val customer = Customer("Joe", "Smith", "20.01.2000", "joe", "898",
    Address("Street 1", "31C", "71912", "Belfast", "CatLand"),
    Some(cart), Some(CreditCard(CreditCardType.Visa, "joe", "abc", "982")))

  val storeInfo = StoreInfo("MangoShop", "www.mango-shop.com", items, Seq(customer))
  val store = new Store(storeInfo)

  println(s"Store is: $store")

  implicit val bank: OrangeBank = new OrangeBank(Seq(
    BankAccount("abc", 1000),
    BankAccount("abcd", 100),
    BankAccount("abcde", 200)
  ))
}