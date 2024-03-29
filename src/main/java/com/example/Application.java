package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Desc: 启动类
 * @author: GanHaiqiang
 * @date: 2019-09-24 10:26
 */
@SpringBootApplication
@MapperScan("com.example.dao")
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
