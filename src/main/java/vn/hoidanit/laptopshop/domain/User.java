package vn.hoidanit.laptopshop.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotNull
    @Size(min = 3, message = "pass phai co toi thieu 3 ki tu")
    private String password;
    @NotNull
    @Size(min = 3,message = "full name phai co toi thieu 3 ki tu")
    private String fullName;
    private String address;
    private String phone;

    private String avatar;
    //User many to one role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
                                    
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    

    public Role getRole() {
        return role;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
// toString method
    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", fullName=" + fullName
                + ", address=" + address + ", phone=" + phone + ", avatar=" + avatar + "]";
    }

    
   

}
