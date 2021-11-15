package com.github.gilbertotcc.fintech.challenge;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
@EqualsAndHashCode
@ToString
public class Balance {

  LocalDate date;
  @With
  BigDecimal amount;

  public BalanceHistory addTransaction(Transaction transaction) {
    return new BalanceHistory(this).addTransaction(transaction);
  }
}
