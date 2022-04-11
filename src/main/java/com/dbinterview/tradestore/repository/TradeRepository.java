package com.dbinterview.tradestore.repository;

import com.dbinterview.tradestore.entity.Trade;
import com.dbinterview.tradestore.entity.TradeIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, TradeIdentifier> {
    @Query("SELECT MAX(t.tradeIdentifier.version) from Trade t where t.tradeIdentifier.tradeId= :tradeId")
    Integer findUpdatedVersionByTradeId(@Param("tradeId") String tradeId);
}
