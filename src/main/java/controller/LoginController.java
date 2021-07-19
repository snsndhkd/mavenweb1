package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login/")
public class LoginController {
@RequestMapping("login") // -> 클래스의 맵핑 +함수 맵핑합쳐서 처리
public String loginform() {
	return "/login/login";
}

@RequestMapping("logindo") // 실질적으로 /login/logindo
public ModelAndView loginform(HttpServletRequest request) {
	System.out.println(request.getRequestURI());
	String[] arr=request.getRequestURI().split("/");
	String map="/"+arr[1]+"/";
	String page="loginsucess";
	ModelAndView mv=new ModelAndView();
	
	request.getSession().setAttribute("id", "user");
	mv.setViewName(map+page);
	return mv;
}
	
}
