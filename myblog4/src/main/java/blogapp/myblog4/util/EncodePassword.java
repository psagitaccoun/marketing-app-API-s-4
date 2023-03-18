package blogapp.myblog4.util;
//this class is not use in project,,just understanding purpose
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword {
    public static void main(String[] args) {
        PasswordEncoder encodePassword=new BCryptPasswordEncoder();
        System.out.println(encodePassword.encode("kavya"));
    }
}
