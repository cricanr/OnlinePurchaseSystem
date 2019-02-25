package ipsgateway

import bank.{BankAccount, OrangeBank}
import org.scalatest.{Matchers, WordSpec}
import paymentGateway.{PaymentGatewayStatus, PaymentResponse}
import store.{CreditCard, CreditCardType}

class IPSProcessorTest extends WordSpec with Matchers {
  "The IPSGateway" when {
    "giving a VISA CC" should {
      "return a VisaProcessor" in {
        IPSProcessor.apply(CreditCard(CreditCardType.Visa, "yogi", "1231", "121"), 12) shouldBe VisaProcessor
      }
    }

    "giving a Maestro CC" should {
      "return a MaestroProcessor" in {
        IPSProcessor.apply(CreditCard(CreditCardType.Maestro, "yogi1", "1231", "121"), 12) shouldBe MaestroProcessor
      }
    }

    "giving a MasterCard CC" should {
      "return a VisaProcessor" in {
        IPSProcessor.apply(CreditCard(CreditCardType.MasterCard, "yogi11", "1231", "121"), 12) shouldBe MastercardProcessor
      }
    }

    implicit val bank: OrangeBank = new OrangeBank(Seq(
      BankAccount("abc", 1000),
      BankAccount("abcd", 100),
      BankAccount("abcde", 200)
    ))

    "calling payment with a CC and a positive amount on a valid account" should {
      "do a deposit" in {
        val response = VisaProcessor.payment(CreditCard(CreditCardType.Visa, "yogi", "abc", "121"), 12)
        response shouldBe PaymentResponse(PaymentGatewayStatus.Success, s"transaction successful, new balance: 1012.0")
      }
    }

    "calling payment with a CC and a positive amount on an invalid account" should {
      "not do a deposit and return a failure status" in {
        val response = VisaProcessor.payment(CreditCard(CreditCardType.Visa, "yogi", "cat", "121"), 12)
        response shouldBe PaymentResponse(PaymentGatewayStatus.Failure, s"transaction failed")
      }
    }

    "calling payment with a CC and a negative amount on a valid account" should {
      "do a withdraw" in {
        val response = VisaProcessor.payment(CreditCard(CreditCardType.Visa, "yogi", "abc", "121"), -30)
        response shouldBe PaymentResponse(PaymentGatewayStatus.Success, s"transaction successful, new balance: 970.0")
      }
    }

    "calling payment with a CC and a negative amount on an invalid account" should {
      "not do a withdraw and return a failure status" in {
        val response = VisaProcessor.payment(CreditCard(CreditCardType.Visa, "yogi", "cat", "121"), -112)
        response shouldBe PaymentResponse(PaymentGatewayStatus.Failure, s"transaction failed")
      }
    }
  }
}
