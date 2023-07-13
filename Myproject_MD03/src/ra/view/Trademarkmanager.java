package ra.view;
import ra.config.InputMethods;
import ra.controller.ProductController;
import ra.controller.TrademarkController;
import ra.model.Product;
import ra.model.Trademark;

import java.util.List;

public class Trademarkmanager {
    private TrademarkController trademarkController;

    public Trademarkmanager(TrademarkController trademarkController) {
        this.trademarkController = trademarkController;
        while (true) {
            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                 QUẢN LÝ THƯƠNG HIỆU                        ║");
            System.out.println("╠════════╦═══════════════════════════════════════════════════╣");
            System.out.println("║   1    │ Nhập số thương hiệu và tên thuơng hiệu cần thêm   ║");
            System.out.println("║   2    │ Hiển thị thông tin tất cả các thương hiệu         ║");
            System.out.println("║   3    │ Chỉnh sửa thông tin thương hiệu                   ║");
            System.out.println("║   4    │ Xóa  thương hiệu sản phẩm                         ║");
            System.out.println("║   5    │ Tìm kiếm thương hiệu                              ║");
            System.out.println("║   0    │ Quay lại                                          ║");
            System.out.println("╚════════╩═══════════════════════════════════════════════════╝");
            System.out.print("Mời bạn lựa chọn: ");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    addNewTrademark();
                    break;
                case 2:
                    showListTrademark();
                    break;
                case 3:
                    editTrademark();
                    break;
                case 4:
                    deleteTrademark();
                    break;
                case 5:
                    searchTrademarkByName();
                    return;
                case 0:
                    return;
                default:
                    System.err.println("vui lòng nhập từ 0 đến 5");
                    break;
            }
        }
    }

    public void addNewTrademark() {
        System.out.print("Bạn muốn thêm vào số lương bao nhiêu : ");
        int n = InputMethods.getPositiveInteger();
        for (int i = 0; i < n; i++) {
            System.out.println("Thương hiệu thứ " + (i + 1));
            Trademark trademark = new Trademark();
            trademark.setId(trademarkController.getNewId());
            trademark.inputData();
            for (Trademark tr: trademarkController.findAll()) {
                if (tr.getTrademarkName().toLowerCase().equals(trademark.getTrademarkName().toLowerCase())){
                    System.err.println("Tên thương  đã tồn tại");
                    return;
                }
            }
            System.out.println("Bạn đã thêm thành công \uD83D\uDC4C \uD83D\uDC4C \uD83D\uDC4C");
            trademarkController.save(trademark);
        }
    }

    public void showListTrademark() {
         if (trademarkController.findAll().size() == 0) {
            System.err.println("Chưa có thương hiệu  nào");
            return;
        }

        for (Trademark c : trademarkController.findAll()) {
            System.out.println("+————————————————————————————————————————————————————————+ ");
            System.out.println("|"   + c                                                    );
            System.out.println("+————————————————————————————————————————————————————————+ ");

        }
    }

    public void editTrademark() {
        if (trademarkController.findAll().isEmpty()){
            System.err.println("chưa có thương hiệu nào ");
            return;
        }
        System.out.print("Nhập tên thườn hiệu cần sửa : ");
        int id = InputMethods.getInteger();
        Trademark trademark = trademarkController.findById(id);
        if (trademark == null) {
            System.err.println("Không tồn tại thương hiệu  này");
            return;
        }
        Trademark newtrademark = new Trademark();
        newtrademark.setId(trademark.getId());
        newtrademark.inputData();
        System.out.println("Bạn đã sửa thành công \uD83D\uDC4C \uD83D\uDC4C \uD83D\uDC4C");
        trademarkController.save(newtrademark);
    }

    public void deleteTrademark() {
        ProductController productController = new ProductController();
        List<Product> productList = productController.findAll();
        if (trademarkController.findAll().isEmpty()){
            System.err.println("chưa có thương hiệu nào ");
            return;
        }
        System.out.print(" Nhập mã thương hiệu cần xoá ");
        int id = InputMethods.getPositiveInteger();
        if (!productList.isEmpty()){
            for (Product p: productList
                 ) {
                if (p.getTrademark().getId() == id){
                    System.err.println("không thể xoá vì vẫn còn sản phẩm của thương hiệu này");
                    return;
                }
            }
        }
        if (trademarkController.findById(id)== null){
            System.err.println("không tìm thấy thương hiệu cần xoá");
            return;
        }
        System.out.println(" Đã xoá thành công ✅");
        trademarkController.delete(id);
    }
    public void searchTrademarkByName() {
        if (trademarkController.findAll().isEmpty()){
            System.err.println("chưa có thương hiệu nào ");
            return;
        }
        boolean flag = false;
        System.out.print(" Nhập vào tên thương hiệu cần tìm kiếm : ");
        String text = InputMethods.getString();
        for (Trademark tr : trademarkController.findAll()) {
            if (tr.getTrademarkName().toLowerCase().contains(text.toLowerCase())) {
                System.out.println("+————————————————————————————————————————————————————————+ ");
                System.out.println(tr);
                System.out.println("+————————————————————————————————————————————————————————+ ");

                flag = true;
            }

        }
        if (!flag) {
            System.err.println("Không tìm thấy thương hiệu nào");
        }
    }
}
