package com.dbinterview.tradestore.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan("com.dbinterview.tradestore")
public class TradeStoreUpdateConfig {

}
