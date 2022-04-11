package com.dbinterview.tradestore.controller;

import com.dbinterview.tradestore.entity.Trade;
import com.dbinterview.tradestore.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/trades")
public class TradesController {
    @Autowired
    private TradeService tradeService;

    /*process trades for saving the trades*/
    @PostMapping("/")
    public Trade processTrades(@RequestBody Trade trade) {
        log.info("inside process trade controller ");
        return tradeService.processTrades(trade);
    }

}
