package com.nicechat.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //MD5加密
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
//        credentialsMatcher.setHashIterations(1024);
        //是否存储为16进制(一定要开启)
        //将setStoredCredentialsHexEncoded设置为true，则需要使用toHex()进行转换成字符串，默认使用的是toBase64()
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager();
        return authorizationAttributeSourceAdvisor;
    }*/

    //ShiroFilterFactoryBean:3
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器

        //anon:无需认证就可以访问
        //authc:必须认证了才能访问
        //user:必须拥有记住我功能才能用
        //perms:拥有对某个资源的权限才能访问
        //role:拥有某个角色权限才能访问

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/provider/index/getRecommendList", "authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求
//        bean.setLoginUrl("/login");
//        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    //DefaultWebSecurityManager:2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //设置SecurityUtils依赖的SecurityManager
        //关联UserRealm
        manager.setRealm(userRealm);
//        manager.setCacheManager(cacheManager);
//        manager.setSessionManager(defaultWebSessionManager());
        manager.setSessionManager(getSessionManager());
        //设置后可以在web中任何地方通过SecurityUtils.getSubject();获取当前对象
//        SecurityUtils.setSecurityManager(manager);
        return manager;
    }

    //创建 realm 对象，需要自定义类:1
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    @Bean
    public RedisSessionDao getRedisSession(){
        return new RedisSessionDao();
    }


    /*@Bean
    public DefaultWebSessionManager getSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(getRedisSession());
        sessionManager.setDeleteInvalidSessions(true);// 删除过期的session
        sessionManager.setSessionValidationSchedulerEnabled(false);// 是否定时检查session
        return sessionManager;
    }*/

    public DefaultWebSessionManager getSessionManager(){
        DefaultWebSessionManager sessionManager = new ShiroWebSessionManager();
        sessionManager.setSessionDAO(getRedisSession());
//        sessionManager.setDeleteInvalidSessions(true);// 删除过期的session
        sessionManager.setSessionValidationSchedulerEnabled(false);// 是否定时检查session
        /*Cookie cookie = new SimpleCookie("ShiroCookie");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);*/
        return sessionManager;
    }

    /*@Bean
    public CacheManager redisCacheManager() {
        return new ShiroRedisCacheManager();
    }*/

    /*@Bean
    public DefaultWebSessionManager defaultWebSessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
//        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setGlobalSessionTimeout(21600000);
//        defaultWebSessionManager.setDeleteInvalidSessions(true);
//        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        // 使用redis缓存
//        defaultWebSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return defaultWebSessionManager;
    }*/

}
