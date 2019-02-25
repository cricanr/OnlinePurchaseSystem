package bank

case class BankAccount(accountNumber: String, balance: Double) {
  def deposit(amount: Double): BankAccount = {
    require(amount > 0)
    BankAccount(accountNumber, balance + amount)
  }

  def withdraw(amount: Double): BankAccount = {
    require(balance - amount >= 0)
    BankAccount(accountNumber, balance - amount)
  }
}