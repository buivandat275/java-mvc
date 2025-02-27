package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class ItemController {

    public final ProductService productService;
        public ItemController(ProductService productService){
            this.productService = productService;
        }
    
    @GetMapping("/products")
    public String getProductPage(Model model, @RequestParam(value = "page" , defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1 , 6);
        //List<Product>  products = this.productService.getAllProduct();

        Page<Product>  prs = this.productService.getAllProduct(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());

        return "client/product/show";
    }
    @GetMapping("/product/{id}") 
    public String getProductPage(Model model, @PathVariable long id){
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/product/detail";
    } 
    
    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id,HttpServletRequest request) {
      HttpSession session = request.getSession(false);
        long productId = id;    
        String email = (String)session.getAttribute("email");
        this.productService.handAddProductToCart(email,productId,session,1);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model,HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        
        double totalPrice = 0;
        for(CartDetail cd : cartDetails){
            totalPrice += cd.getPrice()* cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteProductToCart(@PathVariable long id,HttpServletRequest request) {
      HttpSession session = request.getSession(false);
        long cartDetailId = id;    
        this.productService.handleRemoveCartDetail(cartDetailId,session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }
     @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
                User currentUser = new User();// null
                HttpSession session = request.getSession(false);
                long id = (long) session.getAttribute("id");
                currentUser.setId(id);
                this.productService.handlePlaceOrdeṛ̣(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model){
        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        this.productService.handAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }
    
}
