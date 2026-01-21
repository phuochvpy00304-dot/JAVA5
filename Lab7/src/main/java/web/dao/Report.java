package web.dao;

import java.io.Serializable;

public interface Report {
    Serializable getGroup(); // o.category
    Double getSum();         // SUM(o.price)
    Long getCount();         // COUNT(o)
}
