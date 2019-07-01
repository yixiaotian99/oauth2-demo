基于密码模式+Spring Security OAuth2的最简授权服务器
======

- https://github.com/yixiaotian99/oauth2lab/tree/master/lab01/password-server

# 操作方式

### 1. 获取访问令牌

curl -X POST --user myclient:123 http://localhost:8080/oauth/token 
    -H "accept: application/json" 
    -H "content-type: application/x-www-form-urlencoded" 
    -d "grant_type=password&username=xiao&password=admin&scope=read_userinfo"

响应案例：

{
    "access_token": "58a02fd5-87f5-44ff-bbdd-d429cf6a2f60",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read_userinfo"
}

### 2. 调用API

curl -X GET http://localhost:8080/api/userinfo -H "authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530"

案例响应：

```json
{
    "name": "bobo",
    "email": "bobo@spring2go.com"
}
```

