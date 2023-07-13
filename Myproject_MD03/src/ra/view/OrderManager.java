package ra.view;

import ra.config.InputMethods;
import ra.controller.OrderController;
import ra.controller.ProductController;
import ra.model.*;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private OrderController orderController;


    public OrderManager() {
        orderController = new OrderController();
        while (true) {
            System.out.println("+——————————————————————————————————————————+");
            System.out.println("|              Lịch sử Đơn hàng            |");
            System.out.println("+——————————————————————————————————————————+");
            System.out.println("|  1 │ Hiển thị tất cả Đơn hàng            |");
            System.out.println("|  2 │ Hiển thị đơn hàng đang chờ          |");
            System.out.println("|  3 │ Hiển thị đơn hàng đang giao         |");
            System.out.println("|  4 │ Hiển thị Đơn hàng đã hoàn thành     |");
            System.out.println("|  5 │ Hiển thị Đơn hàng đã hủy            |");
            System.out.println("|  6 │ Hiển thị Chi tiết Đơn hàng          |");
            System.out.println("|  0 │ Quay lại                            |");
            System.out.println("+——————————————————————————————————————————+");
            System.out.println("Nhập lựa chọn của bạn");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // hiển thị tất cả
                    showAllOrder();
                    break;
                case 2:
                    // chờ xác nhận
                    showOrderByCode((byte) 0);
                    break;
                case 3:
                    showOrderByCode((byte) 1);
                    break;
                case 4:
                    showOrderByCode((byte) 3);
                    break;
                case 5:
                    showOrderByCode((byte) 2);
                    break;
                case 6:
                    // chi tiết hóa đơn
                    showOrderDetail();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Vui lòng nhập số từ 0 đến 6");
            }
            if (choice == 0) {
                break;
            }
        }
    }

    public OrderManager(OrderController orderController) {
        this.orderController = orderController;
        while (true) {
            Navbar.menuOrderConfirmManager();
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // hiển thị tất cả các đơn đặt hàng của khách hàng
                    showAllOrderAllUser();
                    break;
                case 2:
                    // xác nhận trạng thái đơn hàng
                    OrderComfirm();
                    break;
                case 3:
                    //Tổng doanh thu
                    TotalOder();
                    break;
                case 4:
                    //quay lại
                    Navbar.menuAdmin();
                    break;
                default:
                    System.err.println("Nhập lựa chọn từ 1 đến 3");
            }
        }
    }

    public void showAllOrderAllUser() {
        if (orderController.findAll().isEmpty()) {
            System.err.println("chưa có đơn hàng nào ");
            return;
        }
        for (Order od : orderController.findAll()
        ) {
            System.out.println("\u001B[34m ——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
            System.out.println(od);
            System.out.println("\u001B[34m ——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
        }
    } public void TotalOder() {
        if (orderController.findAll().isEmpty()) {
            System.err.println("chưa có danh thu ");
            return;
        }
        double sumOD= 0;
        for (Order od : orderController.findAll()
        ) {
            if (od.getStatus()==3){
                sumOD +=od.getTotal();
            }
        }
            System.out.println("\u001B[34m ——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
            System.out.println("Tổng doanh thu là  :" +  sumOD + "$");
            System.out.println("\u001B[34m ——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
    }

    public void OrderComfirm() {
        System.out.println("Nhâp mã đơn hàng");
        int ip = InputMethods.getInteger();
        Order comfirmOrder = orderController.findALlById(ip);
        if (comfirmOrder == null) {
            System.err.println("không tìm thấy mã đơn hàng");

        } else if (comfirmOrder.getStatus() == 0) {
            System.out.println("Xác nhận tình trạng đơn hàng ?");
            System.out.println("1. Đang giao hàng \uD83D\uDEF5");
            System.out.println("2. quay lai");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                comfirmOrder.setStatus((byte) 1);
                orderController.save(comfirmOrder);
            }
        } else if (comfirmOrder.getStatus() == 1) {
            System.out.println("Xác nhận tình trạng đơn hàng ?");
            System.out.println("1.Đã giao thành công ✅");
             System.out.println("2. quay lai");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                comfirmOrder.setStatus((byte) 3);
                orderController.save(comfirmOrder);
            }
        } else if (comfirmOrder.getStatus() == 3) {
            System.out.println("đơn hàng đã hoàn thành");
        }
    }

    public void showAllOrder() {
        List<Order> list = orderController.findOrderByUserId();
        if (list.isEmpty()) {
            System.err.println("Lịch sử đơn hàng trống");
            return;
        }
        for (Order o : list) {
            System.out.println("\u001B[34m ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
            System.out.println(o);
            System.out.println("\u001B[34m ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");

        }
    }

    public void showOrderByCode(byte code) {
        List<Order> orders = orderController.findOrderByUserId();
        List<Order> filter = new ArrayList<>();
        for (Order o : orders) {
            if (o.getStatus() == code) {
                filter.add(o);
            }
        }
        if (filter.isEmpty()) {
            System.err.println("Đơn hàng trống");
            return;
        }
        for (Order o : filter) {
            System.out.println("\u001B[34m ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
            System.out.println(o);
            System.out.println("\u001B[34m ————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————— ");
        }

    }

    public void showOrderDetail() {
        ProductController productController = new ProductController();
        User newuser = Navbar.userLogin;
        System.out.println("Nhập mã đơn hàng");
        int orderId = InputMethods.getInteger();
        Order order = orderController.findById(orderId);
        if (order == null) {
            System.err.println("không tìm thấy mã đơn hàng");
            return;
        }

        // in ra chi tiết hóa đơn
        System.out.printf("———————————————————————————————Chi tiết Đơn hàng————————————————————————————\n");
        System.out.printf("                               Mã đơn hàng : %3d \n", order.getId());
        System.out.println("———————————————————————————————————Thông tin————————————————————————————————");
        System.out.print("Người nhận : " + order.getReceiver() + " | Điện thoại : " + order.getPhoneNumber() + "\n");
        System.out.println(" Địa chỉ : " + order.getAddress());
        System.out.println("———————————————————————————————————Chi tiết—————————————————————————————————");
        for (CartItem ci : order.getOrderDetail()) {
            System.out.println(ci);
        }
        System.out.println("Tổng cộng : " + order.getTotal());
        System.out.println("———————————————————————————————————Kết thúc—————————————————————————————————");
        if (order.getStatus() == 0) {
            System.out.println("Bạn chắc chắn muốn hủy đơn hàng này?");
            System.out.println("1. Có ✅");
            System.out.println("2. Không ❌");
            System.out.println("Nhập lựa chọn của bạn");
            int choice = InputMethods.getInteger();
            if (choice == 1) {
                System.out.println("Đơn hàng đã huỷ");
                for (CartItem cartItem : order.getOrderDetail()) {
                    Product product = productController.findById(cartItem.getProduct().getProductId());
                    product.setStock(product.getStock() + cartItem.getQuantity());
                    if (order.getPay().equals(RolePay.WALLET)) {
                        newuser.setWallet(newuser.getWallet() + order.getTotal());
                    }
                    productController.save(product);
                }
                order.setStatus((byte) 2);
                orderController.save(order);
            }
        }
    }
}
