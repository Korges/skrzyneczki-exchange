package com.korges.account;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface AccountRepository extends CrudRepository<Account, UUID> {

}
