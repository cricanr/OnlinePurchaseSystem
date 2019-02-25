package store

import bank.Bank
import paymentGateway.{CreditCardPaymentGateway, PaymentGatewayStatus, PaymentResponse}
import store.CreditCardType.CreditCardType
import store.ItemType.ItemType
import store.PurchaseResult.PurchaseResult

object CreditCardType extends Enumeration {
  type CreditCardType = Value
  val Visa, MasterCard, Maestro = Value
}

case class Cart(order: Option[Order])

case class CreditCard(cardType: CreditCardType, name: String, accountNumber: String, secCode: String)

case class Customer(firstName: String, lastName: String, birthday: String, user: String, password: String, address: Address, cart: Option[Cart], card: Option[CreditCard])

object ItemType extends Enumeration {
  type ItemType = Value
  val Household, Electronic, Office, Multimedia = Value
}

case class Address(street: String, number: String, postalCode: String, city: String, country: String)

case class Item(name: String, description: String, itemType: ItemType, price: Float)

case class StoreInfo(name: String, link: String, items: Seq[Item], customers: Seq[Customer])

object PurchaseResult extends Enumeration {
  type PurchaseResult = Value
  val PurchaseSuccessful, PurchaseFailure = Value
}

class Store(storeInfo: StoreInfo) {
  private def validate(customer: Customer): Boolean = {
    if (!storeInfo.customers.exists(cust => cust.user == customer.user)) false
    else {
      val maybeOrder = customer.cart.flatMap(_.order)
      val maybeCard = customer.card
      maybeCard.nonEmpty && maybeOrder.nonEmpty
    }
  }

  def purchase(customer: Customer)
              (implicit bank: Bank): PurchaseResult = {
    if (validate(customer)) {
      val maybeOrder = customer.cart.flatMap(_.order)
      val orderTotalPrice: Double = maybeOrder.map(order => Order.getOrderTotalPrice(order)).getOrElse(0)
      val maybePaymentResponse = customer.card.map(card => CreditCardPaymentGateway.apply(card, orderTotalPrice).payment(card, orderTotalPrice))

      maybePaymentResponse match {
        case Some(PaymentResponse(PaymentGatewayStatus.Success, _)) => PurchaseResult.PurchaseSuccessful
        case Some(PaymentResponse(PaymentGatewayStatus.Failure, _)) => PurchaseResult.PurchaseFailure
        case None => PurchaseResult.PurchaseFailure
        case Some(_) => PurchaseResult.PurchaseFailure
      }
    }
    else
      PurchaseResult.PurchaseFailure
  }
}