package ra.view;

import ra.config.InputMethods;
import ra.controller.UserController;
import ra.model.User;

public class UserMananger {
    private UserController userController;

    public UserMananger(UserController userController) {
        this.userController = userController;
        while (true) {
            Navbar.menuAccountManager();
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    showAllAccount();
                    break;
                case 2:
                    changeStatus();
                    break;
                case 3:
                    Navbar.menuAdmin();
                    break;
                default:
                    System.err.println("Nhập lựa chọn từ 1 đến 3");
            }
        }
    }

    public UserMananger() {
        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║            Ví điện tử                ║");
            System.out.println("╠════╦═════════════════════════════════╣");
            System.out.println("║  1 │ Hiển thị số dư tài khoản        ║");
            System.out.println("║  2 │ Nap tiền vào ví                 ║");
            System.out.println("║  3 │ Quay lại                        ║");
            System.out.println("╚════╩═════════════════════════════════╝");
            System.out.println("Mời bạn nhâp vào lựa chọn :");
            int choice = InputMethods.getInteger();
            switch (choice) {
                case 1:
                    // Số dư tài khoản
                    showWallet();
                    break;
                case 2:
                    // Nap tiền vào ví
                    putMoney();
                    break;
                case 3:
                    //trở lại
                    Navbar.menuUser();
                    break;
                default:
                    System.err.println("Nhập lựa chọn từ 1 đến 3");
            }
        }
    }
    public  void  showWallet(){
        User walletUser =Navbar.userLogin;
        double wallet = walletUser.getWallet();
        System.out.println("số dư tài khoản của bạn là : " + wallet + " $ ");
    }
    public  void  putMoney(){
         System.out.println("Nhâp số tiền ");
        double putMoney = InputMethods.getInteger();
        System.out.println("Đã nạp tiền \uD83D\uDCB0  thành công ✅");
        double olwMoney = Navbar.userLogin.getWallet();
        Navbar.userLogin.setWallet(olwMoney + putMoney);
     }
    public void showAllAccount() {
        for (User u : userController.findAll()) {
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————— ");
            System.out.println(u);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————— ");
        }
    }

    public void changeStatus() {
        // lấy ra userlogin để check quyền xem có được quyền khóa tài khoản kia không
        System.out.println("Nhập mã tài khoản");
        int id = InputMethods.getPositiveInteger();
        User user = userController.findById(id);
        if (user == null) {
            System.err.println("Mã tài khoản không tồn tại");
        } else {
            if (user.isStatus() == false) {
                System.out.println("Tài khoản đã được mở \uD83D\uDD13");
                user.setStatus(true);
                userController.save(user);
                return;
            }
            user.setStatus(!user.isStatus());
            System.err.println("Đã khoá tài khoản \uD83D\uDD12");
            userController.save(user);
        }
    }
}
