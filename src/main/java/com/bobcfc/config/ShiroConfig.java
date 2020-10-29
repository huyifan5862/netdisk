package com.bobcfc.config;

import com.bobcfc.realm.UserRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;

/**
 * 作者:胡一帆
 * 项目名:springbootroot
 * 时间:2019/11/20 20:49
 * 描述:
 */
@Configuration
public class ShiroConfig {
    @Bean
    public DefaultWebSecurityManager createSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(createRealm());
        return securityManager;
    }

    // 在 ShiroConfig配置类中添加 shiro方言
    @Bean // 记住我cookie
    public SimpleCookie simpleCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(60*60*24*7);
        return cookie;
    }

    @Bean //记住我管理器
    public CookieRememberMeManager cookieRememberMeManager(SimpleCookie simpleCookie) throws NoSuchAlgorithmException {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie);
        return cookieRememberMeManager;
    }
    @Bean
    public UserRealm createRealm() {
        UserRealm userRealm = new UserRealm();

        //设置加密的方式和次数
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("sha-256");  //sha1  md5
//
//        hashedCredentialsMatcher.setHashIterations(1000);
//        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        return userRealm;
    }


    /**
     * 控制注解权限管理一定生效
     *
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }


    /**
     * 过滤链的声明
     */

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {

        //创建一个过滤连
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // all paths are managed via annotations

        chain.addPathDefinition("/", "anon");
        chain.addPathDefinition("/login", "anon");
        chain.addPathDefinition("/logout", "anon");
        chain.addPathDefinition("/css/**", "anon");
        chain.addPathDefinition("/js/**", "anon");
        chain.addPathDefinition("/less/**", "anon");
        chain.addPathDefinition("/webjars/**", "anon");
        chain.addPathDefinition("/**", "user");

//        chain.addPathDefinition("/user/toRegister", "anon");
//        chain.addPathDefinition("/user/register", "anon");
//        chain.addPathDefinition("/user/captcha", "anon");
//        chain.addPathDefinition("/", "anon");
//        chain.addPathDefinition("/user/login", "anon");
//        chain.addPathDefinition("/user/compare", "anon");
//        chain.addPathDefinition("/category/tohome", "anon");
//        chain.addPathDefinition("/category/selAllid", "anon");
//        chain.addPathDefinition("/category/selAllCategory", "anon");
//        chain.addPathDefinition("/category/selCategoryById", "anon");
//        chain.addPathDefinition("/category/tocart", "authc");
//        chain.addPathDefinition("/goods/addcart", "authc");
//        chain.addPathDefinition("/logout", "logout");
//        chain.addPathDefinition("/images/**", "anon");
//        chain.addPathDefinition("/css/**", "anon");
//        chain.addPathDefinition("/js/**", "anon");
//        chain.addPathDefinition("/javascript/**", "anon");
//        chain.addPathDefinition("/style/**", "anon");
//        chain.addPathDefinition("/**", "user");
        return chain;
    }
}
