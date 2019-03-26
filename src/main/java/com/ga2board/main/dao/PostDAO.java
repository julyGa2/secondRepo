package com.ga2board.main.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ga2board.main.domain.Attachment;
import com.ga2board.main.domain.Comment;
import com.ga2board.main.domain.Criteria;
import com.ga2board.main.domain.Post;


public interface PostDAO {
	
	//id만 가져오는 test
	public List<String> postAllId() ;
	
	//페이징 -- 정해진 범위 내의 게시물만 가져와서 목록 만들기 
	public List<Post> selectBoardList(Criteria cri);
	
	//게시물 총 개수
	public int postListCount();
	
	//postId로 게시물 조회
	public Post selectPostById(String id);
	
	//postId로 게시물의 댓글list 조회
	public List<Comment> selectCommentsByPostId(String id);
	
	//postId로 게시물의 현재 조회수 조회
	public int selectViewsByPostId(String id);
	
	//게시물의 조회수 +1
	public void updatePostViews(Map<String, String> map);
	
	//마지막 comment Id 조회
	public String selectLastCoId();
	
	//댓글 작성( 대댓글 x)
	public void insertFirstComment(Comment comment);
	
	//comment Id로 댓글의 level(depth) 조회
	public int selectCoLevelByCoId(String coId);
	
	//대댓글 작성
	public void insertReComment(Comment comment);
	
	//마지막 post Id 조회
	public String selectLastPostId();
	
	//게시물 등록
	public void insertPost(Post post);
	
	//검색+페이징
	public List<Post> selectPostBySearchText(Map<String, Object> map);
	
	//검색어의 총 개시물 수 + 페이징
	public int selectSearchResultCount(Map<String, String> map);
	
	//writer, postId로 password 조회
	public String selectPassword(Map<String, String> map);
	
	//post update
	public void updatePost(Post post);
	
	//자식댓글 있는지 확인
	public int selectChildCount(String coId);
	
	//comment password 맞는지 확인
	public String selectCommentPassword(String coId);
	
	//댓글 삭제
	public void deleteComment(String coId);
	
	//file 업로드
	public void insertFile(Attachment attachment);
	
	//마지막 file Id 조회
	public String selectLastAttachmentId();
	
	//file 리스트 조회
	public List<Attachment> selectFileListByPostId(String postId);
	
	//file download위한 조회
	public Attachment selectFileByFileId(String fileId);
	
	//file delete
	public void deleteFile(String postId);
	
	public void deletePost(String postId);
	
	public void deleteCommentByPostId(String postId);
	
	//게시물 수정의 file delete
	public void deleteNotInFile(Map<String, Object> map);

	//이전 게시물 Id
	public HashMap<String, String> selectPrePostId(Map<String, String> map);
	
	//이후 게시물 Id
	public HashMap<String, String> selectNextPostId(Map<String, String> map);
}
