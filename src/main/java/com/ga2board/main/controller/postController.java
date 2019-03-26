package com.ga2board.main.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ga2board.main.domain.Attachment;
import com.ga2board.main.domain.Criteria;
import com.ga2board.main.domain.PageMaker;
import com.ga2board.main.domain.Post;
import com.ga2board.main.service.PostService;

@Controller
public class postController {
	
	@Autowired
	private PostService postService;
		
	
	
	/*
	 * test this controller 
	 */
	
	@RequestMapping(value = "/listPage.do", method = RequestMethod.GET)
	public ModelAndView listPage(Criteria cri) {
		
		ModelAndView mav = new ModelAndView("main/main");
		
		List<Post> list = postService.selectPostList(cri);
		mav.addObject("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(postService.listCount());
		
		mav.addObject("pageMaker", pageMaker);
		
		return mav;
	}
	
	@RequestMapping(value = "/postDetail.do", method = RequestMethod.GET)
	public ModelAndView postDetail(String id, String searchText) {
//		System.out.println("댓글이후 detail searchText : "+ searchText);
		ModelAndView mav = new ModelAndView("main/postDetail");
		System.out.println("id >> "+id);
		Post post = postService.findPostById(id);
		List<Attachment> list = post.getAttList();
		
//		System.out.println("here i am");
//		for(int i=0; i<list.size(); i++) {
//			System.out.println(list.get(i).getId());
//		}
		
		Post prePost = postService.findPrePost(id, searchText);
		
		boolean hasPrePost = true;
		if(prePost.getId().equals(id)) {
			hasPrePost = false;
		}
		
		
		mav.addObject("searchText", searchText);
		
		mav.addObject("hasPrePost", hasPrePost);
		mav.addObject("post", post);
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value = "postWrite.do", method = RequestMethod.GET)
	public String postWrite() {
		return "main/postWrite";
	}
	
	
	@RequestMapping(value = "createPost.do", method = RequestMethod.POST)
	public String createPost(HttpServletRequest request) throws Exception {
		System.out.println("ggg");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String password = request.getParameter("password");
		String contents = request.getParameter("contents");

		Post post = new Post();
		post.setTitle(title);
		post.setWriter(writer);
		post.setPassword(password);
		post.setContent(contents);
		
		String postId = postService.registPost(post, request);
		System.out.println(postId);
		return "redirect:/postDetail.do?id="+postId;
//		return "redirect:/postDetail.do?id=p0001";
	}
	
	
	@RequestMapping(value = "search.do", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request, Criteria cri, String searchTxt) throws UnsupportedEncodingException {
		String searchText = null;
		
 		if(searchTxt != null) {
 			searchText = searchTxt;
//			System.out.println("+++++++++++++ search : "+ searchTxt + " , : "+URLEncoder.encode(searchTxt, "UTF-8")); 
		}
 		else {
 			searchText = request.getParameter("searchText");
 		}
		 
		ModelAndView mav = new ModelAndView("main/main2");
		
		
//		String searchOption = request.getParameter("searchOption");

		List<Post> list = postService.findPostListBySearchText(cri, searchText);
		mav.addObject("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		//검색어 list개수 세기
		pageMaker.setTotalCount(postService.searchTextListCount(searchText));
		mav.addObject("pageMaker", pageMaker);
		mav.addObject("searchText",searchText);
		
		return mav;
	}
	
	@RequestMapping(value = "modify.do", method = RequestMethod.GET)
	public ModelAndView modifyPostCheck(String id, String searchText){
//		System.out.println("modify.do");
//		System.out.println(searchText);
		ModelAndView mav = new ModelAndView("main/postModify");
		
		Post post = postService.findPostById(id);
		List<Attachment> list = post.getAttList();
		
		mav.addObject("list", list);
		mav.addObject("post", post);
		mav.addObject("searchText", searchText);
		
		return mav;
	}
	
	@RequestMapping(value = "modify.do", method = RequestMethod.POST)
	public String modifyPost(HttpServletRequest request) throws Exception {
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		String postId = request.getParameter("postId");
		String searchText = request.getParameter("searchText");
		
//		for(int i = 0; i< names.length; i++) {
//			System.out.println(names[i]);
//		}
		
		Post post = new Post();
		post.setTitle(title);
		post.setUpdateWriter(writer);
		post.setContent(contents);
		post.setId(postId);
		
//		List<>
		String[] names = request.getParameterValues("beforeFile");
		List<String> attIds = null;
		
		//names == null일때 처리!
		if(null != names)
			attIds = Arrays.asList(names);
		
		postService.modifyPost(post, request, attIds);
		searchText = URLEncoder.encode(searchText, "UTF-8");
		return "redirect:/postDetail.do?id="+postId+"&searchText="+searchText;
	}
	
	
	@RequestMapping(value = "passwordCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public Boolean passwordCheck(@RequestBody Map<String, Object> map) {

		boolean result = false ;
		result = postService.postWriterCheck((String)map.get("writer"), (String)map.get("password"), (String)map.get("postId"));
		return result;
	}
	
	
	//파일 다운로드
	@RequestMapping("fileDownload.do")
	@ResponseBody
	public void fileDownload(String fileId, HttpServletResponse response) throws IOException {
		System.out.println(">>>fileDownload controller");
		System.out.println("fileId : "+ fileId );
		
		
		Attachment att = postService.findFile(fileId);
		
		String storedFileName = att.getFileName();
		String originalFileName = att.getFileOrg();
		String filePath = att.getFilePath();
		
		System.out.println("storedFileName : "+ storedFileName + ", originalFileName : " + originalFileName);
		byte fileByte[] = FileUtils.readFileToByteArray(new File(filePath+storedFileName));
	     
	    response.setContentType("application/octet-stream");
	    response.setContentLength(fileByte.length);
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");
	    response.getOutputStream().write(fileByte);
	     
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
		
	}
	
	
	@RequestMapping(value = "removePost.do", method = RequestMethod.GET)
	public String removeComment(String postId, String searchText) throws UnsupportedEncodingException {
		System.out.println("removePost controller , "+searchText+" , "+ URLEncoder.encode(searchText, "UTF-8"));
		searchText = URLEncoder.encode(searchText, "UTF-8");
		
		postService.removePost(postId);
		return "redirect:/search.do?searchTxt="+searchText;
	}
	
	
	@RequestMapping(value = "prevPost.do", method = RequestMethod.GET)
	public ModelAndView prevPost(String postId, String searchText) {

		ModelAndView mav = new ModelAndView("main/postDetail");
		
		Post prePost = postService.findPrePost(postId, searchText);
		List<Attachment> list = prePost.getAttList();
		
		
		boolean hasPrePost = true;
		
		if(prePost.getId().equals(postId)) {
			hasPrePost = false;
		}
		
		mav.addObject("hasPrePost", hasPrePost);
		mav.addObject("post", prePost);
		mav.addObject("list", list);
		mav.addObject("searchText", searchText);

		return mav;
	}
	
	@RequestMapping(value = "nextPost.do", method = RequestMethod.GET)
	public ModelAndView nextPost(String postId, String searchText) {

		ModelAndView mav = new ModelAndView("main/postDetail");
		
		Post nextPost = postService.findNextPost(postId, searchText);
		List<Attachment> list = nextPost.getAttList();
		
		
		boolean hasNextPost = true;
		
		if(nextPost.getId().equals(postId)) {
			hasNextPost = false;
		}
		
		mav.addObject("hasNextPost", hasNextPost);
		mav.addObject("post", nextPost);
		mav.addObject("list", list);
		mav.addObject("searchText", searchText);
		
		return mav;
	}
	


}

