package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;
       

    public ProductController(ProductService productService,UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
        
    }


    @GetMapping("/admin/product")
    public String getProduct(Model model,@RequestParam(value ="page",defaultValue = "1") int page ){
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> prs = this.productService.getAllProduct(pageable);
        List<Product> listProduct = prs.getContent(); // phân trang
        model.addAttribute("product1", listProduct);
        model.addAttribute("totalPages", prs.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/product/show";
    }

     @RequestMapping("/admin/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProduct(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("product", new Product());
        return  "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("product") Product product ) {
        this.productService.getDeleteProduct(product.getId());
        return "redirect:/admin/product";
    }
    
    @RequestMapping("/admin/product/update/{id}")
    public String getProductUpdatelPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        // model.addAttribute("user", user);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @PostMapping("admin/product/update")
    public String postUpdateProduct(Model model, @ModelAttribute("product") Product product,
    @RequestParam("hoidanitFile") MultipartFile file) {
        Product currenProduct = this.productService.getProductById(product.getId());
        if (currenProduct != null) {

            //update new image
           if(!file.isEmpty()){
            String img = this.uploadService.handleSaveUploadFile(file, "product");
            
            currenProduct.setImage(img);
           }
            currenProduct.setName(product.getName());;
            currenProduct.setPrice(product.getPrice());
            currenProduct.setShortDesc(product.getShortDesc());
            currenProduct.setDetailDesc(product.getDetailDesc());
            currenProduct.setQuantity(product.getQuantity());
            this.productService.handleSaveProduct(currenProduct);
        }
        return "redirect:/admin/product";
    }


     @GetMapping("/admin/product/create") 
    public String getCreateProduct(Model model){
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create") 
    public String createProduct(Model model,
    @ModelAttribute("newProduct") @Valid Product Buidat, BindingResult bindingResult,
     @RequestParam("hoidanitFile") MultipartFile file
    ){
         List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if(bindingResult.hasErrors()){
            return "admin/product/create";
        }
       String image= this.uploadService.handleSaveUploadFile(file, "product");
       Buidat.setImage(image);
      
        this.productService.handleSaveProduct(Buidat);
        return "redirect:/admin/product";
    }

}
