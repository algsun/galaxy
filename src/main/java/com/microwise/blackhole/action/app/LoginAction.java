package com.microwise.blackhole.action.app;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.microwise.blackhole.bean.*;
import com.microwise.blackhole.service.*;
import com.microwise.blackhole.sys.*;
import com.microwise.blackhole.util.CookieUtil;
import com.microwise.blueplanet.sys.AppCacheHolder;
import com.microwise.common.action.ActionMessage;
import com.microwise.common.sys.ConfigFactory;
import com.microwise.common.sys.Constants;
import com.microwise.common.sys.Security;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.common.sys.freemarker.tools.LocaleBundleTools;
import com.microwise.common.util.ResourceBundleUtil;
import com.microwise.common.util.VerifyCodeUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 登录
 * Date: 12-11-6 Time: 下午1:31
 *
 * @author bastengao
 * @check @wang.yunlong & li.jianfei #789 2012-12-18
 */
@Beans.Action
@Blackhole
public class LoginAction implements ApplicationContextAware {

    //用户名 cookie key
    public static final String USERNAME_COOKIE = "username";
    //语言 cookie key
    public static final String LOCALE_COOKIE = "locale";
    //用户名 cookie 保留时间(7天)
    public static final int USERNAME_COOKIE_MAX_AGE = 604800;

    //验证码名称
    public static final String VERIFY_CODE_NAME = "login";
    //用户名密码输错的次数
    public static final String USERNAME_PASSWORD_ERROR_TIMES = "_usernamePasswordErrorTimes";
    //用户名密码输错超过多少后，出现验证码
    public static final int ERROR_MAX_TIMES = 3;

    @Autowired
    private UserService userService;
    @Autowired
    private LogicGroupService logicGroupService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private LogicGroupSubsystemDisableService logicGroupSubsystemDisableService;

    @Autowired
    private BlackholeLoggerUtil logger;

    @Autowired
    private GlobalizationService globalizationService;

    @Autowired
    private AppCacheHolder appCacheHolder;

    // 权限始始化监听集合
    private Collection<BeforeInitPrivilegesListener> privilegesListeners;

    //input
    /**
     * 用户名(email)
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 登录后跳转到的链接(galaxy内部链接)
     */
    private String redirectTo;

    //output
    /**
     * 访客登陆是否被启用
     */
    private boolean anonymityLoginEnable;

    /**
     * 使用模版
     */
    private String mode;

    /**
     * 语言灯光
     */
    private String language;

    /**
     * 跳转到页面
     *
     * @return
     */
    public String view() {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        mode = appConfig.get(Constants.Config.GALAXY_MODE);
        anonymityLoginEnable = userService.isAnonymityLoginEnable();
        // 如果 username 没有指定, 将 Cookie 中的 username 显示到输入框
        HttpServletRequest request = ServletActionContext.getRequest();
        if (Strings.isNullOrEmpty(username)) {
            if (CookieUtil.hasCookie(USERNAME_COOKIE, request)) {
                username = CookieUtil.getValue(USERNAME_COOKIE, request);
            }
        }

        //国际化语言设置
        Locale locale = (Locale) ServletActionContext.getRequest().getSession().getAttribute("WW_TRANS_I18N_LOCALE");
        if (locale == null) {
            //如果session中没有语言，那么优先看cookie中有没有语言。如果没有，就取浏览器语言。
            if (CookieUtil.hasCookie(LOCALE_COOKIE, request)) {
               String language = CookieUtil.getValue(LOCALE_COOKIE, request);
                String loc[] = language.split("_");
                locale = new Locale(loc[0], loc[1]);
            }else{
                //如果浏览器语言不属于国际化语言，那么默认国际化语言为中文。
                Locale browerLocale = request.getLocale();
                if (!"zh_CNen_US".contains(browerLocale.toString())) {
                    locale = new Locale("zh", "CN");
                } else {
                    locale = browerLocale;
                }
            }
            ServletActionContext.getRequest().getSession().setAttribute("WW_TRANS_I18N_LOCALE", locale);
        }
        addLocaleToCookie(locale.toString());
        return Action.SUCCESS;
    }

