package ra.view;
import ra.model.RoleName;
import ra.model.User;
import ra.service.UserService;
import ra.util.DataBase;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Set<RoleName> set = new HashSet<>();
        Set<RoleName> set2 = new HashSet<>();
        set2.add(RoleName.USER);
        set2.add(RoleName.ADMIN);
        set.add(RoleName.USER);
        User user = new User(1, "huan01@gmail.com", "huan01", "123123", "ninh binh", "0987654321", true, set);
        User admin = new User();
        admin.setId(0);
        admin.setStatus(true);
         admin.setEmail("admin01@gmail.com");
         admin.setPhoneNumber("0968686868");
         admin.setUsername("admin01");
        admin.setPassword("123123");
        admin.setRoles(set2);
        userService.save(admin);
        userService.save(user);
        DataBase<User> data = new DataBase<>();
        for (User u : data.readFromFile(DataBase.USER_PATH)) {
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————");
            System.out.println(u);
            System.out.println("————————————————————————————————————————————————————————————————————————————————————————————————————");
        }
    }
}
