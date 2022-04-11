package com.dbinterview.tradestore.service;

import com.dbinterview.tradestore.entity.Trade;
import com.dbinterview.tradestore.entity.TradeIdentifier;
import com.dbinterview.tradestore.exception.TradeException;
import com.dbinterview.tradestore.repository.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.dbinterview.tradestore.constants.Constants.LOWER_VERSION_OF_TRADE_IS_NOT_ALLOWED;
import static com.dbinterview.tradestore.constants.Constants.MATURITY_DATE_SHOULD_NOT_BE_LESS_THAN_TODAY_S_DATE;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;

    TradeService tradeService;

    @BeforeEach
    void setUp() {
        tradeService = new TradeService(tradeRepository);
    }

    @Test
    public void testProcessTrades() {
        Mockito.when(tradeRepository.save(any(Trade.class))).thenReturn(getTradeObject(1));
        Mockito.when(tradeRepository.findUpdatedVersionByTradeId("T1")).thenReturn((1));
        Trade returnedTradeObj = tradeService.processTrades(getTradeRequestForVersion(1));
        Assertions.assertTrue(returnedTradeObj.getTradeIdentifier().getTradeId().equalsIgnoreCase("T1"));
    }

    @Test
    public void testProcessTradesMultipleVersion() {
        Mockito.when(tradeRepository.save(any(Trade.class))).thenReturn(getTradeObject(1));
        Trade response = tradeService.processTrades(getTradeRequestForVersion(1));

        Assertions.assertTrue(response.getTradeIdentifier().getTradeId().equalsIgnoreCase("T1"));
        Assertions.assertEquals(response.getTradeIdentifier().getVersion(), 1);

        Mockito.when(tradeRepository.save(any(Trade.class))).thenReturn(getTradeObject(2));
        Trade response1 = tradeService.processTrades(getTradeRequestForVersion(2));

        Assertions.assertTrue(response1.getTradeIdentifier().getTradeId().equalsIgnoreCase("T1"));
        Assertions.assertEquals(response1.getTradeIdentifier().getVersion(), 2);
    }

    @Test
    public void testProcessTradesForMaturityDate() {
        Trade t1 = new Trade(new TradeIdentifier("T1", 1), "CP-1", "B1",
                LocalDate.parse("2022-04-05"), LocalDate.now(), 'N');
        TradeException tradeException = Assertions.assertThrows(TradeException.class, () -> tradeService.processTrades(t1), "");
        Assertions.assertEquals(MATURITY_DATE_SHOULD_NOT_BE_LESS_THAN_TODAY_S_DATE, tradeException.getMessage());
    }

    @Test
    public void testProcessTradesLowerVersionNotAllowed() {
        Mockito.when(tradeRepository.findUpdatedVersionByTradeId("T1")).thenReturn((4));
        TradeException tradeException = Assertions.assertThrows(TradeException.class, () -> tradeService.processTrades(getTradeObject(3)), "");
        Assertions.assertEquals(LOWER_VERSION_OF_TRADE_IS_NOT_ALLOWED, tradeException.getMessage());
    }

    private Trade getTradeObject(int version) {
        return Trade.builder()
                .tradeIdentifier(
                        TradeIdentifier.builder()
                                .tradeId("T1")
                                .version(version)
                                .build()
                )
                .expired('N')
                .maturityDate(LocalDate.parse("2022-04-28"))
                .createdDate(LocalDate.now())
                .bookId("B1")
                .counterPartyId("CP-1")
                .build();
    }

    private Trade getTradeRequestForVersion(int version) {
        return new Trade(new TradeIdentifier("T1", version), "CP-1", "B1",
                LocalDate.parse("2022-04-28"), LocalDate.now(), 'N');
    }

}