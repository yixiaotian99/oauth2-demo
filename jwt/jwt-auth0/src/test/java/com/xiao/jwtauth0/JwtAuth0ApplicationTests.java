package com.xiao.jwtauth0;

import com.xiao.jwtauth0.jwt.JwtConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtAuth0ApplicationTests {

    @Autowired
    private JwtConfig jwtConfig;

    @Test
    public void contextLoads() {
    }


    @Test
    public void testJwt(){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "1");
        String token = jwtConfig.createToken(map);

        System.out.println(token);
    }

}
