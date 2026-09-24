package me.khadimprojects
package models

import models.Devise.USD

import java.time.LocalDateTime

case class Transaction(
                      id: String,
                      accountId: String,
                      amount: BigDecimal,
                      typeT: TransactionType,
                      date: LocalDateTime = LocalDateTime.now(),
                      devise: Devise = USD
                      )