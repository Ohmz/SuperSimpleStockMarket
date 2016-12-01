package com.example.okooheji.supersimplestockmarket.stockapi.model;

import com.example.okooheji.supersimplestockmarket.stockapi.exceptions.InvalidStockEntryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StockFactoryTestConfiguration.class,loader=AnnotationConfigContextLoader.class)
public class StockFactoryTest {


    @Autowired
    StockFactory stockFactory;

    @Test
    public void test_CreateStock_ValidCommon() throws InvalidStockEntryException {
        Stock stock = stockFactory.createStock("TST,Common,1,,100");
        assertEquals("TST",stock.getSymbol());
        assertEquals(StockType.Common,stock.getType());
        assertEquals(1,stock.getLastDividend());
        assertEquals(0.0d,stock.getFixedDividend()); // We didn't specify this.
        assertEquals(100,stock.getParValue());
    }

    @Test
    public void test_CreateStockValid_Preferred() throws InvalidStockEntryException {
        Stock stock = stockFactory.createStock("TST,Preferred,1,4,100");
        assertEquals("TST",stock.getSymbol());
        assertEquals(StockType.Preferred,stock.getType());
        assertEquals(1,stock.getLastDividend());
        assertEquals(0.04,stock.getFixedDividend()); // 4% is 0.04
        assertEquals(100,stock.getParValue());
    }

    @Test(expected = InvalidStockEntryException.class)
    public void test_CreateStock_PreferredNoFixedDividend() throws InvalidStockEntryException {
        Stock stock = stockFactory.createStock("TST,Preferred,1,,100");
    }

    @Test(expected = InvalidStockEntryException.class)
    public void test_CreateStock_CommonUnparsableDividend() throws InvalidStockEntryException {
        Stock stock = stockFactory.createStock("TST,Common,T,,100");
    }

    @Test(expected = InvalidStockEntryException.class)
    public void test_CreateStock_TooFewItemsInCSV() throws InvalidStockEntryException {
        Stock stock = stockFactory.createStock("TST,Preferred,1,4");
    }
}
