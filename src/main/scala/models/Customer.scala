package me.khadimprojects
package models

case class Customer(
                   id: Int,
                   name: String,
                   phone_number: String,
                   email: String,
                   login: String,
                   password: String
                   )