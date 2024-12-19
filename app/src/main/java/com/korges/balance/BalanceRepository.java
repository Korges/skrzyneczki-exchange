package com.korges.balance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface BalanceRepository extends CrudRepository<Balance, UUID> {

    List<Balance> findByAccount_id(UUID accountId);

}
