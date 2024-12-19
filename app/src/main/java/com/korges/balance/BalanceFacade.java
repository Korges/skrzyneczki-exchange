package com.korges.balance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
class BalanceFacade {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BalanceRepository balanceRepository;

    BalanceFacade(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    List<BalanceDTO> getBalanceByAccountId(UUID uuid) {
        return balanceRepository.findByAccount_id(uuid).stream()
                .map(cryptocurrency -> objectMapper.convertValue(cryptocurrency, BalanceDTO.class))
                .toList();
    }
}
