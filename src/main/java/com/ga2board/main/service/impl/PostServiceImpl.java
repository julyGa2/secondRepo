package com.ga2board.main.service.impl;


import com.ga2board.main.dao.PostDAO;
import com.ga2board.main.domain.Attachment;
import com.ga2board.main.domain.Comment;
import com.ga2board.main.domain.Criteria;
import com.ga2board.main.domain.Post;
import com.ga2board.main.service.PostService;
import com.ga2board.main.util.FileUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PostServiceImpl implements PostService {
	
//	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="fileUtils")
    private FileUtils fileUtils;


	@Autowired
	private PostDAO postDAO;

	@Override
	public List<String> postAllId() {
		System.out.println("servicelogic");
		
		List<String> list = postDAO.postAllId();
		for(String str : list) {
			System.out.println(str);
		}
		return list;
	}


	@Override
	public List<Post> selectPostList(Criteria cri) {
		List<Post> list = postDAO.selectBoardList(cri);
		return list;
	}


	@Override
	public int listCount() {
		int listCnt = postDAO.postListCount();
		return listCnt;
	}


	@Override
	public Post findPostById(String id) {
		
		int nowViews = postDAO.selectViewsByPostId(id);
		nowViews = nowViews + 1;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("views", Integer.toString(nowViews));
		
		postDAO.updatePostViews(map);

		Post post = postDAO.selectPostById(id);

		//post의 comments set
		List<Comment> commentList = postDAO.selectCommentsByPostId(id);
		post.setCommentList(commentList);

		//post의 Attachment list set
		List<Attachment> attachmentList = postDAO.selectFileListByPostId(id);
		post.setAttList(attachmentList);
		
		
		return post;
	}


	@Override
	public void registComment(Comment comment) {
		
		String lastCoId = postDAO.selectLastCoId();
		int nextCoNum = Integer.parseInt(lastCoId.substring(1, lastCoId.length())) + 1;
		String nextCoNumStr = String.format("%04d", nextCoNum);
		String nextCoId = "c" + nextCoNumStr;
		comment.setId(nextCoId);
		
		postDAO.insertFirstComment(comment);
		
	}


	@Override
	public void registReComment(Comment comment) {
		String lastCoId = postDAO.selectLastCoId();
		int nextCoNum = Integer.parseInt(lastCoId.substring(1, lastCoId.length())) + 1;
		String nextCoNumStr = String.format("%04d", nextCoNum);
		String nextCoId = "c" + nextCoNumStr;
		comment.setId(nextCoId);
		int parentLevel = postDAO.selectCoLevelByCoId(comment.getParentId());
		int myLevel = parentLevel + 1;
		comment.setLevel(myLevel);
		
		postDAO.insertReComment(comment);
	}


	@Override
	public String registPost(Post post, HttpServletRequest request) throws Exception {
		
		String lastPostId = postDAO.selectLastPostId();
		int nextPostNum = Integer.parseInt(lastPostId.substring(1, lastPostId.length())) + 1;
		String nextPostNumStr = String.format("%04d", nextPostNum);
		String nextPostId = "p" + nextPostNumStr;
		post.setId(nextPostId);
		post.setView(0);
		
		postDAO.insertPost(post);
		
        List<Attachment> list = fileUtils.parseInsertFileInfo(post, request);
        
        for(int i=0, size=list.size(); i<size; i++){
        	String lastAttId = postDAO.selectLastAttachmentId();
        	int nextAttNum = Integer.parseInt(lastAttId.substring(1, lastAttId.length())) + 1;
        	String nextAttNumStr = String.format("%04d", nextAttNum);
        	String nextAttId = "f" + nextAttNumStr;
        	list.get(i).setId(nextAttId);
        	list.get(i).setDelGB("N");
        	postDAO.insertFile(list.get(i));
        }

		return nextPostId;
	}


	@Override
	public List<Post> findPostListBySearchText(Criteria cri, String searchText) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("cri", cri);
		String searchTextForSQL = "%"+searchText+"%";
		map.put("searchText", searchTextForSQL);
		
		List<Post> list = postDAO.selectPostBySearchText(map);
		
		
		return list;
	}


	@Override
	public int searchTextListCount(String searchText) {
		Map<String, String> map = new HashMap<String, String>();
		String searchTextForSQL = "%"+searchText+"%";
		map.put("searchText", searchTextForSQL);
		
		int totalCnt = postDAO.selectSearchResultCount(map);
		return totalCnt;
	}


	@Override
	public boolean postWriterCheck(String writer, String password, String postId) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("writer", writer);
		map.put("postId", postId);
		
		String result = postDAO.selectPassword(map);
		if(result == null) {
			return false;
		}
		
		if(password.equals(result)) {
			return true;
		}
		else {
			return false;
		}

	}


	@Override
	public void modifyPost(Post post, HttpServletRequest request, List<String> attIds) throws Exception {
		//게시물 update처리
		postDAO.updatePost(post);
		
		//게시물의 첨부파일 리스트 update처리///
		
		//1. index 위해서 마지막 file의 id 가져옴
		String lastAttId = postDAO.selectLastAttachmentId();
		///계산
		int nextAttNum = Integer.parseInt(lastAttId.substring(1, lastAttId.length())) + 1;
		
		// jsp에서 삭제한 파일리스트 DB에서 삭제 (not in)
		if(attIds != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attIds", attIds);
			map.put("postId", post.getId());
			
			postDAO.deleteNotInFile(map);
		}
		else {
			postDAO.deleteFile(post.getId());
		}
		
		//2. 해당 게시글에 해당하는 첨부파일을 전부 삭제처리
//		postDAO.deleteFile(post.getId());
		
		//3. 그리고 다시 write
		List<Attachment> list = fileUtils.parseInsertFileInfo(post, request);
		for(int i=0, size=list.size(); i<size; i++){
			String nextAttNumStr = String.format("%04d", nextAttNum);
			String nextAttId = "f" + nextAttNumStr;
			list.get(i).setId(nextAttId);
			list.get(i).setDelGB("N");
			postDAO.insertFile(list.get(i));
			nextAttNum++;
		}
		
	}


	@Override
	public int countChildComment(String coId) {
		
		return postDAO.selectChildCount(coId);
	}


	@Override
	public boolean commentRemoveCheck(String password, String coId) {
		String result = postDAO.selectCommentPassword(coId);
		
		if(result == null) {
			return false;
		}
		
		if(password.equals(result)) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public void removeComment(String coId) {
		postDAO.deleteComment(coId);
		
	}


	@Override
	public Attachment findFile(String fileId) {
		return postDAO.selectFileByFileId(fileId);
		
	}


	@Override
	public void removePost(String postId) {
		postDAO.deletePost(postId);
		postDAO.deleteCommentByPostId(postId);
		postDAO.deleteFile(postId);
		
	}
	
//	@Override
//	public Post findPostById(String id) {
//		
//		int nowViews = postDAO.selectViewsByPostId(id);
//		nowViews = nowViews + 1;
//		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("id", id);
//		map.put("views", Integer.toString(nowViews));
//		
//		postDAO.updatePostViews(map);
//
//		Post post = postDAO.selectPostById(id);
//
//		//post의 comments set
//		List<Comment> commentList = postDAO.selectCommentsByPostId(id);
//		post.setCommentList(commentList);
//
//		//post의 Attachment list set
//		List<Attachment> attachmentList = postDAO.selectFileListByPostId(id);
//		post.setAttList(attachmentList);
//		
//		
//		return post;
//	}


	@Override
	public Post findPrePost(String postId, String searchText) {
//		String prePostId = postDAO.selectPrePostId(postId);
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("postId", postId);
		
		if(searchText == null) {
			searchText = "";
		}
		
		String mapSearchText = "%" + searchText + "%";
		parameterMap.put("searchText", mapSearchText);
		
		
		
		Map<String, String> returnMap = postDAO.selectPrePostId(parameterMap);
		String prePostId = returnMap.get("PRE_SEQ");
		
		
		
		
		
		
		
		if("0".equals(prePostId) || prePostId == null) {
			return postDAO.selectPostById(postId);
		}
		
		
		System.out.println(prePostId);
		Post prePost = postDAO.selectPostById(prePostId);
		
		
		
		//조회수 set
		int nowViews = prePost.getView();
		nowViews = nowViews + 1;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", prePostId);
		map.put("views", Integer.toString(nowViews));
		
		postDAO.updatePostViews(map);
		prePost.setView(nowViews);
		
		//comment set
		List<Comment> commentList = postDAO.selectCommentsByPostId(prePostId);
		prePost.setCommentList(commentList);
		
		//file set
		List<Attachment> attachmentList = postDAO.selectFileListByPostId(prePostId);
		prePost.setAttList(attachmentList);
		return prePost;
	}


	@Override
	public Post findNextPost(String postId, String searchText) {
		
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("postId", postId);
		
		String mapSearchText = "%" + searchText +"%";
			if(searchText == null) {
			searchText = "";
		}
		parameterMap.put("searchText", mapSearchText);
		
		
	
		
		Map<String, String> returnMap = postDAO.selectNextPostId(parameterMap);
		String nextPostId = returnMap.get("POST_SEQ");
		
		if("0".equals(nextPostId) || nextPostId == null) {
			return postDAO.selectPostById(postId);
		}
		
		System.out.println(nextPostId);
		Post nextPost = postDAO.selectPostById(nextPostId);
		
		// 조회수 set
		int nowViews = nextPost.getView();
		nowViews = nowViews + 1;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", nextPostId);
		map.put("views", Integer.toString(nowViews));

		postDAO.updatePostViews(map);
		nextPost.setView(nowViews);

		// comment set
		List<Comment> commentList = postDAO.selectCommentsByPostId(nextPostId);
		nextPost.setCommentList(commentList);

		// file set
		List<Attachment> attachmentList = postDAO.selectFileListByPostId(nextPostId);
		nextPost.setAttList(attachmentList);
		return nextPost;
	}
	
	

	


	
}
