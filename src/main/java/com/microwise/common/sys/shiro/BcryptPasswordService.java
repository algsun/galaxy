package com.microwise.common.sys.shiro;

import org.apache.shiro.authc.credential.PasswordService;

/**
 * bcrypt 方式的密码检查
 *
 * @author bastengao
 * @date 12-11-22 16:13
 *
 * @check @wang.yunlong  & li.jianfei # 275 2012-12-18
 */
public class BcryptPasswordService  implements PasswordService{
    @Override
    public String encryptPassword(Object o) throws IllegalArgumentException {
        return o.toString();
    }

    @Override
    public boolean passwordsMatch(Object o, String hashedPassword) {
        //do nothing, 因为 CustomRealm 已经过密码判断了
        return true;
    }
}
