package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductReponsitory;
import vn.hoidanit.laptopshop.service.specification.ProductSpecs;

@Service
public class ProductService {
    private final ProductReponsitory productReponsitory;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductReponsitory productReponsitory, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productReponsitory = productReponsitory;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    public Product handleSaveProduct(Product product) {
        Product dat = this.productReponsitory.save(product);
        return dat;
    }
   
    public Page<Product> getAllProduct(Pageable page) {
        return this.productReponsitory.findAll(page);
    }
    //nameLike
    public Page<Product> getAllProductSwitchSpec(Pageable page, ProductCriteriaDTO productCriteriaDTO) {
        Specification<Product> combinedSpec = Specification.where(null);
    
        if (productCriteriaDTO != null) { // Kiểm tra null trước
            if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
                Specification<Product> currentSpecs = ProductSpecs.listTargetLike(productCriteriaDTO.getTarget().get());
                combinedSpec = combinedSpec.and(currentSpecs);
            }
            if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
                Specification<Product> currentSpecs = ProductSpecs.listFactoryLike(productCriteriaDTO.getFactory().get());
                combinedSpec = combinedSpec.and(currentSpecs);
            }
            if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
                Specification<Product> currentSpecs = this.buildPriceSpecfication(productCriteriaDTO.getPrice().get());
                combinedSpec = combinedSpec.and(currentSpecs);
            }
        }
    
        return this.productReponsitory.findAll(combinedSpec, page);
    }
    
    //min Price 
    // public Page<Product> getAllProductSwichSpec(Pageable page,double price) {
    //     return this.productReponsitory.findAll(ProductSpecs.minPrice(price),page);
    // }

    //maxPrice
    // public Page<Product> getAllProductSwichSpec(Pageable page,double price) {
    //     return this.productReponsitory.findAll(ProductSpecs.maxPrice(price),page);
    // }
    //likeFacty
    // public Page<Product> getAllProductSwichSpec(Pageable page,String name) {
    //     return this.productReponsitory.findAll(ProductSpecs.factoryLike(name),page);
    // }

    //listFactoryLike
    // public Page<Product> getAllProductSwichSpec(Pageable page,List<String> name) {
    //         return this.productReponsitory.findAll(ProductSpecs.listFactoryLike(name),page);
    //     }

    //10<price<15
    // public Page<Product> getAllProductsWithSpec(Pageable page,String price){
    //     if(price.equals("10-toi-15-trieu")){
    //         double min= 10000000;
    //         double max = 15000000;
    //         return this.productReponsitory.findAll(ProductSpecs.matchPrice(min,max),page);
    //     }else if(price.equals("15-toi-30-trieu")){
    //         double min = 15000000;
    //         double max = 30000000;
    //         return this.productReponsitory.findAll(ProductSpecs.matchPrice(min, max),page);
    //     }else{
    //         return this.productReponsitory.findAll(page);
    //     }
    // }

    //price=10-toi-15-trieu,16-toi-20trieu
    public Specification<Product> buildPriceSpecfication(List<String> price){
        Specification<Product> combinedSpec = Specification.where(null);
        for(String p : price ){
            double min = 0;
            double max = 0;

            switch (p) {
                case "duoi-10-trieu":     
                    min = 0;
                    max = 10000000;
                    break;  
                case "10-15-trieu":     
                    min = 10000000;
                    max = 15000000;
                    break;  
                case "15-20-trieu":     
                    min = 15000000;
                    max = 20000000;
                    break;  
                case "tren-20-trieu":     
                    min = 20000000;
                    max = 300000000;
                    break;     
                // Add more cases as needed
            }
            if(min !=0 && max !=0){
                Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min,max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }
        
        return combinedSpec;
    }


    public Product getProductById(long id) {
        return this.productReponsitory.findById(id);
    }

    public void getDeleteProduct(long id) {
        this.productReponsitory.deleteById(id);
    }

    public void handAddProductToCart(String email, Long productId, HttpSession session, long quantity) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user có Cart chưa? chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // tao moi cart
                Cart ortherCart = new Cart();
                ortherCart.setUser(user);
                ortherCart.setSum(0);
                cart = this.cartRepository.save(ortherCart);
            }
            // lưu CartDetail
            Optional<Product> product = this.productReponsitory.findById(productId);
            if (product.isPresent()) {
                Product realProduct = product.get();
                // check product da tung duoc them vao gio hang hay chua
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(cartDetail);
                    // update Cart (sum)
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }
            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(Long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);

        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update curren cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // delete cart(sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrdeṛ̣(User user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {
                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");
                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                // create orderDetail

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cartdetail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // step 3 : update session
                session.setAttribute("sum", 0);
            }
        }

    }

}
