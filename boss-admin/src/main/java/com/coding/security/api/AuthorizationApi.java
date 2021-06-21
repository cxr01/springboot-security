package com.coding.security.api;

import com.coding.common.api.CommonResult;
import com.coding.common.constant.ServerNameConstant;
import com.coding.security.constant.SecurityConstant;
import com.coding.security.dto.AdminUserLoginDto;
import com.coding.security.serv.AuthorizationServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证 Controller
 */
@RestController
@RequestMapping(ServerNameConstant.ADMIN + SecurityConstant.AUTH_URL)
public class AuthorizationApi {


    @Autowired
    private AuthorizationServ authorizationServ;

    @PostMapping("/login")
    public CommonResult login(@Valid @RequestBody(required = false) AdminUserLoginDto dto) {
        String token = authorizationServ.login(dto.getUsername(), dto.getPassword());
        if (token == null) {
            return CommonResult.validateFailedResult("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return CommonResult.successResult(tokenMap);
    }

    @PostMapping("/logout")
    public CommonResult logout(HttpServletRequest request) {
        authorizationServ.logout(request);
        return CommonResult.successResult();
    }
}
