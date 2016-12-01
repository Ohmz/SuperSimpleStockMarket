package com.example.okooheji.supersimplestockmarket.stockapi.dao;

import com.example.okooheji.supersimplestockmarket.stockapi.model.Stock;
import com.example.okooheji.supersimplestockmarket.stockapi.model.StockType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StockDaoTestConfiguration.class,loader=AnnotationConfigContextLoader.class)
public class StockDaoTest {

    @Autowired
    StockDao stockDao;

    @Test
    public void test_buildingStockRepoWorks() {
        checkCommonStock(stockDao.getStock("TEA"), "TEA", StockType.Common, 0, 0, 100);
        checkCommonStock(stockDao.getStock("POP"), "POP", StockType.Common, 8, 0, 100);
        checkCommonStock(stockDao.getStock("ALE"), "ALE", StockType.Common, 23, 0, 60);
        checkCommonStock(stockDao.getStock("GIN"), "GIN", StockType.Preferred, 8, 0.02d, 100);
        checkCommonStock(stockDao.getStock("JOE"), "JOE", StockType.Common, 13, 0, 250);
    }
    
    private void checkCommonStock(Optional<Stock> stockOptional, String symbol, StockType type, int lastDividend, double fixedDividend , int parValue) {
        assert(stockOptional.isPresent());
        Stock stock = stockOptional.get();
        assertNotNull(stock);
        assertEquals(symbol, stock.getSymbol());
        assertEquals(type, stock.getType());
        assertEquals(lastDividend, stock.getLastDividend());
        assertEquals(fixedDividend, stock.getFixedDividend(),0);
        assertEquals(parValue, stock.getParValue());
    }

}
