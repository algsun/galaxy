package com.microwise.common.sys.shiro;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 权限不足时，拦截 "UnauthorizedException" 跳转到提示页面
 *
 * @author bastengao
 * @date 12-11-27 10:01
 * @check @wang.yunlong  &  li.jianfei #366 2012-12-18
 */
public class ShiroAuthorizationInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(ShiroAuthorizationInterceptor.class);

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }


    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        try {
            return actionInvocation.invoke();
        } catch (UnauthorizedException e) {
            log.debug("权限不足", e);
            return "unauthorized";
        }
    }
}