    public String setting() {
        // 国际化
        HttpServletRequest request = ServletActionContext.getRequest();
        if (!Strings.isNullOrEmpty(language)) {
            String loc[] = language.split("_");
            Locale locale = new Locale(loc[0], loc[1]);
            request.getLocale().setDefault(locale);
            ServletActionContext.getRequest().getSession().setAttribute("WW_TRANS_I18N_LOCALE", locale);
        }
        appCacheHolder.evictAvailableSensorinfoOfSite();
        return Action.SUCCESS;
    }

    /**
     * 登录.
     * 用户登录失败过后，再次登录需要输验证码
     *
     * @return
     */
    public String execute() {
        ConfigFactory.Configs appConfig = ConfigFactory.getInstance().getConfig(Constants.Config.CONFIG_PROPERTIES_URL);
        mode = appConfig.get(Constants.Config.GALAXY_MODE);
        anonymityLoginEnable = userService.isAnonymityLoginEnable();

        if (Strings.isNullOrEmpty(username) || username.trim().isEmpty()) {
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blackhole.login.usernameEmpty")).consume();
            return Action.LOGIN;
        }

        if (Strings.isNullOrEmpty(password) || password.trim().isEmpty()) {
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blackhole.login.passwordBlank")).consume();
            return Action.LOGIN;
        }

        ActionContext actionContext = ActionContext.getContext();
        if (VerifyCodeAction.hasVerifyCode(actionContext, VERIFY_CODE_NAME)) {
            String realVerifyCode = VerifyCodeAction.getVerifyCode(actionContext, VERIFY_CODE_NAME);
            if (!realVerifyCode.equalsIgnoreCase(verifyCode)) {
                ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blackhole.login.incorrectVerification")).consume();
                return Action.LOGIN;
            }
        }

        //用户名就是邮箱
        String email = username.toLowerCase();
        Subject currentUser = SecurityUtils.getSubject();
        try {

            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            currentUser.login(token);
        }
        //  注意: 此处注释的代码不是遗留代码 @gaohui 2012-12-19
        // "帐户不存在" 信息不能透露给用户
        /*************************************
         catch (UnknownAccountException uae) {
         //username wasn't in the system, show them an error message?
         failAndConsume("帐户不存在");
         return EventAction.LOGIN;
         }
         */
        // "密码不正确" 信息不能透露给用户
        /**********************************
         catch (IncorrectCredentialsException ice) {
         //password didn't match, try again?
         failAndConsume("密码不正确");
         return EventAction.LOGIN;
         }
         *///
        catch (LockedAccountException lae) {
            //account for that username is locked - can't login.  Show them a message?
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blackhole.login.accountDisabled")).consume();
            return Action.LOGIN;
        } catch (AuthenticationException ae) {
            //unexpected condition - error?
            ActionMessage.createByAction().fail(ResourceBundleUtil.getBundle().getString("blackhole.login.wrongUsername")).consume();
            int errorTimes = usernamePasswordError();
            if (errorTimes >= ERROR_MAX_TIMES) {
                //预置验证码
                VerifyCodeAction.putVerifyCode(ActionContext.getContext(), VERIFY_CODE_NAME, VerifyCodeUtil.createVerifyCode());
            }
            return Action.LOGIN;
        }

        HttpServletRequest request = ServletActionContext.getRequest();
        String galaxyResourcesBaseURL = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort() + "/galaxy-resources";

//        HttpSession session = request.getSession();
//        session.setAttribute(Constants.Session.GALAXY_RESOURCE_URL, galaxyResourcesBaseURL);

        //最后一次退的用户
        String logoutUserName=null;
        if (CookieUtil.hasCookie(USERNAME_COOKIE, request)) {
             logoutUserName = CookieUtil.getValue(USERNAME_COOKIE, request);
        }
        addEmailToCookie(email);
        initSession(email, galaxyResourcesBaseURL);

        logger.log("登录", "登录");

        // 如果是超级管理员跳转到 "选择站点" 页面
        if (isSuperMan()) {
            return "chooseLogicGroup";
        } else if (Security.isAnonymity()) {
            return "anonymityLogin";
        }

        String customize = appConfig.get(Constants.Config.GALAXY_CUSTOMIZE);
        if ("1".equals(customize)) {
            return "toSaturn";
        }

        if (!Strings.isNullOrEmpty(redirectTo)) {
            return "redirectTo";
        }

        String resultName = null;
        //如果最后一次退出的用户是当前用户，那么跳转到最后一次退出的系统
        if(email.equals(logoutUserName)){
            resultName=redirectToLastLogoutSubsystem(email);
        }

        if (resultName != null) {
            return resultName;
        }

        return Action.SUCCESS;
    }


