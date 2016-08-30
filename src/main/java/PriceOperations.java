import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by maxsmirnov on 29.08.2016.
 */

public class PriceOperations {

    public PriceOperations() {

    }

    /**
     * @param currentPrices коллекция текущих цен
     * @param newPrices     коллекция новых цен
     * @return возвращает коллекцию объединенных цен, осортированную в естественном порядке
     */

    public Collection<ProductPrice> merge(Collection<ProductPrice> currentPrices, Collection<ProductPrice> newPrices) {

        Objects.requireNonNull(currentPrices);
        Objects.requireNonNull(newPrices);

        Set<ProductPrice> allPrices = new TreeSet<>(currentPrices);
        allPrices.addAll(newPrices);

        Set<ProductPrice> resultCollection = new TreeSet<>();

        ProductPrice previousProductPrice = null;

        for (ProductPrice elm : allPrices) {
            if (!(previousProductPrice == null)) resultCollection.addAll(mergeElement(previousProductPrice, elm));
            previousProductPrice = elm;
        }

        return resultCollection;
    }

    /**
     * @param priceElement1
     * @param priceElement2
     * @return возвращает коллекцию созданную на основе объединения двух элементов с совпадающими номерами и отделами
     * для совпадающих или отличающихся значений цен
     */

    public Collection<ProductPrice> mergeElement(ProductPrice priceElement1, ProductPrice priceElement2) {

        Objects.requireNonNull(priceElement1);
        Objects.requireNonNull(priceElement2);

        Collection<ProductPrice> result = new ArrayList<>();
        if (priceElement1.equalPriceValues(priceElement2)) {
            LocalDateTime mergeDateBegin;
            LocalDateTime mergeDateEnd;
            mergeDateBegin = least(priceElement1.getBegin(), priceElement2.getBegin());
            mergeDateEnd = largest(priceElement1.getEnd(), priceElement2.getEnd());
            result.add(new ProductPrice(priceElement1.getId(),
                    priceElement1.getProductCode(),
                    priceElement1.getNumber(),
                    priceElement1.getDepart(),
                    mergeDateBegin,
                    mergeDateEnd,
                    priceElement1.getValue()));
            return result;
        }

        if (priceElement1.equalNumberAndDepartment(priceElement2)) {

            Collection<LocalDateTime> dateCollection = dateIntersection(priceElement1.getBegin(),
                    priceElement1.getEnd(), priceElement2.getBegin(), priceElement2.getEnd());
            long currentPriceValue = priceElement2.getValue();
            LocalDateTime previousElmDate = null;
            for (LocalDateTime elmDate : dateCollection) {
                if (!(previousElmDate == null)) {
                    result.add(new ProductPrice(priceElement1.getId(),
                            priceElement1.getProductCode(),
                            priceElement1.getNumber(),
                            priceElement1.getDepart(),
                            previousElmDate,
                            elmDate,
                            currentPriceValue));
                }

                if (currentPriceValue == priceElement1.getValue()) currentPriceValue = priceElement2.getValue();
                else currentPriceValue = priceElement1.getValue();
                previousElmDate = elmDate;
            }
            return result;
        }

        return result;
    }

    /**
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minutes
     * @param seconds
     * @return
     */

    public LocalDateTime createDate(int day, int month, int year, int hour, int minutes, int seconds) {

        LocalDateTime resultDate;
        resultDate = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minutes, seconds));

        return resultDate;
    }

    /**
     * @param date1
     * @param date2
     * @return возвращает date1 если она меньше date2
     */

    public LocalDateTime least(LocalDateTime date1, LocalDateTime date2) {

        Objects.requireNonNull(date1);
        Objects.requireNonNull(date2);

        return date1.isBefore(date2) ? date1 : date2;
    }

    /**
     * @param date1
     * @param date2
     * @return возвращает date2 если она больше date1
     */

    public LocalDateTime largest(LocalDateTime date1, LocalDateTime date2) {

        Objects.requireNonNull(date1);
        Objects.requireNonNull(date2);

        return date1.isBefore(date2) ? date2 : date1;
    }

    /**
     * @param beginDateElm1
     * @param endDateElm1
     * @param beginDateElm2
     * @param endDateElm2
     * @return возвращает коллекцию дат в результате сравнения отрезков дат, отсортированную по возрастанию
     */

    public Collection<LocalDateTime> dateIntersection(LocalDateTime beginDateElm1, LocalDateTime endDateElm1,
                                                      LocalDateTime beginDateElm2, LocalDateTime endDateElm2) {

        Objects.requireNonNull(beginDateElm1);
        Objects.requireNonNull(endDateElm1);
        Objects.requireNonNull(beginDateElm2);
        Objects.requireNonNull(endDateElm2);

        Collection<LocalDateTime> dateCollection = new TreeSet<>();

        if ((beginDateElm2.isBefore(endDateElm1) &&
                endDateElm2.isBefore(endDateElm1) && beginDateElm1.isBefore(beginDateElm2))
                || (beginDateElm1.isBefore(endDateElm2) &&
                endDateElm1.isBefore(endDateElm2) && beginDateElm2.isBefore(beginDateElm1))) {
            dateCollection.add(beginDateElm1);
            dateCollection.add(endDateElm1);
            dateCollection.add(beginDateElm2);
            dateCollection.add(endDateElm2);
        }
        if (beginDateElm2.isBefore(endDateElm1) && endDateElm1.isBefore(endDateElm2)) {

            dateCollection.add(beginDateElm1);
            dateCollection.add(beginDateElm2);
            dateCollection.add(endDateElm2);
        }
        if (beginDateElm1.isBefore(endDateElm2) && endDateElm2.isBefore(endDateElm1)) {

            dateCollection.add(beginDateElm1);
            dateCollection.add(beginDateElm2);
            dateCollection.add(endDateElm1);
        }

        return dateCollection;
    }

}
