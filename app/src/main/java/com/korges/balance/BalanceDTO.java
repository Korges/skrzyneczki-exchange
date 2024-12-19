package com.korges.balance;

import com.korges.account.Account;
import com.korges.cryptocurrency.Coin;

import java.math.BigDecimal;
import java.util.UUID;

public record BalanceDTO(UUID id, Account account, Coin coin, BigDecimal balance) {
}
