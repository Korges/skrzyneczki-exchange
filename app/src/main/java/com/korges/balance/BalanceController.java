package com.korges.balance;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/balance")
class BalanceController {

    private final BalanceFacade balanceFacade;

    BalanceController(BalanceFacade balanceFacade) {
        this.balanceFacade = balanceFacade;
    }

    @GetMapping("/{uuid}")
    ResponseEntity<List<BalanceDTO>> getBalanceByAccountId(@PathVariable UUID uuid) {
        List<BalanceDTO> result = balanceFacade.getBalanceByAccountId(uuid);

        return ResponseEntity.ok(result);
    }


}
