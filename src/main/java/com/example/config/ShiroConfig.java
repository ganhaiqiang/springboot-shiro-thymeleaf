package com.example.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * @Desc: Shiro配置
 * @author: GanHaiqiang
 * @date: 2019-09-24 11:28
 */
@Configuration
public class ShiroConfig {

	/*
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		/*
		 * 使用Shiro内置过滤器实现页面拦截：拦截url链接请求
		 * 
		 * shiro内置过滤器，可以实现权限相关的拦截器 常用的过滤器： anon:无需认证（登录）可以访问 authc:必须认证才可以访问
		 * user:如果使用rememberMe的功能可以直接访问 （记住用户和密码） perms:该资源必须得到资源权限才可以访问 （密码验证）
		 * role:该资源必须得到角色权限才可以访问 （VIP会员）
		 * 
		 */
		// 创建集合——充作拦截器集合
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		/*
		 * 单个url拦截，
		 */
		/*
		 * filterChainDefinitionMap.put("/add", "authc");
		 * filterChainDefinitionMap.put("/update", "authc");
		 */
		// 授权过滤器 注意：当前授权拦截后，shiro会自动跳转到默认的未授权页面
		filterChainDefinitionMap.put("/add", "perms[user:add]");
		// url放行
		filterChainDefinitionMap.put("/help", "anon");
		// 配置退出过滤器，退出代码Shiro已经实现
		filterChainDefinitionMap.put("/logout", "logout");
		// 批量url拦截
		filterChainDefinitionMap.put("/*", "authc");
		/*
		 * shiro拦截器拦截成功后，会返回一个默认的地址login.jsp 可以自定义修改调整的登录页面
		 */
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 设置未授权的提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("noAuth");

		// 设置拦截器map集合
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/*
	 * 创建DefaultWebSecurityManager——关联realm（连接数据库）
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 关联realm
		securityManager.setRealm(myRealm);
		return securityManager;
	}

	/*
	 * 创建Realm——自定义Realm类
	 */
	@Bean(name = "myRealm")
	public MyRealm getRealm(@Qualifier("credentialsMatcher") HashedCredentialsMatcher credentialsMatcher) {
		MyRealm realm = new MyRealm();
		realm.setCredentialsMatcher(credentialsMatcher);
		realm.setCachingEnabled(false);
		return realm;
	}

	@Bean(name = "credentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		// 散列的次数，比如散列两次，相当于 md5(md5(""));
		hashedCredentialsMatcher.setHashIterations(2);
		// storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
}
