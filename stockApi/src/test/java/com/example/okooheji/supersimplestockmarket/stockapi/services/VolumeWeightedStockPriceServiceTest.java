package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.TradeDao;
import com.example.okooheji.supersimplestockmarket.stockapi.model.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VolumeWeightedStockPriceServiceTest {

    @Mock
    TradeDao tradeDao;

    @Mock
    TimeNowProvider timeNowProvider;

    @InjectMocks
    VolumeWeightedStockPriceServiceImpl volumeWeightedStockPriceService;

    @Test
    public void test_calculateVolumeWeightedStockPrice_allGood() {
        when(timeNowProvider.getTimeNow()).thenReturn(100l);
        when(tradeDao.getTradesWithinFifteenMinutesOfTimeStamp("TEA",100)).thenReturn(getDummyListOfTrades());
        double result = volumeWeightedStockPriceService.calculateVolumeWeightedStockPrice("TEA");
        assertEquals(2.0d, result,0);

    }

    @Test
    public void test_calculateVolumeWeightedStockPrice_EmptyList() {
        when(timeNowProvider.getTimeNow()).thenReturn(100l);
        when(tradeDao.getTradesWithinFifteenMinutesOfTimeStamp("TEA",100)).thenReturn(new ArrayList<>());
        double result = volumeWeightedStockPriceService.calculateVolumeWeightedStockPrice("TEA");
        assertEquals(0.0d, result,0);

    }


    private List<Trade> getDummyListOfTrades() {
        List<Trade> list = new ArrayList<>();
        list.add(new Trade("TEA",101, 5, 2));
        list.add(new Trade("TEA",103, 4, 2));
        return list;
    }


}
