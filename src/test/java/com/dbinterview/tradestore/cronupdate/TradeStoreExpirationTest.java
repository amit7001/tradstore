package com.dbinterview.tradestore.cronupdate;

import com.dbinterview.tradestore.config.TradeStoreUpdateConfig;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;


@SpringJUnitConfig(TradeStoreUpdateConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TradeStoreExpirationTest {
    @SpyBean
    private TradeStoreUpdateExpiration storeUpdateExpiration;

    @Test
    public void testUpdateWhenExpired() {
        await().atMost(Duration.ONE_SECOND).untilAsserted(() ->
                verify(storeUpdateExpiration, atLeast(40)).updateExpiration());
    }

}