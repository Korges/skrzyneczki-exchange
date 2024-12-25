package com.korges.balance;

import com.korges.account.Account;
import com.korges.coin.Coin;

import java.math.BigDecimal;
import java.util.UUID;

record BalanceDTO(UUID id, Account account, Coin coin, BigDecimal balance) {
}
