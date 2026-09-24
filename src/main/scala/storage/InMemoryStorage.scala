package me.khadimprojects
package storage

import models.{Account, Transaction}

class InMemoryStorage(accounts: Map[String, Account], transactions: Map[String, List[Transaction]]):

  // Add an account to the memory
  def addAccount(account: Account): InMemoryStorage =
    new InMemoryStorage(accounts.updated(account.account_num, account), transactions)

  // Find an account by its id
  def findAccount(accountId: String): Option[Account] = {
    accounts.get(accountId)
  }

  // Get all transactions for an account
  def getTransactions(accountId: String): List[Transaction] = {
    transactions.getOrElse(accountId, List.empty)
  }

  // Add a new transaction for an account
  def addTransaction(transaction: Transaction): InMemoryStorage = {
    val updated = transactions.updatedWith(transaction.accountId) {
      case Some(list) => Some(transaction :: list)
      case None => Some(List(transaction))
    }
    new InMemoryStorage(accounts, updated)
  }