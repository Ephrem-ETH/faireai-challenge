package com.github.gilbertotcc.fintech.challenge;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static com.github.gilbertotcc.fintech.challenge.BalanceFixture.balanceOf;
import static com.github.gilbertotcc.fintech.challenge.BalanceFixture.transactionOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BalanceHistoryTest {

  @Test
  void createBalanceHistoryShouldSucceed() {
    // given
    var balance = balanceOf("2021-07-21", "1.12");

    // when
    var balances = new BalanceHistory(balance)
      .getBalances();

    // then
    assertThat(balances.length()).isEqualTo(1);
    assertThat(balances.get(0)).isEqualTo(balance);
  }

  @Test
  void addSingleTransactionSameDayOfReferenceShouldSucceed() {
    // given
    var balance = balanceOf("2021-07-21", "1.12");
    var transaction = transactionOf("2021-07-21", "1.23");

    // when
    var balances = new BalanceHistory(balance)
      .addTransaction(transaction)
      .getBalances();

    // then
    assertThat(balances.length()).isEqualTo(1);
    assertThat(balances.get(0)).isEqualTo(balanceOf("2021-07-21", "2.35"));
  }

  @Test
  void addSingleTransactionBeforeReferenceShouldSucceed() {
    // given
    var balance = balanceOf("2021-07-21", "1.12");
    var transaction = transactionOf("2021-07-20", "1.23");

    // when
    var balances = new BalanceHistory(balance)
      .addTransaction(transaction)
      .getBalances();

    // then
    assertThat(balances.length()).isEqualTo(2);
    assertThat(balances.get(0)).isEqualTo(balanceOf("2021-07-20", "-0.11"));
    assertThat(balances.get(1)).isEqualTo(balance);
  }

  @Test
  void addTransactionAfterReferenceShouldSucceed() {
    // given
    var balance = balanceOf("2021-07-21", "1.12");
    var transaction = transactionOf("2021-07-22", "1.23");

    // when
    var balances = new BalanceHistory(balance)
      .addTransaction(transaction)
      .getBalances();

    // then
    assertThat(balances.length()).isEqualTo(2);
    assertThat(balances.get(0)).isEqualTo(balance);
    assertThat(balances.get(1)).isEqualTo(balanceOf("2021-07-22", "2.35"));
  }

  @Test
  void addOnwardTransactionsInOrderShouldSucceed() {
    // given
    var startingBalance = balanceOf("2021-07-21", "1.23");
    var transactions = List.of(
      transactionOf("2021-07-23", "1.00"),
      transactionOf("2021-07-23", "1.00"),
      transactionOf("2021-07-24", "1.01")
    );

    // when
    var balanceHistory = new BalanceHistory(startingBalance);
    var balances = transactions.foldLeft(balanceHistory, BalanceHistory::addTransaction)
      .getBalances();

    // then
    assertThat(balances.size()).isEqualTo(3);
    assertThat(balances.get(0)).isEqualTo(balanceOf("2021-07-21", "1.23"));
    assertThat(balances.get(1)).isEqualTo(balanceOf("2021-07-23", "3.23"));
    assertThat(balances.get(2)).isEqualTo(balanceOf("2021-07-24", "4.24"));
  }

  @Test
  void addOnwardTransactionsInReverseOrderShouldSucceed() {
    // given
    var startingBalance = balanceOf("2021-07-21", "1.23");
    var transactions = List.of(
      transactionOf("2021-07-23", "1.00"),
      transactionOf("2021-07-23", "1.00"),
      transactionOf("2021-07-24", "1.01")
    ).reverse();

    // when
    var balanceHistory = new BalanceHistory(startingBalance);
    var balances = transactions.foldLeft(balanceHistory, BalanceHistory::addTransaction)
      .getBalances();

    // then
    assertThat(balances.size()).isEqualTo(3);
    assertThat(balances.get(0)).isEqualTo(balanceOf("2021-07-21", "1.23"));
    assertThat(balances.get(1)).isEqualTo(balanceOf("2021-07-23", "3.23"));
    assertThat(balances.get(2)).isEqualTo(balanceOf("2021-07-24", "4.24"));
  }

  @Test
  void addBackwardTransactionsInOrderShouldSucceed() {
    // given
    var startingBalance = balanceOf("2021-07-21", "1.23");
    var transactions = List.of(
      transactionOf("2021-07-19", "1.00"),
      transactionOf("2021-07-19", "1.00"),
      transactionOf("2021-07-18", "1.01")
    );

    // when
    var balanceHistory = new BalanceHistory(startingBalance);
    var balances = transactions.foldLeft(balanceHistory, BalanceHistory::addTransaction)
      .getBalances();

    // then
    assertThat(balances.size()).isEqualTo(3);
    assertThat(balances.get(2)).isEqualTo(balanceOf("2021-07-21", "1.23"));
    assertThat(balances.get(1)).isEqualTo(balanceOf("2021-07-19", "-0.77"));
    assertThat(balances.get(0)).isEqualTo(balanceOf("2021-07-18", "-1.78"));
  }

  @Test
  void addBackwardTransactionsInReverseOrderShouldSucceed() {
    // given
    var startingBalance = balanceOf("2021-07-21", "1.23");
    var transactions = List.of(
      transactionOf("2021-07-19", "1.00"),
      transactionOf("2021-07-19", "1.00"),
      transactionOf("2021-07-18", "1.01")
    ).reverse();

    // when
    var balanceHistory = new BalanceHistory(startingBalance);
    var balances = transactions.foldLeft(balanceHistory, BalanceHistory::addTransaction)
      .getBalances();

    // then
    assertThat(balances.size()).isEqualTo(3);
    assertThat(balances.get(2)).isEqualTo(balanceOf("2021-07-21", "1.23"));
    assertThat(balances.get(1)).isEqualTo(balanceOf("2021-07-19", "-0.77"));
    assertThat(balances.get(0)).isEqualTo(balanceOf("2021-07-18", "-1.78"));
  }
  
  
}
