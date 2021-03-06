package features;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.fairy.annotation.FairyClient;
import org.noear.solon.test.SolonJUnit4ClassRunner;
import org.noear.solon.test.SolonTest;
import webapp.demo5_rpc.protocol.UserModel;
import webapp.demo5_rpc.protocol.UserService;

@RunWith(SolonJUnit4ClassRunner.class)
@SolonTest(webapp.TestApp.class)
public class RpcTest {
    //直接指定服务端地址
    @FairyClient("http://localhost:8080/demo5/user/")
    UserService userService;

    //使用负载
    @FairyClient("local:/demo5/user/")
    UserService userService2;

    @Test
    public void test() {
        UserModel user = userService.getUser(12);
        System.out.println(user);
        assert user.getId() == 12;

        user = userService2.getUser(23);
        System.out.println(user);
        assert user.getId() == 23;
    }
}
