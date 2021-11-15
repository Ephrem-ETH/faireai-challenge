package com.github.gilbertotcc.fintech.challenge;

import io.vavr.collection.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor
@With(value = AccessLevel.PRIVATE)
public class BalanceHistory {

	private final Balance referenceBalance;
	final List<Balance> balances = List.empty();

	public BalanceHistory addTransaction(Transaction transaction) {

		if (transaction.getBookedDate().isAfter(referenceBalance.getDate())) {
			// Add the amount of the transaction on the reference balance
			var newAmount = referenceBalance.getAmount().add(transaction.getAmount());
			balances.append(Balance.of(transaction.getBookedDate(), newAmount));
		}

		else if (transaction.getBookedDate().isBefore(referenceBalance.getDate())) {
			// Subtract the amount of the transaction from the reference balance

			var newAmount = referenceBalance.getAmount().subtract(transaction.getAmount());
			balances.append(Balance.of(transaction.getBookedDate(), newAmount));
		}

		else {
			var newAmount = referenceBalance.getAmount().add(transaction.getAmount());
			int index = IntStream.range(0, balances.size())
					.filter(i -> balances.get(i).equals(transaction.getBookedDate())).findFirst().orElse(-1);

			balances.append(balances.get(index).withAmount(newAmount));

		}

		return this;
	}

	public List<Balance> getBalances() {

		if (balances.isEmpty()) {
			balances.append(referenceBalance);

		}

		return balances;
	}

}
