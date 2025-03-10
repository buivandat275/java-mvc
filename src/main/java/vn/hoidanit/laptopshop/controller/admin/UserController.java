package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arraUsers = this.userService.getAllUserByEmail("fsadfsadf@gmail.com");
        // List<User> arraUsers = this.userService.getAllUsers();
        System.out.println(arraUsers);
        model.addAttribute("eric", "test");
        model.addAttribute("buiday", "form controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam(value="page",defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1,3);
        Page<User> urs = this.userService.getAllUsers(pageable);
        List<User> users = urs.getContent(); 
        model.addAttribute("users1", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", urs.getTotalPages());
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdatelPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        // model.addAttribute("user", user);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getUserDelete(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postUserDelete(Model model, @ModelAttribute("newUser") User buidat) {
        this.userService.deleteUserById(buidat.getId());
        return "redirect:/admin/user";
    }

    @PostMapping("admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User Buidat) {
        User currenUser = this.userService.getUserById(Buidat.getId());
        if (currenUser != null) {
            currenUser.setAddress(Buidat.getAddress());
            currenUser.setFullName(Buidat.getFullName());
            currenUser.setPhone(Buidat.getPhone());
            this.userService.handleSaveUser(currenUser);
        }
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User Buidat, BindingResult bindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if(bindingResult.hasErrors()){
            return "admin/user/create";
        }
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassWord = this.passwordEncoder.encode(Buidat.getPassword());
        Buidat.setAvatar(avatar);
        Buidat.setPassword(hashPassWord);
        Buidat.setRole(this.userService.getRoleByName(Buidat.getRole().getName()));
        this.userService.handleSaveUser(Buidat);
        return "redirect:/admin/user";
    }
}
