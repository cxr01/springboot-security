"# Boss" 

**项目结构**

* `boss-admin` 代码
* `document` 先按照 `login.png` 登录获取token，再携带token根据 `请求资源.png`来访问其他接口
* `sql` 这里是账号表，省略 用户角色表、角色表、角色权限表、权限表

```
- admin
    api  登录之后请求的资源接口
    entity  基础实体类
    repo  基础dao层
    serv  账号表service，省略其他
- common
    api  接口通用返回类
    base  实体类父类
    config  jpa、redis配置
    constant  常量，分为前后台，这里只实现了前台
    exception 异常处理
- security
    annotation 匿名访问注解
    api 登录接口
    component  权限认证过滤器
    config  security配置
    constant  常量
    dto  登录参数
    enums  枚举用在匿名方法上，即修饰不登录可以访问的方法
    exception  自定义security异常
    serv  登录service
    user  根据用户名查用户信息，返回的信息为模拟数据，上数据库后可以去数据库查询
    util jwtUtil常用方法、ResponseUtil设置返回、securityUtils用在登录后获取用户信息
```