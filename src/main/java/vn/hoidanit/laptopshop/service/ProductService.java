package vn.hoidanit.laptopshop.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductReponsitory;

@Service
public class ProductService {
    private final ProductReponsitory productReponsitory;

     public ProductService(ProductReponsitory productReponsitory) {
        this.productReponsitory = productReponsitory;
    } 

    public Product handleSaveProduct(Product product){
        Product dat = this.productReponsitory.save(product);
        return dat;
    }
    
    public List<Product> getAllProduct(){
        return this.productReponsitory.findAll();
    }

    public Product getProductById(long id){
       return this.productReponsitory.findById(id);
    }

   

    public void getDeleteProduct(long id){
         this.productReponsitory.deleteById(id);
    }


    
}
