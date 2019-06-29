基于简化模式+Spring Security OAuth2的最简授权服务器
======

- https://github.com/yixiaotian99/oauth2lab/tree/master/lab01/implicit-server

# 操作方式

### 1. 获取授权码

浏览器请求：

http://localhost:8080/oauth/authorize?client_id=myclient&redirect_uri=http://localhost:9001/callback&response_type=token&state=abc

根据提示输入用户名密码(在application.properties文件里头)进行认证，并Approve授权。

响应案例：

http://localhost:9001/callback#access_token=0406040a-779e-4b5e-adf1-bf2f02031e83&token_type=bearer&state=abc&expires_in=119


### 2. 调用API

curl -X GET http://localhost:8080/api/userinfo -H "authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530"

案例响应：

```json
{
    "name": "bobo",
    "email": "bobo@spring2go.com"
}
```

