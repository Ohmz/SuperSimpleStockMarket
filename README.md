# SuperSimpleStockMarket
Interview exercise for a commercial bank.

This code exposes an api which allows queries and calculations to be made on stocks, ordinarilly I'd include detailed documentation on the excercise here, but in the interest of keeping the test confidential I'm omiting the specification.

The excercise could have been completed in a small number of classes, however I opted to develop it as if i were writing production code, or at least as close to that as possible given the time scales.

The requirements stated that the project was to not use a database and instead use in memory storage. I've included a DAO with an interface which can by used to extend it to replace the in memory implementations with a db, or a webservice or whatever.

The unit tests are all in the folder: `stockApi/src/test/java` and can be run from an IDE or using maven.

The class `com.example.okooheji.supersimplestockmarket.stockapi.StockApiFactory.java` exposes a method `getStockApiInstance()` to get an instance of the API class which can be used for testing.

This factory class reads in the Spring configuration from `com.example.okooheji.supersimplestockmarket.stockapi.StockApiConfiguration` and which wires in all the appropriate Beans.

There is an example of using the class in the integration test:
`SuperSimpleStockMarket/stockApi/src/test/java/com/example/okooheji/supersimplestockmarket/stockapi/StockApiTest.java`
