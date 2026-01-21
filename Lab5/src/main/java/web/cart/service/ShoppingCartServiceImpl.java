package web.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import web.cart.model.DB;
import web.cart.model.Item;
import java.util.*;

@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Map<Integer, Item> map = new HashMap<>();

    @Override
    public Item add(Integer id) {
        Item item = map.get(id);
        if (item != null) {
            item.setQty(item.getQty() + 1); // tăng số lượng
            return item;
        }
        Item p = DB.items.get(id);          // Lấy sản phẩm mẫu từ DB
        if (p == null) return null;
        item = new Item(p.getId(), p.getName(), p.getPrice(), 1);
        map.put(id, item);
        return item;
    }

    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public Item update(Integer id, int qty) {
        Item item = map.get(id);
        if (qty <= 0) map.remove(id);
        else item.setQty(qty);
        return item;
    }

    @Override
    public void clear() {

        map.clear();
    }

    @Override
    public Collection<Item> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        return map.values().stream()
                .mapToInt(Item::getQty)
                .sum();
    }

    @Override
    public double getAmount() {
        return map.values().stream()
                .mapToDouble(i -> i.getPrice() * i.getQty())
                .sum();
    }
}
