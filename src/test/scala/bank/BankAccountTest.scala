package bank

import org.scalatest.{Matchers, WordSpec}

class BankAccountTest extends WordSpec with Matchers {
  "A bank account" when {
    "calling deposit with a positive value" should {
      "deposit the value" in {
        val bankAccount = BankAccount("134", 10)
        val bankAccountWithDeposit = bankAccount.deposit(21)

        bankAccountWithDeposit.balance shouldBe 31
      }
    }

    "calling deposit with a negative value" should {
      "fail precondition" in {
        val bankAccount = BankAccount("134", 10)
        a[IllegalArgumentException] should be thrownBy {
          bankAccount.deposit(-22)
        }
      }
    }

    "calling deposit with a 0 value" should {
      "fail precondition" in {
        val bankAccount = BankAccount("134", 10)
        a[IllegalArgumentException] should be thrownBy {
          bankAccount.deposit(0)
        }
      }
    }

    "calling withdraw with an available value in the account balance" should {
      "withdraw the value" in {
        val bankAccount = BankAccount("134", 100)
        val bankAccountWithDeposit = bankAccount.withdraw(30)

        bankAccountWithDeposit.balance shouldBe 70
      }
    }

    "calling withdraw with an unavailable value in the account balance" should {
      "fail precondition" in {
        val bankAccount = BankAccount("134", 10)
        a[IllegalArgumentException] should be thrownBy {
          bankAccount.withdraw(22)
        }
      }
    }

    "calling withdra with a 0 value" should {
      "withdraw the value 0" in {
        val bankAccount = BankAccount("134", 10)
        val bankAccountWithDeposit = bankAccount.withdraw(0)

        bankAccountWithDeposit.balance shouldBe 10
      }
    }
  }
}
