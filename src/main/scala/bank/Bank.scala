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
    Try(findBankAccount(accountNumber).map(_.withdraw(amount))).toOption.flatten
  }

  def deposit(accountNumber: String, amount: Double): Option[BankAccount] = {
    Try(findBankAccount(accountNumber).map(_.deposit(amount))).toOption.flatten
  }
}
