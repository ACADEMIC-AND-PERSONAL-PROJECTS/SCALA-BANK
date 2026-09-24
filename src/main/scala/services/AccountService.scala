package me.khadimprojects
package services

import models.{Account, AccountType, Customer, Devise, Transaction}
import storage.InMemoryStorage
import models.AccountStatut.ACTIVE
import models.Devise.USD
import models.TransactionType.{DEPOSIT, WITHDRAW}

import java.util.UUID
import java.time.LocalDateTime

class AccountService:

  // Create an account
  def create(
              storage: InMemoryStorage,
              accountType: AccountType,
              devise: Devise,
              customer: Customer): Either[String, (Account, InMemoryStorage)] = {

    // Initiate account
    val account = Account(
      generateId(),
      BigDecimal(0.0),
      accountType,
      ACTIVE,
      LocalDateTime.now(),
      devise,
      customer
    )

    // Add an account into the in memory storage
    val newStorage = storage.addAccount(account)

    // Return the new account created and the storage
    Right((account, newStorage))

  }

  // Deposit to an account
  def deposit(storage: InMemoryStorage,
              accountId: String,
              amount: BigDecimal): Either[String, (Transaction, InMemoryStorage)] = {
    for {
      // Find the account
      account <- storage.findAccount(accountId).toRight("Account not found")

      // Update account
      updatedAccount = account.copy(balance = account.balance + amount)

      // Retrieve the new storage
      storage1 = storage.addAccount(updatedAccount)

      // Create a transaction
      deposit = Transaction(generateId(), accountId, amount, DEPOSIT, LocalDateTime.now(), USD)

      // New storage with all values updated
      storage2 = storage1.addTransaction(deposit)
    } yield (deposit, storage2)
  }

  // Withdraw an amount
  def withdraw(storage: InMemoryStorage,
               accountId: String,
               amount: BigDecimal): Either[String, (Transaction, InMemoryStorage)] = {
    for {
      // Find the account
      account <- storage.findAccount(accountId).toRight("Account not found")

      // Check if the remaining amount is suffisant
      _ <- if (account.balance >= amount) Right(()) else Left("Insufficient funds")

      // Then withdraw
      updatedAccount = account.copy(balance = account.balance - amount)

      // Storing the new account
      storage1 = storage.addAccount(updatedAccount)

      // Create the transaction
      withdraw = Transaction(
        generateId(),
        accountId,
        amount,
        WITHDRAW,
        LocalDateTime.now(),
        USD
      )

      // new storage
      storage2 = storage1.addTransaction(withdraw)
    } yield (withdraw, storage2)
  }

  // Generate an id
  private def generateId(): String = {
    UUID.randomUUID().toString
  }
