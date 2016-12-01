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
public class AllShareIndexServiceTest {

    @Mock
    TradeDao tradeDao;

    @InjectMocks
    AllShareIndexServiceImpl allShareIndexService;


    @Test
    public void test_calculateAllShareIndex_TwoItems() {
        when(tradeDao.getLastTradeForEachStock()).thenReturn(getDummyTradeListTwoItems());
        double asi = allShareIndexService.calculateAllShareIndex();
        assertEquals(4.0d, asi,0); // 2*8 = 16, 16 ^ (1/2) = 4
    }


    @Test
    public void test_calculateAllShareIndex_FourItems() {
        when(tradeDao.getLastTradeForEachStock()).thenReturn(getDummyTradeListFourItems());
        double asi = allShareIndexService.calculateAllShareIndex();
        assertEquals(2.0d, asi,0); // 2*2*2*2 = 16, 16 ^ (1/4) = 2
    }

    @Test
    public void test_calculateAllShareIndex_EmptyList() {
        when(tradeDao.getLastTradeForEachStock()).thenReturn(new ArrayList<Trade>());
        double asi = allShareIndexService.calculateAllShareIndex();
        assertEquals(0.0d, asi,0);
    }


    private List<Trade> getDummyTradeListTwoItems() {
        List<Trade> list = new ArrayList<>();
        list.add(new Trade("GIN",100, 10, 2));
        list.add(new Trade("TEA",101, 5, 8));
        return list;
    }

    private List<Trade> getDummyTradeListFourItems() {
        List<Trade> list = new ArrayList<>();
        list.add(new Trade("GIN",100, 10, 2));
        list.add(new Trade("TEA",101, 5, 2));
        list.add(new Trade("ICE",102, 3, 2));
        list.add(new Trade("RUM",103, 4, 2));
        return list;
    }

}
