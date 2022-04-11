package com.dbinterview.tradestore.cronupdate;

import com.dbinterview.tradestore.entity.Trade;
import com.dbinterview.tradestore.repository.TradeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/* update the expired flag based  cron expression to validate the if the existing record match the condition  */
@Component
@AllArgsConstructor
@Slf4j
public class TradeStoreUpdateExpiration {
    private final TradeRepository tradeRepository;

    @Scheduled(fixedDelay = 10)
    public void updateExpiration() {
        tradeRepository.findAll().forEach(trade -> {
            if (isExpired(trade)) {
                trade.setExpired('Y');
                log.info("Trade is expired", trade);
                tradeRepository.save(trade);
            }
        });
    }

    private boolean isExpired(@NotNull Trade trade) {
        return trade.getMaturityDate().isBefore(LocalDate.now());
    }

}
