package com.coding.security.serv;


import javax.servlet.http.HttpServletRequest;

/**
 * @author cxr
 * @date 2021/2/24 17:31
 */
public interface AuthorizationServ {

    String login(String username,String password);

    void logout(HttpServletRequest request);
}
