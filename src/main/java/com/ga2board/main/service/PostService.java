package com.ga2board.main.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ga2board.main.domain.Attachment;
import com.ga2board.main.domain.Comment;
import com.ga2board.main.domain.Criteria;
import com.ga2board.main.domain.Post;




public interface PostService {

	//test
	public List<String> postAllId();
	
	//전체 리스트
	public List<Post> selectPostList(Criteria cri);
	
	//총 게시물 수 - 페이징
	public int listCount();
	
	//post id로 게시물 조회
	public Post findPostById(String id);
	
	//댓글 작성
	public void registComment(Comment comment);

	//대댓글 작성
	public void registReComment(Comment comment);
	
	//새 게시물 작성
	public String registPost(Post post, HttpServletRequest request) throws Exception;
	
	//게시물 검색(제목/작성자) - 페이징
	public List<Post> findPostListBySearchText(Criteria cri, String searchText);
	
	//게시물 검색의 총 게시물 수(제목/작성자) - 페이징
	public int searchTextListCount(String searchText);
	
	//게시물 수정시 posId, writer로 password 일치 체크
	public boolean postWriterCheck(String writer, String password, String postId);
	
	//게시물 수정
	public void modifyPost(Post post, HttpServletRequest request, List<String> attIds) throws Exception;
	
	//대댓글 자식댓글 있는지 확인
	public int countChildComment(String coId);
	
	public boolean commentRemoveCheck(String password, String coId);
	
	public void removeComment(String coId);
	
	public Attachment findFile(String fileId);
	
	//게시물 삭제
	public void removePost(String postId);
	
	//이전게시물
	public Post findPrePost(String postId, String searchText);
	
	public Post findNextPost(String postId, String searchText);
}
