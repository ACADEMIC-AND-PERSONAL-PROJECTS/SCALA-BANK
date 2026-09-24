package me.khadimprojects
package models

class CommandParser:

  // Function used to parse the input of the user to a scala statement
  def commandParser(input: String): Either[String, Command] = {
    // Retrieving the user input
    val tokens = input.split(" ").toList

    // Pattern matching to redirect into the right statement
    val command = tokens match {

      // Create an account
      case List("create", accountType, devise) =>
        for {
          accountType <- toAccountType(accountType)
          devise <- toDevise(devise)
        } yield Command.CreateAccount(accountType, devise)

      // Deposit into a user account
      case List("deposit", id, amount) =>
        validAmount(amount).map(a => Command.Deposit(id, a))

      // View account
      case List("view", "account", id) => Right(Command.ViewAccount(id))

      // Withdraw from an account
      case List("withdraw", id, amount) =>
        validAmount(amount).map(a => Command.Withdraw(id, a))

      // List all account
      case List("all", "account") => Right(Command.ListAccounts())

      // Transfer amount from an account to another one
      case List("transfer", fromId, toId, amount) =>
        validAmount(amount).map(a => Command.Transfer(fromId, toId, a))

      // View all transactions
      case List("transactions", id) => Right(Command.ViewTransactions(id))

      // Close an account
      case List("close", id) => Right(Command.CloseAccount(id))

      // Exit
      case List("exit") => Right(Command.Exit)

      // If the input doesn't match any option in the menu
      case _ => Left("Veuillez revoir votre entre SVP!")

    }

    command
  }

  // Helper that transform a number into an enumeration accountType
  private def toAccountType(input: String): Either[String, AccountType] = {
    val accountType = input match {
      case "1" => Right(AccountType.valueOf("CHECKING"))
      case "2" => Right(AccountType.valueOf("SAVINGS"))
      case "3" => Right(AccountType.valueOf("BUSINESS"))
      case _ => Left("Please make sure you chose on of the number proposed!")
    }
    accountType
  }

  // Helper that transform the devise into the right enumeration value
  private def toDevise(input: String): Either[String, Devise] = {

    val devise = input match {
      case "1" => Right(Devise.valueOf("EUR"))
      case "2" => Right(Devise.valueOf("USD"))
      case "3" => Right(Devise.valueOf("XOF"))
      case "4" => Right(Devise.valueOf("GBP"))
      case "5" => Right(Devise.valueOf("JPY"))
    }

    devise
  }

  // Helper that check if the amount is positive
  private def validAmount(amount: String): Either[String, BigDecimal] = {
    try {
      val amountValue: Either[String, BigDecimal] = Either.cond(
        BigDecimal(amount) > BigDecimal(0), BigDecimal(amount), "Please enter a valid amount"
      )
      amountValue
    } catch {
      case e: Exception => Left("Invalid Amount")
    }

  }
