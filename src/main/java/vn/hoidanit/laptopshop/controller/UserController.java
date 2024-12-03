package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@Controller 
public class UserController {
    
        private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/") 
    public String getHomePage(Model model){
        List<User> arraUsers  = this.userService.getAllUserByEmail("fsadfsadf@gmail.com");
       // List<User> arraUsers  = this.userService.getAllUsers();
        System.out.println(arraUsers);
        model.addAttribute("eric", "test");
        model.addAttribute("buiday","form controller with model");
        return "hello";
    }
    @RequestMapping("/admin/user") 
    public String getUserPage(Model model){
        List<User> users =this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}") 
    public String getUserDetailPage(Model model, @PathVariable long id){
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/update/{id}") 
    public String getUserUpdatelPage(Model model, @PathVariable long id){
        User user = this.userService.getUserById(id);
        //model.addAttribute("user", user);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }
    @GetMapping("/admin/user/delete/{id}") 
    public String getUserDelete(Model model, @PathVariable long id){
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete") 
    public String postUserDelete(Model model,@ModelAttribute("newUser") User buidat ){
        this.userService.deleteUserById(buidat.getId());
        return "redirect:/admin/user";
    }
    
    @PostMapping("admin/user/update")
    public String postUpdateUser(Model model,@ModelAttribute("newUser") User Buidat) {
        User currenUser = this.userService.getUserById(Buidat.getId());
       if(currenUser!=null){
        currenUser.setAddress(Buidat.getAddress());
        currenUser.setFullName(Buidat.getFullName());
        currenUser.setPhone(Buidat.getPhone());
        this.userService.handleSaveUser(currenUser);
    }
        return "redirect:/admin/user";
    }
    
    
    @RequestMapping("/admin/user/create") 
    public String getCreateUserPage(Model model){
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }


    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) 
    public String createUserPage(Model model,@ModelAttribute("newUser") User Buidat){
        System.out.println("run here"+Buidat);
        this.userService.handleSaveUser(Buidat);
        return "redirect:/admin/user";
    }
}
