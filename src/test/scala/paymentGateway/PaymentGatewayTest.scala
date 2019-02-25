package paymentGateway

import ipsgateway.MastercardProcessor
import org.scalatest.{Matchers, WordSpec}
import store.{CreditCard, CreditCardType}

class PaymentGatewayTest extends WordSpec with Matchers {
  "The PaymentGateway" when {
    "calling a payment with a CC" should {
      "return an IPSProcessor for the CC" in {
        CreditCardPaymentGateway.apply(CreditCard(CreditCardType.MasterCard, "yogi11", "1231", "121"), 12) shouldBe MastercardProcessor
      }
    }
  }
}
