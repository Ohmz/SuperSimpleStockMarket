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
public class DividendYieldServiceTest {


    @Mock
    StockDao stockDao;

    @InjectMocks
    DividendYieldServiceImpl dividendYieldService;

    StockFactory stockFactory = new StockFactory();


    @Test
    public void test_calculateDividendYield_Common() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("RUM", StockType.Common, 8, 0, 100)));
        double result = dividendYieldService.calculateDividendYield("RUM",50);
        assertEquals(0.16d,result,0);
    }

    @Test
    public void test_calculateDividendYield_Preferred() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("FIZ",StockType.Preferred, 50, 0.02, 100)));
        double result = dividendYieldService.calculateDividendYield("FIZ",50);
        assertEquals(0.04d, result,0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_calculateDividendYield_ZeroPrice() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("RUM", StockType.Common, 8, 0, 100)));
        double result = dividendYieldService.calculateDividendYield("RUM",0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_calculateDividendYield_NullSymbol() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("RUM", StockType.Common, 8, 0, 100)));
        double result = dividendYieldService.calculateDividendYield(null,50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_calculateDividendYield_EmptySymbol() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.of(stockFactory.createStock("RUM", StockType.Common, 8, 0, 100)));
        double result = dividendYieldService.calculateDividendYield("",50);
    }

    @Test(expected = StockNotFoundException.class)
    public void test_calculateDividendYield_StockNotFound() throws StockNotFoundException {
        when(stockDao.getStock(anyString())).thenReturn(Optional.empty());
        double result = dividendYieldService.calculateDividendYield("RUM",50);
    }
}
