# 文档

## 1命名和编码规范

> 边写边制定

```yaml
me:
	avrilvent:
		library: 到这一级是包名
			auth: 权限模块
				xx: ...
			manager: 业务模块
				api: 即Controller
					UserApi: Api首字母大写即可
				serv: 即service
					...: service接口以后在这一层
					impl:
						UserServ: service层命名(写好再抽出interface)
				repo: 即repository
					UserRepo: repository命名
				mod: 数据模型
					msg: 响应消息等格式定义(Msg先写好，之后按照经验改成interface)
						Res: 响应体定义
							code: 状态码
							cmt: comment
							data: 携带数据
					data: 数据实体
						TabUser: 实体Tab开头代表对应表 (先写自己的Mod，之后根据写好的抽出BaseMod)
                config: 配置相关
                	factory: Bean
                tool: 工具包
```

- 能用Spring规范的都遵照Spring规范是大方向
- 数据实体之所以Tab之类是为了和表名一致`tab_user`,不携带Mgr这样的项目名是为了通用规范，例如应用到mongo集合什么的概念并不明确，要见名就知道层次
- 权限先不做，做的时候完全按照SpringSecurity的权限表规范来做，不自定义规范
- 用SpringData，不写sql,不碰数据库,暂时不设计任何关联，用程序来做聚合关联， `getUserIdByUserNameAndAge()`如果需要方法，这样命名repo
- 如果写工具，不用静态方法，所有都必须纳入Spring的Bean管理，用注入的方式，注入使用`@AutoWired`，不用原生注解(`@Resource`)
- 所有业务，异常都在Service处理，Repo提供通用基本的查询，能用SpringData提供的就不多写，Api直接调用Service即可
- repo层用sql关键字来命名，api层以及service层用http动词命名
- 一条线的 xxRepo -> xxServ -> xxApi 内的注入命名 repo即可不用 xxRepo，其他注入则全名
- 用户信息表这样的表，叫`TabUserInfo`, 但是如果是关联表，则叫`TabWorkFinishReco`即记录表，不对应一个真实实体，只是某种记录。
- 一个方法出现5次就应该抽出到tool

---

## 2模块划分

> 毕设项目简单，所以暂时只分两个模块，1增删查改的业务模块，2权限的模块
>
> 暂时放在一个项目里做就可以，权限放在所有功能做好之后弄

- Library Manager
  - 权限: SpringSecurity + JWT
  - 后端
    - 增删查改业务框架: SpringData + MySQL
  - 前端
    - VUECLI + ElementUI

---

## 3技术选型

- springboot2.4.3

- lombok: 全项目使用lombok，开启`Accessor(chain=true)`
- web: SpringMVC(servlet系)
- dao: SpringData



