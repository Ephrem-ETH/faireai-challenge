package com.github.gilbertotcc.fintech.challenge;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class BalanceFixture {

  static Transaction transactionOf(String bookedDateInIso8601, String amountInEur) {
    LocalDate bookedDate = LocalDate.parse(bookedDateInIso8601);
    BigDecimal amount = new BigDecimal(amountInEur);
    return Transaction.of(bookedDate, amount);
  }

  static Balance balanceOf(String dateInIso8601, String amountInEur) {
    LocalDate date = LocalDate.parse(dateInIso8601);
    BigDecimal amount = new BigDecimal(amountInEur);
    return Balance.of(date, amount);
  }
}
