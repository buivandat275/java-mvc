package vn.hoidanit.laptopshop.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductReponsitory;

@Service
public class ProductService {
    private final ProductReponsitory productReponsitory;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

     public ProductService(ProductReponsitory productReponsitory,CartRepository cartRepository,CartDetailRepository cartDetailRepository,UserService userService,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productReponsitory = productReponsitory;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderDetailRepository= orderDetailRepository;
        this.orderRepository = orderRepository;
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

    public void handAddProductToCart(String email,Long productId,HttpSession session){

        User user = this.userService.getUserByEmail(email);
        if(user!=null){
//check user có Cart chưa? chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);
            if(cart == null){
                //tao moi cart
                Cart ortherCart = new Cart();
                ortherCart.setUser(user);
                ortherCart.setSum(0);
               cart= this.cartRepository.save(ortherCart);
            }
            //lưu CartDetail
           Optional<Product> product = this.productReponsitory.findById(productId);
            if(product.isPresent()){
                Product realProduct =product.get();
                //check product da tung duoc them vao gio hang hay chua
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if(oldDetail ==null){
                    CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(realProduct);
                cartDetail.setPrice(realProduct.getPrice());
                cartDetail.setQuantity(1);
               this.cartDetailRepository.save(cartDetail);
               // update Cart (sum)
               int s = cart.getSum()+1;
               cart.setSum(s);
               this.cartRepository.save(cart);
               session.setAttribute("sum", s);
                }else{
                    oldDetail.setQuantity(oldDetail.getQuantity()+1);
                    this.cartDetailRepository.save(oldDetail);
                }
            }
        }
    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(Long cartDetailId ,HttpSession session ){
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);

        if(cartDetailOptional.isPresent()){
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            //delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if(currentCart.getSum()>1){
                //update curren cart
                int s = currentCart.getSum()-1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            }else{
                //delete cart(sum = 1)
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
    public void handlePlaceOrdeṛ̣(User user,HttpSession session,
     String receiverName,String receiverAddress,String receiverPhone ){
  
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
