package me.khadimprojects
package models

enum Command:
  case CreateAccount(accountType: AccountType, devise: Devise)
  case ViewAccount(accountId: String)
  case ListAccounts()
  case Deposit(accountId: String, amount: BigDecimal)
  case Withdraw(accountId: String, amount: BigDecimal)
  case Transfer(fromId: String, toId: String, amount: BigDecimal)
  case ViewTransactions(accountId: String)
  case ConvertCurrency(accountId: String, targetDevise: Devise)
  case CloseAccount(accountId: String)
  case Exit