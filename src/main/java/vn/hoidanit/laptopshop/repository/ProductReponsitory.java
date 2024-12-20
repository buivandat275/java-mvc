package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductReponsitory extends JpaRepository<Product,Long> {

    Product save(Product buidat);

    Product findById(long id);

    List<Product> findAll();


    void deleteById(long id);

}
