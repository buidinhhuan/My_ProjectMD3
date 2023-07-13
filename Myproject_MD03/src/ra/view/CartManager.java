package ra.view;

import ra.config.Constants;
import ra.config.InputMethods;
import ra.controller.CartController;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.controller.UserController;
import ra.model.*;

public class CartManager {
    private static CartController cartController;
    public ProductController productController;

    public CartManager() {
        cartController = new CartController(Navbar.userLogin);
        productController = new ProductController();
        while (true) {
            Navbar.menuCart();
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // xem danh sách giỏ hàng
                    showCart();
                    break;
                case 2:
                    // chỉnh sửa số lượng
                    changeQuantity();
                    break;
                case 3:
                    // xóa 1 item
                    deleteItem();
                    break;
                case 4:
                    // xóa hêt
                    if (cartController.findAll().size() == 0) {
                        System.err.println("giỏ hàng rỗng");
                        return;
                    }
                    System.out.println("Đã xoá tất cả sản phẩm trong giỏ hàng  ✅");
                    cartController.clearAll();
                    break;
                case 5:
                    // tạo hóa đơn
                    checkout(productController);
                    break;
                case 0:
                    Navbar.menuUser();
                    break;
                default:
                    System.err.println("Vui lòng nhập số từ 0 đến 5");
            }

        }
    }

    public void showCart() {
        User userLogin = Navbar.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Giỏ hàng rỗng");
            return;
        }
        for (CartItem ci : userLogin.getCart()
        ) {
            System.out.println("———————————————————————————————————————————————————————————————————————————————————");
            System.out.println(ci);
            System.out.println("———————————————————————————————————————————————————————————————————————————————————");

        }
    }

    public void changeQuantity() {
        if (cartController.findAll().size() == 0) {
            System.err.println("giỏ hàng rỗng");
            return;
        }
        System.out.println("Nhập mã sản phẩm ");
        int cartItemID = InputMethods.getInteger();
        CartItem cartItem = cartController.findById(cartItemID);
        if (cartItem == null) {
            System.err.println("không tìm thấy mã sản phẩm trong giỏ hàng cần thay đổi");
            return;
        }
        System.out.println("Nhập số lượng");
        cartItem.setQuantity(InputMethods.getInteger());
        System.out.println("thay đổi số lượng thành công  ✅");
        cartController.save(cartItem);

    }

    public void deleteItem() {
        if (cartController.findAll().size() == 0) {
            System.err.println("giỏ hàng rỗng");
            return;
        }
        System.out.println("Nhập mã sản phẩm cần xoá");
        int cartItemID = InputMethods.getInteger();
        if (cartController.findById(cartItemID) == null) {
            System.err.println("không tìm thấy mã sản phẩm cần xoá");
            return;
        }
        System.out.println("Đã xoá thành công  ✅");
        cartController.delete(cartItemID);
    }

    public void checkout(ProductController productController) {
        OrderController orderController = new OrderController();
        UserController userController = new UserController();
        User userLogin = Navbar.userLogin;
        if (userLogin.getCart().isEmpty()) {
            System.err.println("Giỏ hàng rỗng");
            return;
        }
        //  kiểm tra số lượng trong kho
        for (CartItem ci : userLogin.getCart()) {
            Product p = productController.findById(ci.getProduct().getProductId());
            if (p.getStock() == 0) {
                System.err.println("Xin lỗi quý khách ! sản phẩm " + p.getProductName() + " Đã hết hàng ");
                return;
            } else if (ci.getQuantity() > p.getStock()) {
                System.err.println("Sản phẩm " + p.getProductName() + " chỉ còn " + p.getStock() + " sản phẩm, vui lòng giảm số lượng");
                return;
            }
        }

        Order newOrder = new Order();
        newOrder.setId(orderController.getNewId());
        // coppy sp trong gior hàng sang hóa đơn
        newOrder.setOrderDetail(userLogin.getCart());
        // cập nhật tổng tiền
        double total = 0;
        for (CartItem ci : userLogin.getCart()) {
            total += ci.getQuantity() * ci.getProduct().getProductPrice();
        }
        newOrder.setTotal(total);
        System.out.println("Chon phương thức thanh toán :");
        System.out.println("1. Thanh toán tiền mặt");
        System.out.println("2. Thanh toán bằng ví điện tử");
        System.out.println("3. quay lai");
        int choice = InputMethods.getInteger();
        if (choice == 1) {
            newOrder.setUserId(userLogin.getId());
            System.out.println("Nhập tên ");
            newOrder.setReceiver(InputMethods.getString());
            System.out.println("Nhập số điện thoại");
            newOrder.setPhoneNumber(InputMethods.getPhoneNumber());
            System.out.println("Nhập địa chỉ");
            newOrder.setAddress(InputMethods.getString());
            System.out.println("Đặt hàng thành công  ✅   cảm ơn quý khách  \uD83D\uDE46");
            newOrder.setPay(RolePay.CASH);
            orderController.save(newOrder);
            // giảm số lượng đi
            for (CartItem ci : userLogin.getCart()) {
                Product p = productController.findById(ci.getProduct().getProductId());
                p.setStock(p.getStock() - ci.getQuantity());
                productController.save(p);
            }
            userController.save(userLogin);
            cartController.clearAll();

        } else if (choice == 2) {
            if (userLogin.getWallet() < total) {
                System.err.println("Số tiền trong tài khoản không đủ để thanh toán");
            } else {
                newOrder.setUserId(userLogin.getId());
                System.out.println("Nhập tên ");
                newOrder.setReceiver(InputMethods.getString());
                System.out.println("Nhập số điện thoại");
                newOrder.setPhoneNumber(InputMethods.getPhoneNumber());
                System.out.println("Nhập địa chỉ");
                newOrder.setAddress(InputMethods.getString());
                System.out.println("Thanh toán thành công  ✅   cảm ơn quý khách  \uD83D\uDE46");
                newOrder.setPay(RolePay.WALLET);
                orderController.save(newOrder);
                // giảm số lượng đi
                for (CartItem ci : userLogin.getCart()) {
                    Product p = productController.findById(ci.getProduct().getProductId());
                    p.setStock(p.getStock() - ci.getQuantity());
                    productController.save(p);
                }
                userLogin.setWallet(userLogin.getWallet() - total);
                userController.save(userLogin);
                cartController.clearAll();
            }
        } else if (choice == 3) {
            new CartManager();
        } else {
            System.err.println("Lựa chọn không hơp lệ");
        }
    }

    public static void addToCart() {
        cartController = new CartController(Navbar.userLogin);
        ProductController productController = new ProductController();
        CartItem cartItem = new CartItem();
        System.out.println("Nhập mã sản phẩm");
        int proId = InputMethods.getInteger();
        for (Product checkproduct : productController.findAll()) {
            if (checkproduct.getStock() == 0 && checkproduct.getProductId() == proId) {
                System.err.println("Sản phẩm đã bán hết");
                return;
            }
        }
        Product pro = productController.findById(proId);
        if (pro == null) {
            System.err.println(Constants.NOT_FOUND);
            return;
        }
        cartItem.setId(cartController.getNewId());
        cartItem.setProduct(pro);
        System.out.println("Nhập số lượng cần thêm");
        cartItem.setQuantity(InputMethods.getPositiveInteger());
        System.out.println("Đã thêm sản phẩm vào giỏ hàng ✅");
        cartController.save(cartItem);
    }
}
