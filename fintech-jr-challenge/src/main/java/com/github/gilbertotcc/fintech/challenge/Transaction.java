package com.github.gilbertotcc.fintech.challenge;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value(staticConstructor = "of")
@EqualsAndHashCode
@ToString
public class Transaction {
  LocalDate bookedDate;
  BigDecimal amount;
}
