package com.korges.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class AccountFacade {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AccountRepository accountRepository;

    AccountFacade(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    AccountDTO addAccount(AccountDTO accountDTO) {
        Account address = objectMapper.convertValue(accountDTO, Account.class);
        Account result = accountRepository.save(address);
        return objectMapper.convertValue(result, AccountDTO.class);
    }

    AccountDTO findAccountById(UUID uuid) {
        Account result = accountRepository.findById(uuid).orElseThrow(EntityNotFoundException::new);
        return objectMapper.convertValue(result, AccountDTO.class);
    }

    void deleteAccountById(UUID uuid) {
        accountRepository.deleteById(uuid);
    }
}
