package me.khadimprojects
package models

import java.time.LocalDateTime

case class Account(
                    account_num: String,
                    balance: BigDecimal,
                    account_type: AccountType,
                    statut: AccountStatut,
                    created_at: LocalDateTime,
                    devise: Devise,
                    customer: Customer
                  )
