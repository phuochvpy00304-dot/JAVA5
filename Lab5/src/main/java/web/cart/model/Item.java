package web.cart.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    Integer id;
    String  name;
    double  price;
    int     qty = 1;
}
