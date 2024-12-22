package com.korges.coin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CoinRepository extends CrudRepository<Coin, String> {
}
