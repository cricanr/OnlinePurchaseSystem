package ipsgateway

import bank.Bank
import ipsgateway.IPSPaymentStatus.IPSPaymentStatus
import paymentGateway.{PaymentGatewayStatus, PaymentResponse}
import store.{CreditCard, CreditCardType}

object IPSPaymentStatus extends Enumeration {
  type IPSPaymentStatus = Value
  val Success, Failure = Value
}

case class IPSPaymentResponse(status: IPSPaymentStatus, message: String)

trait IPSProcessor {
  def payment(creditCard: CreditCard, amount: Double)
             (implicit bank: Bank): PaymentResponse = {
    val maybeBankAccount = if (amount < 0)
      bank.withdraw(creditCard.accountNumber, -amount)
    else
      bank.deposit(creditCard.accountNumber, amount)

    maybeBankAccount.map(bankAccount => PaymentResponse(PaymentGatewayStatus.Success, s"transaction successful, new balance: ${bankAccount.balance}"))
      .getOrElse(PaymentResponse(PaymentGatewayStatus.Failure, "transaction failed"))
  }
}

object IPSProcessor {
  def apply(creditCard: CreditCard, amount: Double): IPSProcessor = {
    creditCard.cardType match {
      case CreditCardType.Visa => VisaProcessor
      case CreditCardType.Maestro => MaestroProcessor
      case CreditCardType.MasterCard => MastercardProcessor
    }
  }
}

case object VisaProcessor extends IPSProcessor

case object MaestroProcessor extends IPSProcessor

case object MastercardProcessor extends IPSProcessor