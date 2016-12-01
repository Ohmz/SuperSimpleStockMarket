package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class TradeSaveServiceTest {

    @Mock
    TradeDao tradeDao;

    @InjectMocks
    TradeSaveServiceImpl tradeSaveService;

    @Test
    public void test_saveTrade() {
        tradeSaveService.saveTrade("TEA", 10000000l, 4 , 10);
        verify(tradeDao,times(1)).recordTrade("TEA", 10000000l, 4, 10);
    }

}
