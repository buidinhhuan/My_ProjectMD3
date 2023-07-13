package ra.model;

import ra.config.InputMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int id;
    private String email;
    private String username;
    private String password;
    private String address;
    private  String phoneNumber;
    private  double wallet = 0;
    private boolean status = true;
    private Set<RoleName> roles = new HashSet<>();
    private List<CartItem> cart = new ArrayList<>();
    public User() {
    }

    public User(int id, String email, String username, String password, String address, String phoneNumber , boolean status, Set<RoleName> roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.roles = roles;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }
    public  void inputUserData(){
        System.out.println("nhập mật khẩu mới ");
        this.password= InputMethods.getpassword();
    }
    @Override
    public String toString() {
        return" Mã người dùng: " + id + " | Tên tài khoản : " + username  +  " | Email : "+ email +  "| Số điện thoại : "+ phoneNumber   + "| Tình trạng : "+(status?"Mở khoá":"Khoá") ;
    }
}