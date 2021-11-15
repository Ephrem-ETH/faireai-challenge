package com.github.gilbertotcc.fintech.challenge;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BalanceTest {

  @Test
  void createBalanceHistoryFromBalanceShouldSucceed() {
    // given
    Balance balance = Balance.of(LocalDate.of(2021, 8, 9), new BigDecimal("1.23"));
    Transaction transaction = Transaction.of(LocalDate.of(2021, 8, 10), new BigDecimal("1.00"));

    // when
    var balances = balance
      .addTransaction(transaction)
      .getBalances();

    // then
    assertThat(balances.get(1)).isEqualTo(Balance.of(LocalDate.of(2021, 8, 10), new BigDecimal("2.23")));
  }
}
