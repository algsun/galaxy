package com.microwise.common.sys.shiro;

import com.microwise.blackhole.action.app.PrivilegesBanListener;
import com.microwise.blackhole.bean.Privilege;
import com.microwise.blackhole.bean.Role;
import com.microwise.blackhole.bean.User;
import com.microwise.blackhole.service.PrivilegeService;
import com.microwise.blackhole.service.RoleService;
import com.microwise.blackhole.service.UserService;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

/**
 * 自定义认证，授权回调
 *
 * @author bastengao
 * @date 12-11-4 下午10:18
 * @check  @wang.yunlong & li.jianfei #577 2012-12-18
 */
public class CustomRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory
            .getLogger(CustomRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        if (log.isDebugEnabled()) {
            log.debug("用户认证");
        }

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String email = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UnknownAccountException("此帐户不存在:" + email);
        }
        // 密码是否正确
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new IncorrectCredentialsException("密码不正确");
        }

        // 帐户是否禁用
        if (user.isDisable()) {
            throw new LockedAccountException("此帐户被禁用:" + email);
        }

        // 帐户未激活
        if(!user.isActive()){
            throw new LockedAccountException("此帐户被禁用:" + email);
        }

        // 在这里不要暴露用户的真实密码了, 保护用户隐私
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getId(), user.getPassword(), getName());
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        int userId = (Integer) principalCollection.fromRealm(getName())
                .iterator().next();

        if (log.isDebugEnabled()) {
            log.debug("用户授权:{}", userId);
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //加载公共权限
        for(Privilege privilege : privilegeService.findRequiredPrivilegesByLanguage(LocaleBundleTools.appLocale())){
            authorizationInfo.addStringPermission(privilege.getPrivilegeId());
        }

        //如果是超级管理员
        if (isSuperman(userId)) {
            authorizationInfo.addRole(Constants.Roles.SUPERMAN);
            authorizationInfo.addRole(Constants.Roles.SITE_MANAGER);
            authorizationInfo.addRole(Constants.Roles.USER);
            // 超级管理员的权限直接从权限表查
            List<Privilege> privileges = privilegeService.findSupermanPrivileges(LocaleBundleTools.appLocale());
            appendPrivileges(authorizationInfo, privileges);
        } else {
            // 加载角色
            List<Role> roles = roleService.findRoleListByUserId(userId);
            for (Role role : roles) {
                //如果是站点管理员
                if (role.isManager()) {
                    authorizationInfo.addRole(Constants.Roles.SITE_MANAGER);
                }

                // 如果是匿名用户
                if(role.getState() == Role.STATE_ANONYMITY){
                    authorizationInfo.addRole(Constants.Roles.ANONYMITY);
                }
            }
            authorizationInfo.addRole(Constants.Roles.USER);

            // TODO 优化：可批量查询角色对应的权限 @gaohui 2012-12-03
            for (Role role : roles) {
                // 只加载叶子权限
                //因为 shiro 框架权限上有名称空间, 如果用户有 blackhole:log 权限,没有 blackhole:log:query ，但是 blackhole:log:query isPermmited 判断是通过的
                List<Privilege> privileges = privilegeService.findLeafPrivilegesByRoleId(role.getId());
                List<String> banPrivileges = PrivilegesBanListener.getBanPrivileges();
                for(Iterator<Privilege> it = privileges.iterator(); it.hasNext(); ){
                    if(banPrivileges.contains(it.next().getPrivilegeId())){
                        it.remove();
                    }
                }
                appendPrivileges(authorizationInfo, privileges);
            }
        }
        log.debug("authorizations:{}", authorizationInfo.getStringPermissions());

        return authorizationInfo;
    }

    /**
     * 是否是超级管理员
     *
     * @param userId
     * @return
     */
    public static boolean isSuperman(int userId) {
        // 此处写死超级管理员的 userId, 暂时是这样子(数据库初始化脚本超级管理员的 userId 为 1)。
        return userId == 1;
    }

    /**
     * 追加权限
     *
     * @param authorizationInfo
     * @param privileges
     */
    private void appendPrivileges(SimpleAuthorizationInfo authorizationInfo, List<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            authorizationInfo.addStringPermission(privilege.getPrivilegeId());
        }
    }

}
