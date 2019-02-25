package paymentGateway

import bank.Bank
import ipsgateway.IPSProcessor
import paymentGateway.PaymentGatewayStatus.PaymentGatewayStatus
import store.CreditCard

object PaymentGatewayStatus extends Enumeration {
  type PaymentGatewayStatus = Value
  val Success, Failure = Value
}

case class PaymentResponse(status: PaymentGatewayStatus, message: String)

trait PaymentGateway {
  def payment(creditCard: CreditCard, amount: Double)
             (implicit bank: Bank): PaymentResponse
}

object CreditCardPaymentGateway {
  def apply(creditCard: CreditCard, amount: Double): IPSProcessor = {
    IPSProcessor.apply(creditCard, amount)
  }
}