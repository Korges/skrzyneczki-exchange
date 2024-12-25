package com.korges.balance;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

interface BalanceRepository extends CrudRepository<Balance, UUID> {

    List<Balance> findByAccount_id(UUID accountId);

}
