package com.example.okooheji.supersimplestockmarket.stockapi.services;

import com.example.okooheji.supersimplestockmarket.stockapi.dao.StockDao;
import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.StockNotFoundException;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockFactory;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PERatioServiceTest {

    @Mock
    StockDao stockDao;

    @InjectMocks
    PERatioServiceImpl peRatioService;

    StockFactory stockFactory = new StockFactory();

    @Test
    public void test_calculatePERatio_BaseCase() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("TEA", StockType.Common, 8, 0, 100)));
        double result = peRatioService.calculatePERatio("TEA",20);
        assertEquals(2.5d,result,0);
    }

    @Test
    public void test_calculatePERatio_ZeroDividend() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("TEA", StockType.Common, 0, 0, 100)));
        double result = peRatioService.calculatePERatio("TEA",20);
        assertEquals(0.0d,result,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_calculatePERatio_EmptyStock() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("TEA", StockType.Common, 0, 0, 100)));
        double result = peRatioService.calculatePERatio("",20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_calculatePERatio_NullStock() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("TEA", StockType.Common, 0, 0, 100)));
        double result = peRatioService.calculatePERatio(null,20);
    }

    @Test(expected = StockNotFoundException.class)
    public void test_calculatePERatio_NotFound() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.empty());
        double result = peRatioService.calculatePERatio("TEA",20);
    }

}
