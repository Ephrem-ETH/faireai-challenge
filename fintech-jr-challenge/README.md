# Fintech challenges | Balance history

The challenge in this project asks to solve a common problem in the banking
sector: calculating the daily balances of a bank account from a known balance
and a set of transaction.

Indeed, given a known balance in a given date and one or more transactions, the
daily balances can be calculated summing and subtracting the amounts of the
transactions to and from the known balance.
This allows to determine the balance of the bank account at any date between the
first and the latter transaction dates.
You can find examples in the unit tests at
[BalanceHistoryTest.java](src/test/java/com/github/gilbertotcc/fintech/challenge/BalanceHistoryTest.java).

The project contains three class definitions:
- `Balance`, represents a bank account balance in a given date;
- `BalanceHistory`, represents the history of daily balances;
- `Transaction`, is a simple model for a bank transaction.

The challenge asks to implement public methods `BalanceHistory::addTransaction`
and `BalanceHistory::getBalances`.
`BalanceHistory::addTransaction`, given a transaction, returns a new  instance
of a `BalanceHistory` that contains the changes of the balance in the maximum
computable time interval.
`BalanceHistory::getBalances` returns all the balance changes in the instance of
the `BalanceHistory`.

## Usage

The challenge is defined in a Gradle project.
To build and test the results of your code, run the following command from the
project root.

```sh
$ ./gradlew check
```
