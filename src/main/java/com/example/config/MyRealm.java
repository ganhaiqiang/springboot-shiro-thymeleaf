package com.example.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.User;
import com.example.service.UserService;

/**
 * @Desc: 自定义Realm类
 * @author: GanHaiqiang
 * @date: 2019-09-24 11:39
 */
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		/*
		 * 给资源进行授权
		 */
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 添加资源的授权字符串
//		info.addStringPermission("user:add");	//参数字符串与资源授权过滤器的参数值一致

		/*
		 * 改造授权字符串硬编码 到数据库中查询当前登录用户的授权字符串
		 */
		// 获取当前用户
		Subject subject = SecurityUtils.getSubject();
		// 获取执行认证返回的principal的参数 //执行认证逻辑时判断密码返回的第一个参数user
		User user = (User) subject.getPrincipal();
		// 得到user的id
		User userId = userService.findUserById(user.getId());
		// 获取当前用户数据库中给定的授权字符串,并将其添加为资源的授权字符串
		info.addStringPermission(userId.getPerms());
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("-------身份认证方法--------");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		if (StringUtils.isBlank(usernamePasswordToken.getUsername())) {
			return null;
		}
		User user = userService.findUserByName(usernamePasswordToken.getUsername());
		// 用户是否存在
		if (user == null) {
			throw new UnknownAccountException();
		}
		// 根据用户名从数据库获取密码
		return new SimpleAuthenticationInfo(user, // 用户
				user.getPassword(), // 密码
				ByteSource.Util.bytes(user.getName() + "salt"), // salt=username+salt
				getName() // realm name
		);
	}
}
