package com.xiao.clientresttemplate.controller;

import com.xiao.clientresttemplate.dao.UserRepository;
import com.xiao.clientresttemplate.model.ClientUser;
import com.xiao.clientresttemplate.model.Entry;
import com.xiao.clientresttemplate.model.OAuth2Token;
import com.xiao.clientresttemplate.model.UserVO;
import com.xiao.clientresttemplate.security.ClientUserDetails;
import com.xiao.clientresttemplate.service.AuthorizationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Controller
public class MainPageController {
    @Autowired
    private AuthorizationTokenService tokenService;

    @Autowired
    private UserRepository users;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * 自定义登录界面
     *
     * @return
     */
    @GetMapping("/myLogin")
    public String myLogin() {
        return "myLogin";
    }


    /**
     * 授权服务器自动回调
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public ModelAndView callback(String code, String state) {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        OAuth2Token token = tokenService.getToken(code);
        clientUser.setAccessToken(token.getAccessToken());

        Calendar tokenValidity = Calendar.getInstance();
        long validIn = System.currentTimeMillis() + Long.parseLong(token.getExpiresIn());
        tokenValidity.setTime(new Date(validIn));
        clientUser.setAccessTokenValidity(tokenValidity);

        users.save(clientUser);

        return new ModelAndView("redirect:/mainpage");
    }


    /**
     * 授权后返回资源访问结果
     *
     * @return
     */
    @GetMapping("/mainpage")
    public ModelAndView mainpage() {
        ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        ClientUser clientUser = userDetails.getClientUser();

        //未授权时浏览器重定向到授权端点
        if (clientUser.getAccessToken() == null) {
            String authEndpoint = tokenService.getAuthorizationEndpoint();
            return new ModelAndView("redirect:" + authEndpoint);
        }

        clientUser.setEntries(Arrays.asList(
                new Entry("entry 1"),
                new Entry("entry 2")));

        ModelAndView mv = new ModelAndView("mainpage");
        mv.addObject("user", clientUser);

        tryToGetUserVO(mv, clientUser.getAccessToken());

        return mv;
    }


    /**
     * 获取资源服务器资源
     *
     * @param mv
     * @param token
     */
    private void tryToGetUserVO(ModelAndView mv, String token) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + token);
        String endpoint = "http://localhost:8080/api/userinfo";

        try {
            RequestEntity<Object> request = new RequestEntity<>(
                    headers, HttpMethod.GET, URI.create(endpoint));

            ResponseEntity<UserVO> UserVO = restTemplate.exchange(request, UserVO.class);

            if (UserVO.getStatusCode().is2xxSuccessful()) {
                mv.addObject("userVO", UserVO.getBody());
            } else {
                throw new RuntimeException("it was not possible to retrieve user profile");
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("it was not possible to retrieve user profile");
        }
    }

}
