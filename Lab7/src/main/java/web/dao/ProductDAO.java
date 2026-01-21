package web.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.entity.Product;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
//    @Query("FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
//    List<Product> findByPrice(double minPrice, double maxPrice);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    List<Product> findByNameLikeAndPriceBetween(String keywords, double minPrice, double maxPrice);
//    @Query("FROM Product o WHERE o.name LIKE ?1")
//    Page<Product> findByKeywords(String keywords, Pageable pageable);
Page<Product> findAllByNameLike(String keywords, Pageable pageable);

    @Query("SELECT o.category AS group, sum(o.price) AS sum, count(o) AS count "
           + "FROM Product o GROUP BY o.category ORDER BY sum(o.price) DESC")
    List<Report> getInventoryByCategory();
}
