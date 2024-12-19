package com.korges.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface AccountRepository extends CrudRepository<Account, UUID> {

}
