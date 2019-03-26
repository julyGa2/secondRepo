package com.ga2board.main.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ga2board.main.domain.Comment;
import com.ga2board.main.service.PostService;

@Controller
public class ReplyController {
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/createReply.do", method = RequestMethod.POST)
	public String createReply(HttpServletRequest request) throws UnsupportedEncodingException {
		
		String writer = request.getParameter("commentWriter");
		String password = request.getParameter("password");
		String postId = request.getParameter("postId");
		String contents = request.getParameter("contents");
		
		//post에 해당하는 comment의 id 가져와서 +1 해주기.
		Comment comment = new Comment();
		comment.setPostId(postId);
		comment.setLevel(1);
		comment.setContents(contents);
		comment.setPassword(password);
		comment.setWriter(writer);

		String searchText = request.getParameter("searchText");
		System.out.println(">>>>>>>searchText : " + searchText);
		postService.registComment(comment);
		searchText = URLEncoder.encode(searchText, "UTF-8");


		
		return "redirect:/postDetail.do?id="+postId+"&searchText="+searchText;
	}
	
	@RequestMapping(value = "/createReReply.do", method = RequestMethod.POST)
	public String createReReply(HttpServletRequest request) throws UnsupportedEncodingException {
		String writer = request.getParameter("commentWriter");
		String password = request.getParameter("password");
		String postId = request.getParameter("postId");
		String contents = request.getParameter("contents");
		String parentId = request.getParameter("parentId");
		
		Comment comment = new Comment();
		comment.setPostId(postId);
		comment.setParentId(parentId);
		comment.setContents(contents);
		comment.setPassword(password);
		comment.setWriter(writer);
		
		String searchText = request.getParameter("searchText");
		
		postService.registReComment(comment);
		searchText = URLEncoder.encode(searchText, "UTF-8");
		return "redirect:/postDetail.do?id="+postId+"&searchText="+searchText;
	
	}
	
	@RequestMapping(value = "isParentComment.do", method = RequestMethod.POST)
	@ResponseBody
	public int isParentComment(@RequestBody Map<String, Object> map) {
		String coId = (String)map.get("coId");

		int cnt = postService.countChildComment(coId);
		
		return cnt;
	}
	
	
	@RequestMapping(value = "commentPasswordCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean passwordCheck(@RequestBody Map<String, Object> map) {

		boolean result = postService.commentRemoveCheck((String)map.get("password"), (String)map.get("coId"));
		
		return result;
	}
	
	@RequestMapping(value = "removeComment.do", method = RequestMethod.GET)
	public String removeComment(String coId, String postId, String searchText) throws UnsupportedEncodingException {
		
		searchText = URLEncoder.encode(searchText, "UTF-8"); 
		postService.removeComment(coId);
		
		return "redirect:/postDetail.do?id="+postId+"&searchText="+searchText;
	}
	
	
	
	
}
