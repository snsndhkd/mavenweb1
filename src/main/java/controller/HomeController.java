package controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import data.Wheather;

@Controller
public class HomeController {
	
	@Autowired
	Wheather wheather;
	//url -> /aaa -> aaa.jsp������ �䱸�ϰ� ����.
	//url -> /test -> test.jsp������ �䱸�ϰ� ����.
	//���ϰ��� ���� �Լ��� ������ ��� ���� ������ �����.
	@RequestMapping("/test")
	void test() {
		System.out.println("test");
	}
	
	@RequestMapping("/test1")
	ModelAndView test1() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("data","test1");
		mv.setViewName("test");
		return mv;
	}
	
	@RequestMapping("/test2")
	ModelAndView test2() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("data","test2");
		mv.setViewName("test");
		return mv;
	}
	
	@RequestMapping("/test3")
	String test3() {
		return "test333";
			
	}
	
	@RequestMapping("/weather")
	ModelAndView wheather(HttpServletRequest request,HttpServletResponse response) {
		//추가사항
		/*
		ServletContext context=request.getServletContext();
		context.setAttribute("context", "context");
		HttpSession session=request.getSession();
		session.setAttribute("session", "session");
		*/
		ModelAndView mv=new ModelAndView(); //내부적으로 request,response
		//mv.addObject("data",new Wheather().getWeatherDate());
		mv.addObject("data",wheather.getWeatherDate());
		mv.setViewName("weather");
		return mv;
	}
}
