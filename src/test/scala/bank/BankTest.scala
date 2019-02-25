package bank

import org.scalatest.{Matchers, WordSpec}

class BankTest extends WordSpec with Matchers {
  implicit val bank: OrangeBank = new OrangeBank(Seq(
    BankAccount("abc", 1000),
    BankAccount("abcd", 100),
    BankAccount("abcde", 200)
  ))

  "A Bank" when {
    "calling findBankAccount with a valid bankAccount" should {
      "return the bankAccount" in {
        bank.findBankAccount("abc") shouldBe Some(BankAccount("abc", 1000))
      }
    }

    "calling findBankAccount with an invalid bankAccount" should {
      "return no bankAccount" in {
        bank.findBankAccount("cat") shouldBe None
      }
    }

    "calling deposit with a valid bankAccount" should {
      "call deposit on that bankAccount" in {
        bank.deposit("abc", 12) shouldBe Some(BankAccount("abc", 1012))
      }
    }

    "calling deposit with an invalid bankAccount" should {
      "not call deposit on that bankAccount" in {
        bank.deposit("cat", 12) shouldBe None
      }
    }

    "calling deposit with a valid bankAccount and a negative value" should {
      "not do a deposit" in {
        bank.deposit("abc", -12) shouldBe None
      }
    }

    "calling withdraw with a valid bankAccount" should {
      "call withdraw on that bankAccount" in {
        bank.withdraw("abc", 12) shouldBe Some(BankAccount("abc", 988))
      }
    }

    "calling withdraw with an invalid bankAccount" should {
      "not call withdraw on that bankAccount" in {
        bank.withdraw("cat", 12) shouldBe None
      }
    }

    "calling withdraw with a valid bankAccount and a value greater then the current balance" should {
      "not do a withdraw" in {
        bank.withdraw("abc", 1001) shouldBe None
      }
    }
  }
}
