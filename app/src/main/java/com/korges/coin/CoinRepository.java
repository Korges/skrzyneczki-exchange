package com.korges.coin;

import org.springframework.data.repository.CrudRepository;

interface CoinRepository extends CrudRepository<Coin, String> {
}
