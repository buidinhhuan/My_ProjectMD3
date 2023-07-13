package ra.view;
import ra.config.InputMethods;
import ra.controller.ProductController;
import ra.controller.TrademarkController;
 import ra.model.Product;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductManager  {
    private  ProductController productController ;
    private TrademarkController trademarkController = new TrademarkController();

    public ProductManager(ProductController productController) {
        this.productController = productController;

         while (true) {
             System.out.println("╔═════════════════════════════════════════════════╗");
             System.out.println("║               QUẢN LÝ SẢN PHẨM                  ║");
             System.out.println("╠════╦════════════════════════════════════════════╣");
             System.out.println("║  1 │ Nhập số lượng và sản phẩm cần thêm         ║");
             System.out.println("║  2 │ Hiển thị thông tin các sản phẩm            ║");
             System.out.println("║  3 │ Sắp xếp sản phẩm theo giá                  ║");
             System.out.println("║  4 │ Xóa sản phẩm                               ║");
             System.out.println("║  5 │ Tìm kiếm sản phẩm                          ║");
             System.out.println("║  6 │ Chỉnh sửa thông tin sản phẩm               ║");
             System.out.println("║  0 │ Quay lại                                   ║");
             System.out.println("╚════╩════════════════════════════════════════════╝");
             System.out.print("Mời bạn lựa chọn: ");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    displayListProduct();
                    break;
                case 3:
                  sortProductByPrice();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProductByName();
                    break;
                case 6:
                    editProduct();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("vui lòng nhập từ 0 đến 6");
                    break;
            }
        }
    }
    public  static void displayListProduct(){
        ProductController productController1 =new ProductController();
         System.out.println("——————————————————————————————————————————————————————————————————————————————————————————————————");
        if (productController1.findAll().isEmpty()){
            System.err.println("chưa có sản phẩm nào");
            return;
        }
         for (Product p: productController1.findAll()) {
             if (p.getStock()==0){
                 p.setStatus(false);
                 System.out.println(p);
             }
            if(p.isStatus()){
                System.out.println(p);
            }
        }
    }

    public void addNewProduct() {
        System.out.print("Bạn muốn nhập vào  nhiêu sản phẩm : ");
        int n = InputMethods.getInteger();
        for (int i = 0; i < n; i++) {
            System.out.println("Sản phẩm thứ " + (i + 1));
            Product product = new Product();
            product.setProductId(productController.getNewId());
            product.inputData(trademarkController.findAll());
            for (Product p: productController.findAll()) {
                if (p.getProductName().toLowerCase().equals(product.getProductName().toLowerCase())){
                    System.err.println("Tên sản phẩm đã tồn tại");
                    return;
                }
            }
            System.out.println("Thêm sản phẩm thành công ✅");
            productController.save(product);
        }
    }


    public void deleteProduct() {
        if (productController.findAll().isEmpty()){
            System.err.println("chưa có sản phẩm nào");
            return;
        }
        System.out.print("Nhập vào Mã sản phẩm: ");
        int id = InputMethods.getPositiveInteger();
        if (productController.findById( id ) == null){
            System.err.println("không tìm thấy sản phẩm cần xoá");
            return;
        }
        System.out.println(" Đã xoá thành công ✅");
        productController.delete(id);
    }
    public void sortProductByPrice() {
        if (productController.findAll().isEmpty()) {
            System.err.println("Chưa có sản phẩm nào.");
            return;
        }
        productController.findAll().sort(Comparator.comparingDouble(Product::getProductPrice));
        System.out.println("Danh sách sản phẩm sau khi được sắp xếp theo giá cả:");
        for (Product product : productController.findAll()) {
            System.out.println(product);
        }
    }

    public void searchProductByName() {
        if (productController.findAll().isEmpty()) {
            System.err.println("chưa có sản phẩm nào");
        } else {
            boolean flag = false;
            System.out.print("Nhập vào tên nước hoa cần tìm kiếm : ");
            String text = InputMethods.getString();
            for (Product p : productController.findAll()) {
                if (p.getProductName().toLowerCase().contains(text.toLowerCase())) {
                    if (p.getStock() == 0) {
                        p.setStatus(false);
                    }
                    System.out.println("——————————————————————————————————————————————————————————————————————————————————————————————————  ");
                    System.out.println(p);
                    flag = true;
                }
            }
            if (!flag) {
                System.err.println("Không có sản phẩm nào");
            }
        }
    }
    public void editProduct() {
        if (productController.findAll().isEmpty()) {
            System.err.println("chưa có sản phẩm  nào");
            return;
         }
            System.out.print("Nhập vào mã sản phẩm cần sửa: ");
            int id = InputMethods.getInteger();
            Product product = productController.findById(id);
            if (product == null) {
                System.err.println("Không có sản phẩm bạn muốn tìm");
                return;
            }
            Product newProduct = new Product();
            newProduct.setProductId(product.getProductId());
            newProduct.inputData(trademarkController.findAll());
            System.out.println(" Bạn đã sửa thành công ✅");
            productController.save(newProduct);
    }
}
