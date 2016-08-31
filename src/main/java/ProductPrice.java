import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by maxsmirnov on 29.08.2016.
 */

public class ProductPrice implements Comparable<ProductPrice> {
    private long id; // идентификатор в БД
    private String productCode; // код товара
    private int number; // номер цены
    private int depart; // номер отдела
    private LocalDateTime begin; // начало действия
    private LocalDateTime end; // конец действия
    private long value; // значение цены в копейках

    ProductPrice(long id, String productCode, int number, int depart,
                 LocalDateTime begin, LocalDateTime end, long value) {
        this.id = id;
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    @Override
    public int compareTo(ProductPrice price) {
        int result;
        result = Integer.compare(Integer.parseInt(productCode), Integer.parseInt(price.productCode));
        if (result != 0) {
            return result;
        }
        result = Integer.compare(number, price.number);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(depart, price.depart);
        if (result != 0) {
            return result;
        }
        result = begin.compareTo(price.begin);
        if (result != 0) {
            return result;
        }
        result = end.compareTo(price.end);
        if (result != 0) {
            return result;
        }
        result = Long.compare(value, price.value);
        if (result != 0) {
            return result;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPrice)) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        if (depart != that.depart) {
            return false;
        }
        if (id != that.id) {
            return false;
        }
        if (number != that.number) {
            return false;
        }
        if (value != that.value) {
            return false;
        }
        if (!begin.equals(that.begin)) {
            return false;
        }
        if (!end.equals(that.end)) {
            return false;
        }
        if (!productCode.equals(that.productCode)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + productCode.hashCode();
        result = 31 * result + number;
        result = 31 * result + depart;
        result = 31 * result + begin.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + (int) (value ^ (value >>> 32));
        return result;
    }

    public boolean equalPriceValues(ProductPrice price) {
        int result;
        result = Integer.compare(Integer.parseInt(productCode), Integer.parseInt(price.productCode));
        if (result != 0) {
            return false;
        }
        result = Integer.compare(number, price.number);
        if (result != 0) {
            return false;
        }
        result = Integer.compare(depart, price.depart);
        if (result != 0) {
            return false;
        }
        result = Long.compare(value, price.value);
        if (result != 0) {
            return false;
        }
        return true;
    }

    public boolean equalNumberAndDepartment(ProductPrice price) {
        int result;
        result = Integer.compare(Integer.parseInt(productCode), Integer.parseInt(price.productCode));
        if (result != 0) {
            return false;
        }
        result = Integer.compare(number, price.number);
        if (result != 0) {
            return false;
        }
        result = Integer.compare(depart, price.depart);
        if (result != 0) {
            return false;
        }
        result = Long.compare(value, price.value);
        if (result != 0) {
            return true;
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
