package com.microwise.blackhole.action.app;

import com.microwise.blackhole.sys.Blackhole;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.util.VerifyCodeUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 验证码 action.响应 web 请求的同时还, 还负责验证码的后台逻辑(放置、获取、判断、清除)。
 * <p/>
 * 每个验证码都有自己的名称和值, 不同的名称对应不同功能的验证码。
 *
 * @author bastengao
 * @date 12-11-19 16:59
 * @check @wang.yunlong & li.jianfei #156 2012-12-18
 */
@Beans.Action
@Blackhole
public class VerifyCodeAction {
    //input
    /**
     * 验证码名称
     */
    private String name;

    //output
    // 验证码图片字节流
    private InputStream inputStream;

    public String execute() throws IOException {

        ByteArrayOutputStream imageOut = new ByteArrayOutputStream();
        try {
            //缓存 code
            String code = VerifyCodeUtil.createVerifyCode(imageOut);
            putVerifyCode(ActionContext.getContext(), name, code);
            inputStream = new ByteArrayInputStream(imageOut.toByteArray());
        } finally {
            imageOut.close();
        }
        return Action.SUCCESS;
    }

    /**
     * 设置验证码到候 session
     *
     * @param actionContext
     * @param name
     * @param code
     */
    public static void putVerifyCode(ActionContext actionContext, String name, String code) {
        actionContext.getSession().put(verifyCodeKey(name), code);
    }

    /**
     * 是否有指定的验证码
     *
     * @param actionContext
     * @param name
     * @return
     */
    public static boolean hasVerifyCode(ActionContext actionContext, String name) {
        return actionContext.getSession().containsKey(verifyCodeKey(name));
    }

    /**
     * 清除指定的验证码
     *
     * @param actionContext
     * @param name
     */
    public static void removeVerifyCode(ActionContext actionContext, String name) {
        actionContext.getSession().remove(verifyCodeKey(name));
    }

    /**
     * 获取验证码
     *
     * @param name
     * @return
     */
    public static String getVerifyCode(ActionContext actionContext, String name) {
        return (String) actionContext.getSession().get(verifyCodeKey(name));
    }

    private static String verifyCodeKey(String name) {
        return String.format("blackhole:verifyCode:%s", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
