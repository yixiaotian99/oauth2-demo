package com.xiao.jwtauth0.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Author sunjinwei
 * @Date 2019-07-04 20:31
 * @Description
 **/
@Component
public class JwtConfig {

    /**
     * JWT 自定义秘钥
     */
    private static final String SECRET_KEY = "test";

    /**
     * jwt 过期时间 24 小时* 300
     */

    private static long expire_time = 60 * 24 * 60 * 1000 * 300;

    /**
     * token 过期时间: 300天
     */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 300;


    /**
     * 创建用户token
     *
     * @return
     */
    public String createToken(Map<String, Object> payload) {
        Date iatDate = new Date();
        //日期转字符串
        Calendar calendar = Calendar.getInstance();
        //特定时间的年后
        calendar.add(calendarField, calendarInterval);
        Date expiresDate = calendar.getTime();
        //JWT 随机ID,做为验证的key
        String jwtId = UUID.randomUUID().toString();
        //1 . 加密算法进行签名得到token
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withClaim("userId", payload.get("userId").toString())
                .withClaim("jwt-id", jwtId)
                // jwt的签发时间
                .withIssuedAt(iatDate)
                //jwt的过期时间，这个过期时间必须要大于签发时间
                .withExpiresAt(expiresDate)
                .sign(algorithm);
        return token;

    }


    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 返回是否校验通过
     */
    public boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", getUserIdByToken(token))
                    .withClaim("jwt-id", getJwtIdByToken(token))
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) { //捕捉到任何异常都视为校验失败
            return false;
        }
    }


    /**
     * 根据Token 获取jwt-id
     */
    private String getJwtIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("jwt-id").asString();
    }

    /**
     * 根据Token 获取userId 必须和原来类型保存一致
     */
    public String getUserIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("userId").asString();
    }

}
