import com.xwh.UserServerApplication;
import com.xwh.pojo.User;
import com.xwh.pojo.dto.UserLoginDTO;
import com.xwh.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.annotation.security.RunAs;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@SpringBootTest(classes = {UserServerApplication.class})
public class UserTest {
    @Resource
    private UserService userService;

    @Test
    public void testLogin(){
        User user = new User();
        user.setOpenid("1212");
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        BeanUtils.copyProperties(user,userLoginDTO);
        userService.login(userLoginDTO);

    }
}
