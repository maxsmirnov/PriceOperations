import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by maxsmirnov on 30.08.2016.
 */

public class PriceOperationsTest {
    private PriceOperations priceOperations = new PriceOperations();
    private Collection<ProductPrice> currentPrices = new ArrayList<>();
    private Collection<ProductPrice> newPrices = new ArrayList<>();
    private Collection<ProductPrice> resultCollection = new ArrayList<>();
    private Collection<ProductPrice> expectCollection = new ArrayDeque<>();

    @Test
    public void should_merge_dates() throws Exception {
        //currentPrices = new ArrayList<>();
        currentPrices.add(new ProductPrice(1, "122856", 1, 1, LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 23, 59, 59), 11000));
        currentPrices.add(new ProductPrice(2, "122856", 2, 1, LocalDateTime.of(2013, 1, 10, 0, 0, 0),
                LocalDateTime.of(2013, 1, 20, 23, 59, 59), 99000));
        currentPrices.add(new ProductPrice(3, "6654", 1, 2, LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 0, 0, 0), 5000));

        newPrices.add(new ProductPrice(4, "122856", 1, 1, LocalDateTime.of(2013, 1, 20, 0, 0, 0),
                LocalDateTime.of(2013, 2, 20, 23, 59, 59), 11000));
        newPrices.add(new ProductPrice(5, "122856", 2, 1, LocalDateTime.of(2013, 1, 15, 0, 0, 0),
                LocalDateTime.of(2013, 1, 25, 23, 59, 59), 92000));
        newPrices.add(new ProductPrice(6, "6654", 1, 2, LocalDateTime.of(2013, 1, 12, 0, 0, 0),
                LocalDateTime.of(2013, 1, 13, 0, 0, 0), 4000));

        expectCollection.add(new ProductPrice(3, "6654", 1, 2, LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 1, 12, 0, 0, 0), 5000));
        expectCollection.add(new ProductPrice(3, "6654", 1, 2, LocalDateTime.of(2013, 1, 12, 0, 0, 0),
                LocalDateTime.of(2013, 1, 13, 0, 0, 0), 4000));
        expectCollection.add(new ProductPrice(3, "6654", 1, 2, LocalDateTime.of(2013, 1, 13, 0, 0, 0),
                LocalDateTime.of(2013, 1, 31, 0, 0, 0), 5000));

        expectCollection.add(new ProductPrice(1, "122856", 1, 1, LocalDateTime.of(2013, 1, 1, 0, 0, 0),
                LocalDateTime.of(2013, 2, 20, 23, 59, 59), 11000));
        expectCollection.add(new ProductPrice(2, "122856", 2, 1, LocalDateTime.of(2013, 1, 10, 0, 0, 0),
                LocalDateTime.of(2013, 1, 15, 0, 0, 0), 99000));
        expectCollection.add(new ProductPrice(2, "122856", 2, 1, LocalDateTime.of(2013, 1, 15, 0, 0, 0),
                LocalDateTime.of(2013, 1, 25, 23, 59, 59), 92000));

        resultCollection = priceOperations.merge(currentPrices, newPrices);

        assertEquals(resultCollection.size(),expectCollection.size());

       for (ProductPrice elm : resultCollection) {
           ProductPrice expectElm = ((ArrayDeque<ProductPrice>)expectCollection).poll();
           assertTrue((elm.getProductCode()).equals(expectElm.getProductCode()));
           assertTrue((elm.getNumber())==(expectElm.getNumber()));
           assertTrue((elm.getDepart())==(expectElm.getDepart()));
           assertTrue((elm.getValue()) ==(expectElm.getValue()));
           assertTrue((elm.getBegin()) .equals (expectElm.getBegin()));
           assertTrue((elm.getEnd()).equals(expectElm.getEnd()));
       }
    }

}
