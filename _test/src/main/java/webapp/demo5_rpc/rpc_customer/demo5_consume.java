package webapp.demo5_rpc.rpc_customer;

import org.noear.fairy.Fairy;
import webapp.demo5_rpc.rockapi;

public class demo5_consume {
    public static void main(String[] args){
        rockapi client =  Fairy.builder().create(rockapi.class);

        Object val = client.test1(12);
        if(val == null){
            return;
        }
    }
}
