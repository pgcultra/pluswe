package com.pluswe.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexAction {
	
	@ResponseBody
	@RequestMapping({ "/index", "/index/" })
	public String index(){
		System.out.println("hello world");
		return "hello";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
	}

}
