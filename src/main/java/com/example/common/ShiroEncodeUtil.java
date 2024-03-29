package com.example.common;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Desc: shiro密码加密工具
 * @author: GanHaiqiang
 * @date: 2019-09-24 15:13
 */
public class ShiroEncodeUtil {

	public static String MD5Pwd(String username, String pwd) {
		// 加密算法MD5
		// salt盐 username + salt
		// 迭代次数
		String md5Pwd = new SimpleHash("MD5", pwd, ByteSource.Util.bytes(username + "salt"), 2).toHex();
		return md5Pwd;
	}

	public static void main(String[] args) {
		System.out.println(MD5Pwd("server", "123"));
	}
}
