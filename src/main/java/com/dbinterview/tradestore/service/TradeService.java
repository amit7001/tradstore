package com.dbinterview.tradestore.service;

import com.dbinterview.tradestore.entity.Trade;
import com.dbinterview.tradestore.exception.TradeException;
import com.dbinterview.tradestore.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.dbinterview.tradestore.constants.Constants.LOWER_VERSION_OF_TRADE_IS_NOT_ALLOWED;
import static com.dbinterview.tradestore.constants.Constants.MATURITY_DATE_SHOULD_NOT_BE_LESS_THAN_TODAY_S_DATE;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Trade processTrades(Trade trade) {
        log.info("inside processTrades service");
        validateTrade(trade);
        return tradeRepository.save(trade);
    }

    private void validateTrade(final Trade trade) {
        log.info("inside validation of trades");
        Integer updatedVersion = getUpdatedVersion(trade.getTradeIdentifier().getTradeId());
        if (updatedVersion != null && updatedVersion > trade.getTradeIdentifier().getVersion()) {
            log.info("validation failed" + LOWER_VERSION_OF_TRADE_IS_NOT_ALLOWED);
            throw new TradeException(LOWER_VERSION_OF_TRADE_IS_NOT_ALLOWED);
        }
        if (LocalDate.now().isAfter(trade.getMaturityDate())) {
            log.info("validation failed" + MATURITY_DATE_SHOULD_NOT_BE_LESS_THAN_TODAY_S_DATE);
            throw new TradeException(MATURITY_DATE_SHOULD_NOT_BE_LESS_THAN_TODAY_S_DATE);
        }
    }

    private Integer getUpdatedVersion(String tradeId) {
        return tradeRepository.findUpdatedVersionByTradeId(tradeId);
    }
}
