package com.korges.coin;


import com.korges.coin.dto.CoinDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cryptocurrency")
class CoinController {
    private final CoinFacade coinFacade;

    CoinController(CoinFacade coinFacade) {
        this.coinFacade = coinFacade;
    }

    @GetMapping
    ResponseEntity<List<CoinDTO>> getAllCryptocurrency() {
        List<CoinDTO> response = coinFacade.findAllCryptocurrency();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<CoinDTO> getCryptocurrency(@PathVariable String id) {
        CoinDTO response = coinFacade.findCryptocurrencyById(id);

        return ResponseEntity.ok(response);
    }

}
