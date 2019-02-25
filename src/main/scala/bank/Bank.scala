package bank

import scala.util.Try

trait Bank {
  def findBankAccount(accountNumber: String): Option[BankAccount]

  def withdraw(accountNumber: String, amount: Double): Option[BankAccount]

  def deposit(accountNumber: String, amount: Double): Option[BankAccount]
}

class OrangeBank(bankAccounts: Seq[BankAccount]) extends Bank {
  def findBankAccount(accountNumber: String): Option[BankAccount] = {
    bankAccounts.find(bankAccount => bankAccount.accountNumber == accountNumber)
  }

  def withdraw(accountNumber: String, amount: Double): Option[BankAccount] = {
    val maybeBankAccount = findBankAccount(accountNumber)
    val maybeUpdatedBankAccount = maybeBankAccount.flatMap(bankAccount => Try(bankAccount.withdraw(amount)).toOption)

    updateBankAccount(maybeBankAccount, maybeUpdatedBankAccount)

    maybeUpdatedBankAccount
  }

  def deposit(accountNumber: String, amount: Double): Option[BankAccount] = {
    val maybeBankAccount = findBankAccount(accountNumber)
    val maybeUpdatedBankAccount = maybeBankAccount.flatMap(bankAccount => Try(bankAccount.deposit(amount)).toOption)

    updateBankAccount(maybeBankAccount, maybeUpdatedBankAccount)

    maybeUpdatedBankAccount
  }

  private def updateBankAccount(maybeBankAccount: Option[BankAccount], maybeUpdatedBankAccount: Option[BankAccount]) = {
    for {
      bankAccount <- maybeBankAccount
      updatedBankAccount <- maybeUpdatedBankAccount
    } yield bankAccounts.updated(bankAccounts.indexOf(bankAccount), updatedBankAccount)
  }
}
