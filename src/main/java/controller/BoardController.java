package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import board.dao.BoardDAO;
import board.model.BoardVO;

@Controller
@RequestMapping("/board/")
public class BoardController {
	@Autowired
	BoardDAO dao;
	
	@RequestMapping("board")
	public ModelAndView list() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageboard",dao.list(1));
		mv.setViewName("/board/board");
		return mv;
	}
	
	@RequestMapping("testform")
	public String testform() {
		
		return "/board/testform";
	}
	
	@RequestMapping("test")
	@ResponseBody //body안에 내용을 입력함.
	public String test(String p) {
		System.out.println("p:"+p);
		return p;
	}
	
	@RequestMapping("searchList")
	public ModelAndView searchList(String field,String search,int requestPage) {
		ModelAndView mv=new ModelAndView();
		mv.addObject("pageboard", dao.searchList(field, search, requestPage));
		mv.setViewName("/board/board");
		return mv;
	}
	
	@RequestMapping("read")
	public ModelAndView reade(HttpServletRequest request) {
		int idx=Integer.parseInt(request.getParameter("idx"));
		System.out.println("idx:"+request.getParameter("idx"));
		//int requestPage=Integer.parseInt(request.getParameter("reauestPage"));
		ModelAndView mv=new ModelAndView();
		mv.addObject("board",dao.select(idx));
		
		mv.setViewName("/board/read");
		return mv;
	}
	
	@RequestMapping("insert")
	public ModelAndView insert() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("pagaboard",dao.insert(null));
		mv.setViewName("/board/writeForm");
		return mv;
	}
	
	@RequestMapping("update")
	public ModelAndView update() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("pagaboard",dao.insert(null));
		mv.setViewName("/board/writeForm");
		return mv;
	}
}
