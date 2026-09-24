package me.khadimprojects
package services

import models.{Account, AccountType, Customer, Devise}
import storage.InMemoryStorage
import models.AccountStatut.ACTIVE

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
      val newStorage = storage.addAccount(account)
      Right((account, newStorage))

  }

  // Generate an id
  private def generateId(): String = {
    UUID.randomUUID().toString
  }
