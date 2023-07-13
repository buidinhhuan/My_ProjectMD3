package ra.config;

public class Message {
    public static String getStatusByCode(byte code) {
        switch (code) {
            case 0:
                return " Đang chờ xác nhận  ⏳ ";
            case 1:
                return " Đang giao hàng  \uD83D\uDEF5 ";
            case 2:
                return " Đã huỷ  ❌";
            case 3:
                return "Đã giao thành công ✅ ";
            default:
                return " Không hơp lê ";
        }
    }
}
