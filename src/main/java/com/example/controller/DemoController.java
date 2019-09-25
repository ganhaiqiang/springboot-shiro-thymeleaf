package com.example.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.alibaba.fastjson.JSON;
import com.example.entity.User;

/**
 * @Desc: 测试
 * @author: GanHaiqiang
 * @date: 2019-09-24 11:32
 */
@Controller
public class DemoController {

	@GetMapping(value = { "/", "/login" })
	public String index() {
		return "login";
	}

	@RequestMapping("/help")
	public String help(@ModelAttribute("user") User user,Model model) {
		System.out.println("==============");
		System.out.println(user.getName());
		if (user!=null) {
			model.addAttribute("user", user);
		}
		return "help";
	}

	@RequestMapping("/noAuth")
	public String noAuth() {
		return "noAuth";
	}

	@PostMapping("/login")
	public String login(@RequestParam(required = true, name = "name") String name, String password, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(name)) {
			return "login";
		}
		/*
		 * 使用Shiro编写认证操作 1.获取Subject 2.封装用户数据 3、执行登录方法
		 */
		// 1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		// 2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		// 3.执行登录方法
		try {
			subject.login(token);
			/*
			 * 无任何异常，则登录成功 跳转到test.html页面
			 */
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			redirectAttributes.addFlashAttribute("user", user);
			return "redirect:/help"; // redirect：重定向（不带数据），而非转发请求（带数据）
		} catch (UnknownAccountException e) {
			// UnKnownAccountException登录失败：用户名不存在
			model.addAttribute("msg", "用户名不存在");
			// 带着msg数据，转发请求
			return "login";
		} catch (IncorrectCredentialsException e) {
			// IncorrectCredentialsException登录失败：密码错误
			model.addAttribute("msg", "密码错误");
			return "login";
		} catch (LockedAccountException lae) {
			model.addAttribute("msg", "账户已锁定");
			return "login";
		} catch (ExcessiveAttemptsException eae) {
			model.addAttribute("msg", "用户名或密码错误次数过多");
			return "login";
		} catch (AuthenticationException ae) {
			model.addAttribute("msg", "用户名或密码不正确！");
			return "login";
		}
	}

}