    /**
     * 初化用户相关信息到 session
     *
     * @param email 用户邮箱
     */
    private void initSession(String email, String galaxyResourcesBaseURL) {
        Map<String, Object> session = ActionContext.getContext().getSession();

        //Galaxy Resource URL
        session.put(Constants.Session.GALAXY_RESOURCE_URL, galaxyResourcesBaseURL);

        //清除验证码
        VerifyCodeAction.removeVerifyCode(ActionContext.getContext(), VERIFY_CODE_NAME);
        clearUsernamePasswordError();

        //将登录的用户 put 到 session
        User user = userService.findUserByEmail(email);
        session.put(Constants.Session.USER_OF_SESSION, user);

        //用户归属站点 put 到 session
        //如果是超级管理员没有 LogicGroup
        LogicGroup logicGroup = logicGroupService.findLogicGroupByUserId(user.getId());
        if (logicGroup != null) {
            //携带 Site 如果有
            logicGroup = logicGroupService.findLogicGroupCarrySite(logicGroup.getId());
            session.put("subsystemList", logicGroupSubsystemDisableService.findSubsystemOpen(logicGroup.getId()));
        }
        session.put(Constants.Session.USER_LOGIC_GROUP, logicGroup);

        //用户当前选择站点(当前站点)
        //默认用户的归属站点为当前站点
        if (logicGroup != null) {
            Sessions sessions = new Sessions(session);
            List<LogicGroup> subLogicGroupsOfCurrent = sessions.refreshCurrentLogicGroupAndSub(logicGroup, logicGroupService, userService, logicGroupSubsystemDisableService);

            session.put(Constants.Session.SUB_LOGIC_GROUP_OF_USER_LOGIC_GROUP, subLogicGroupsOfCurrent);
        }

        initPrivileges(user);
    }

    /**
     * 跳转到最后退出的业务系统如果可以；如果不行，则返回 null
     */
    private String redirectToLastLogoutSubsystem() {
        // 如果是基层站点，可跳转到业务系统
        if (Sessions.createByAction().currentLogicGroup().getSite() != null) {
            String cookieKey = LogoutAction.COOKIE_NAME_LAST_LOGOUT_FROM;
            HttpServletRequest request = ServletActionContext.getRequest();
            // 根据最后一次退出时所在业务系统，登录后跳转到那个业务系统
            if (CookieUtil.hasCookie(cookieKey, request)) {
                String subsystem = CookieUtil.getValue(cookieKey, request);
                if (Arrays.asList("blueplanet", "proxima", "orion", "uma", "phoenix").contains(subsystem.toLowerCase())) {
                    return subsystem.toLowerCase();
                }
            }
        }

        return null;
    }

