package com.korges.account;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/account")
class AccountController {
    private final AccountFacade accountFacade;

    AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping
    ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO account) {
        AccountDTO result = accountFacade.addAccount(account);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    ResponseEntity<AccountDTO> getAccount(@PathVariable UUID uuid) {
        AccountDTO result = accountFacade.findAccountById(uuid);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> deleteAccount(@PathVariable UUID uuid) {
        accountFacade.deleteAccountById(uuid);
        return ResponseEntity.ok().build();
    }

}