    /**
     * 跳转到上次退出的系统，如果可以，则返回跳转路径；否则返回null
     */
    private String redirectToLastLogoutSubsystem(String email){
        //最后一次退出的系统是否在此次登录的站点启用
        boolean isOpen=false;
        String logoutSubsystem=redirectToLastLogoutSubsystem();
        List<Subsystem> subsystemList = (List<Subsystem>) ActionContext.getContext().getSession().get("subsystemList");
        for(Subsystem subsystem : subsystemList){
            if(subsystem.getSubsystemCode().equals(logoutSubsystem)){
               isOpen=true;
               break;
            }
        }
        //最后一次退出的系统用户是否有权限访问
        if (isOpen){
            String subsystemPrivilege="blackhole:subsystem:"+logoutSubsystem;
            User user = userService.findUserByEmail(email);
            List<Role> roles = roleService.findRoleListByUserId(user.getId());
            for(Role role : roles){
                List<Privilege> privileges = privilegeService.findPrivilegeListByRoleId(role.getId());
                for (Privilege privilege : privileges){
                    if(subsystemPrivilege.equals(privilege.getPrivilegeId())){
                        return logoutSubsystem;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 加载权限
     */
    public void initPrivileges(User user) {

        Map<String, Object> session = ActionContext.getContext().getSession();
        //所有角色的所有权限
        Set<Privilege> allPrivilegesOfUser = new HashSet<Privilege>();

        //语言国际化
        String language = LocaleBundleTools.appLocale();
        globalizationService.changeDisplayByLanguage(language);
        allPrivilegesOfUser.addAll(privilegeService.findRequiredPrivilegesByLanguage(language));

        //加载公共权限

        //超级管理员权限直接从权限表来
        if (SecurityUtils.getSubject().hasRole(com.microwise.common.sys.Constants.Roles.SUPERMAN)) {
            allPrivilegesOfUser.addAll(privilegeService.findSupermanPrivileges(language));
        }

        //普通用户权限从角色来
        // TODO 优化：可批量查询角色对应的权限
        List<Role> roles = roleService.findRoleListByUserId(user.getId());
        for (Role role : roles) {
            List<Privilege> privileges = privilegeService.findPrivilegesCarryParent(role.getId(),language);
            allPrivilegesOfUser.addAll(privileges);
        }

        fireBeforeInitPrivileges(allPrivilegesOfUser);
        //归属站点用户权限
        Map<Integer, Set<Privilege>> subsystemPrivilegesOfUser = forkBySubsystem(allPrivilegesOfUser);
        session.put(Constants.Session.SUBSYSTEM_PRIVILEGES_OF_USER, subsystemPrivilegesOfUser);


        List<Privilege> guestPrivileges = SystemPrivileges.getGuestPrivileges();
        fireBeforeInitPrivileges(guestPrivileges);
        //访客权限(用户在其他站点权限)
        session.put(Constants.Session.GUEST_PRIVILEGES, privilegeSet(guestPrivileges));
        Map<Integer, Set<Privilege>> subsystemPrivilegesOfGuest = forkBySubsystem(guestPrivileges);
        session.put(Constants.Session.SUBSYSTEM_PRIVILEGES_OF_GUEST, subsystemPrivilegesOfGuest);
    }

    /**
     * 按子系统将权限划分开, 同时将权限组织为一棵树
     * <p/>
     * subsystemId:Integer => privileges:Set . 子系统 => 子系统的权限
     *
     * @param allPrivileges 所有权限集合
     * @return 子系统--权限 map
     */
    private Map<Integer, Set<Privilege>> forkBySubsystem(Collection<Privilege> allPrivileges) {
        Map<Integer, Set<Privilege>> subsystemPrivileges = new HashMap<Integer, Set<Privilege>>();

        for (final Subsystem subsystem : Subsystems.all()) {
            Collection<Privilege> privilegesOfSubsystem = Collections2.filter(allPrivileges, new Predicate<Privilege>() {
                @Override
                public boolean apply(Privilege privilege) {
                    return subsystem.getSubsystemId() == privilege.getSubsystemId();
                }
            });

            //权限树
            Set<Privilege> privilegesTreeOfSubsystem = resortPrivileges(privilegesOfSubsystem);
            subsystemPrivileges.put(subsystem.getSubsystemId(), privilegesTreeOfSubsystem);
        }
        return subsystemPrivileges;
    }

    /**
     * 将用户名(email)记录到 Cookie 中
     *
     * @param email 用户名
     */
    private void addEmailToCookie(String email) {
        // 如果用户登录成功，将用户名记录到 Cookie 中
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Cookie userNameCookie = new Cookie(USERNAME_COOKIE, email);
        //userNameCookie.setSecure(true);
        userNameCookie.setPath(request.getContextPath());
        userNameCookie.setMaxAge(USERNAME_COOKIE_MAX_AGE);
        response.addCookie(userNameCookie);
    }

    /**
     * 将语言记录到 Cookie 中
     *
     * @param locale 语言编码
     */
    private void addLocaleToCookie(String locale) {
        // 如果用户登录成功，将用户名记录到 Cookie 中
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Cookie localeCookie = new Cookie(LOCALE_COOKIE, locale);
        localeCookie.setPath(request.getContextPath());
        localeCookie.setMaxAge(USERNAME_COOKIE_MAX_AGE);
        response.addCookie(localeCookie);
    }

    /**
     * 返回用户名密码错误次数
     *
     * @return 错误次数
     */
    private int usernamePasswordError() {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        int errorTimes;
        if (session.containsKey(USERNAME_PASSWORD_ERROR_TIMES)) {
            errorTimes = ((Integer) session.get(USERNAME_PASSWORD_ERROR_TIMES)) + 1;
        } else {
            errorTimes = 1;
        }
        session.put(USERNAME_PASSWORD_ERROR_TIMES, errorTimes);
        return errorTimes;
    }

    /**
     * 清除"用户名密码"错误 session
     */
    private void clearUsernamePasswordError() {
        ActionContext.getContext().getSession().remove(USERNAME_PASSWORD_ERROR_TIMES);
    }

    /**
     * 重新组织权限为一棵树
     *
     * @param privileges
     * @return
     */
    private static Set<Privilege> resortPrivileges(Collection<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            privilege.setChildPrivileges(sortPrivileges(children(privileges, privilege)));
        }

        Set<Privilege> topPrivileges = sortPrivileges(topPrivileges(privileges));
        return topPrivileges;
    }

    /**
     * 一个权限的子权限
     *
     * @param allPrivileges
     * @param parent
     * @return
     */
    private static Collection<Privilege> children(Collection<Privilege> allPrivileges, final Privilege parent) {
        return Collections2.filter(allPrivileges, new Predicate<Privilege>() {
            @Override
            public boolean apply(Privilege privilege) {
                if (privilege.getParent() == null) {
                    return false;
                }

                return privilege.getParent().getPrivilegeId().equals(parent.getPrivilegeId());
            }
        });
    }

    /**
     * 排序同级的权限
     *
     * @param privileges
     * @return
     */
    private static SortedSet<Privilege> sortPrivileges(Collection<Privilege> privileges) {
        SortedSet<Privilege> sortedSet = new TreeSet<Privilege>(new Privilege.PrivilegeComparator());
        sortedSet.addAll(privileges);
        return sortedSet;
    }


    /**
     * 返回顶级权限
     *
     * @param allPrivileges
     * @return
     */
    private static Collection<Privilege> topPrivileges(Collection<Privilege> allPrivileges) {
        return Collections2.filter(allPrivileges, new Predicate<Privilege>() {
            @Override
            public boolean apply(Privilege privilege) {
                return privilege.getParent() == null;
            }
        });
    }

    /**
     * 返回权限ID集合
     *
     * @param privileges
     * @return
     */
    private static Set<String> privilegeSet(Collection<Privilege> privileges) {
        Set<String> privilegesSet = new HashSet<String>();
        for (Privilege privilege : privileges) {
            privilegesSet.add(privilege.getPrivilegeId());
        }
        return privilegesSet;
    }

    /**
     * 是否是超级管理员
     *
     * @return
     */
    private boolean isSuperMan() {
        return SecurityUtils.getSubject().hasRole(com.microwise.common.sys.Constants.Roles.SUPERMAN);
    }

    /**
     * 触发监听
     *
     * @param privileges
     */
    private void fireBeforeInitPrivileges(Collection<Privilege> privileges) {
        for (BeforeInitPrivilegesListener listener : privilegesListeners) {
            listener.beforeInit(privileges, Sessions.createByAction().currentLogicGroup());
        }
    }

    /**
     * 获取权限监听器集合
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, BeforeInitPrivilegesListener> listeners = applicationContext.getBeansOfType(BeforeInitPrivilegesListener.class);
        privilegesListeners = listeners.values();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isAnonymityLoginEnable() {
        return anonymityLoginEnable;
    }

    public void setAnonymityLoginEnable(boolean anonymityLoginEnable) {
        this.anonymityLoginEnable = anonymityLoginEnable;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public String getMode() {
        return mode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
