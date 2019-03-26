<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>ga2 board</title>

<meta charset="UTF-8">
<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

// 대댓글 jquery
 $(document).ready(function(){
// 	 var writer = document.getElementById('modifyCommentWriter').value;
// 	 var hasPrePost = document.getElementById('hasPrePost').value;
	 
// 	 if(false == hasPrePost){
// 		 alert("이전 게시물이 없습니다.");
// 	 }
	 
	 $(document).on("click","a[id=hasPrePostId]",function(event){
// 		 alert("ggg");
		 var hasPrePost = document.getElementById('hasPrePost').value;
///		 alert(hasPrePost);
		 if('false' == hasPrePost){
			 alert("이전 게시물이 없습니다.");
		 }
	 });
	 
	 $(document).on("click","a[id=hasNextPostId]",function(event){
 		// alert("next");
		 var hasNextPost = document.getElementById('hasNextPost').value;
		// alert(hasNextPost);
		 if('false' == hasNextPost){
			 alert("이후 게시물이 없습니다.");
		 }
	 });
	 
	 //대댓글 달기
	 $(document).on("click","a[class=rere]",function(event){
    	console.log($(this).closest('div').attr('id'));
    	var coId = $(this).closest('div').attr('id');
    	var myDiv = $(this).closest('div')
    	var str = "";
    	str += '<div id= "replyDialog" style="border: 1px solid gray; width: 99%; display: inline-block " >'
    	str += 		'<form name="form3" action="createReReply.do" method="post">'
    	str += 			'이름: <input name = "commentWriter" size=15 required> '
    	str += 			'비밀번호: <input name = "password" type="password" size=10 required> '
    	str += 			'비밀번호 확인: <input name = "passwordCheck" type="password" size=10 required>'
    	str += 			'<textarea name = "contents" class="input_write_comment" placeholder="댓글 쓰기" rows="4" cols="20" required></textarea>'
    	str +=			'<input type="hidden" name = "searchText" size=15 value="${searchText}">'
    	str += 			'<input type ="hidden" name = "postId" value = "${post.id}">'
    	str += 			'<input type ="hidden" name = "level" value = "${comment.level}">'
    	str += 			'<input type ="hidden" name = "parentId" value = "'
    	str +=			coId
    	str +=  		'">'
    	str +=			'<a href="#" class="replySave" >저장</a>'
    	str +=		'</form>'
    	str += '</div>'
		myDiv.append(str);
    });
    
    //첨부파일 다운로드 
    $("a[class = 'file']").unbind("click").click(function(e){
    	e.preventDefault();
    	fn_downloadFile($(this));
    });
    
 }); 

 
 //대댓글 비밀번호 일치 체크
 $(document).on("click",".replySave",function(){
	 
	 var password = $(this).closest('form').find("[name='password']").val();
	 var passwordCheck = $(this).closest('form').find("[name='passwordCheck']").val();
	 //alert(password + ", check : " + passwordCheck);
	 if(password != passwordCheck){
		 alert("비밀번호가 일치하지 않습니다.");
	 }
	 
	 else{
		 var mtForm = $(this).closest('form');
		 mtForm.submit();
	 }
	 
	 });


//댓글 비밀번호 일치 체크
function fn_passwordCheck(){
	var password = document.form3.password.value;
	var passwordCheck = document.form3.passwordCheck.value;
	///alert(password + ", check : " + passwordCheck);

	 if(password != passwordCheck){
		 alert("비밀번호가 일치하지 않습니다.");
		 return false;
	 }
	 
	 else{
		 return true;
	 }
}

var errorCallback = function(data) {
	console.log(data);
	alert("수행중 오류가 발생했습니다.")
};


//댓글 삭제 fn_replyDelete
function fn_replyDelete(coId, postId, searchText){
	
	//자식댓글이 있는지( 내가 부모댓글인지 확인)
	var data = {
			"coId" : coId
	};
	
	$.ajax({
		url : "isParentComment.do",
		type : "post",
		data : JSON.stringify(data),
		dataType: "json",
		contentType:"application/json;charset=UTF-8",
		success : function(childCnt) {
			console.log(data);
			if(childCnt == 0){
				removeReComment(coId, postId);
			}
			else{
				alert("댓글을 삭제할 수 없습니다.");
			}
		},
		error : errorCallback
	});
	
	var errorCallback = function(data) {
		console.log(data);
		alert("수행중 오류가 발생했습니다.")
	};
	
	
	function removeReComment(coId, postId){
		var password = prompt('비밀번호를 입력하세요', ''); 
		var data =  {
				"password" : password,
				"coId" : coId,
			};
		
		$.ajax({
			url : "commentPasswordCheck.do",
			type : "post",
			data : JSON.stringify(data),
			dataType: "json",
			contentType:"application/json;charset=UTF-8",
			success : function(data) {
				console.log(data);
				if(data){
					//삭제 컨트롤러로 가기
					location.href = "removeComment.do?coId="+coId+"&postId="+postId+"&searchText="+searchText
				}
				else{
					alert("비밀번호를 확인하세요");
				}
			},
			error : errorCallback
		});

	}
	
}


//게시글 수정
function fn_modifyPost(){

	var modifyCheckFormDiv = document.getElementById("modifyCheckForm");
	modifyCheckFormDiv.style.display = "";
 	var modifyDiv = document.getElementById("modifyDiv");
	var deleteDiv = document.getElementById("deleteDiv");
  	
	document.getElementById("moButton").style.disply="none";
	document.getElementById("deleteButton").style.disply="none";

	modifyDiv.style.display = "none";
	deleteDiv.style.display = "none";
	
	document.body.appendChild(modifyDiv);
	document.body.appendChild(deleteDiv);
 	
}


function fn_userCheck(searchText){

	var writer = document.getElementById('modifyCommentWriter').value;
	var password = document.getElementById('modifyPassword').value;
	var postId = document.getElementById('modifyPostId').value;

	var data =  {
			"writer" : writer,
			"password" : password,
			"postId" : postId,
		};
	
	$.ajax({
		url : "passwordCheck.do",
		type : "post",
		data : JSON.stringify(data),
		dataType: "json",
		contentType:"application/json;charset=UTF-8",
		success : function(data) {
			console.log(data);
			if(data){
				location.href = "modify.do?id="+postId+"&searchText="+searchText
			}
			else{
				alert("사용자 이름과 비밀번호를 확인하세요");
			}
		},
		error : errorCallback

	});
	
	
};



function fn_userCheck_Delete(searchText){
	console.log("searchText >>>>>" + searchText);
	///alert("searchText >>>>>" + searchText);
	var writer = document.getElementById('deletePostWriter').value;
	var password = document.getElementById('deletePassword').value;
	var postId = document.getElementById('deletePostId').value;

	var data =  {
			"writer" : writer,
			"password" : password,
			"postId" : postId,
			"searchText" : searchText,
		};
	
	$.ajax({
		url : "passwordCheck.do",
		type : "post",
		data : JSON.stringify(data),
		dataType: "json",
		contentType:"application/json;charset=UTF-8",
		success : function(data) {
			console.log(data);
			if(data){
				location.href = "removePost.do?postId="+postId+"&searchText="+searchText
			}
			else{
				alert("사용자 이름과 비밀번호를 확인하세요");
			}
		},
		error : errorCallback

	});
	
	
};



//게시글 삭제
function fn_deletePost(postId, writer){
	///alert("postId : "+postId+", writer : "+writer);
	var deleteCheckFormDiv = document.getElementById("deleteCheckForm");
// 	var modifyCheckFormDiv = document.getElementById("modifyCheckForm");

	deleteCheckFormDiv.style.display = "";
// 	modifyCheckFormDiv.style.display = "";

	var deleteDiv = document.getElementById("deleteDiv");
  	var modifyDiv = document.getElementById("modifyDiv");
  	
	document.getElementById("moButton").style.disply="none";
	document.getElementById("deleteButton").style.disply="none";

	modifyDiv.style.display = "none";
	deleteDiv.style.display = "none";
	
	document.body.appendChild(modifyDiv);
	document.body.appendChild(deleteDiv);
	
//  	document.getElementById("moButton").style.disply="none";
//  	modifyDiv.style.display = "none";
//  	document.body.appendChild(modifyDiv);
}


// function fn_fileDownload(fileId, postId){
// // 	alert("fileId : "+fileId+", postId : "+postId)
	
	
// 	$.ajax({
// 		url : "fileDownload.do",
// 		type : "get",
// 		data : {
// 			"fileId" : fileId,
// 			"postId" : postId
// 		},
// 		success : function() {
// 			console.log("success return");
// 		},
// 		error : errorCallback
// 	});
	
// };



// function fn_downloadFile(obj){
// 	var idx = obj.parent().find("#IDX").val();

// 	alert(idx);
	
	
	
	
	
	
	
// // 	var formCount = $("#commonForm")


// // 	alert(formCount);
// // 	if(formCount.length > 0){
// // 		formCount.children().remove();
// // 	}
	
// }



</script>

</head>
<body>

	<div id="root">
		<header>
			<h1>ga2 board</h1>
		</header>

		<hr />

		<nav>
			<a href="listPage.do">목록으로</a>
			<hr />
			<form action="search.do" method="get" name="searchForm" >
				<input type="hidden" name = "searchText" size=15 value="${searchText}">
				<input type="submit" class="comment_submit" value="뒤로가기">
			</form>   				
<!-- 			<a href="javascript:history.back()">뒤로가기</a> -->
		</nav>

		<hr />
		<input type=hidden id="hasPrePost" value="${hasPrePost}">
		<input type=hidden id="hasNextPost" value="${hasNextPost}">
		<section id="container">
			<table border="1">
				<tr>
					<th>제목</th>
					<td>${post.title}</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${post.writer}</td>
				</tr>
				<tr>
					<th>날짜</th>
					<td><fmt:formatDate value="${post.date}" pattern="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${post.view}</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>${post.content}</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td colspan="3">
                    <c:forEach var="row" items="${list}">
                        <input type="hidden" id="IDX" value="${row.id}">
<%--                         <a href="#" onclick="fn_fileDownload('${row.id}','${post.id}')">${row.fileOrg}</a> --%>
<%--                         	(${row.size} kb) --%>
                        	<a href="fileDownload.do?fileId=${row.id}" >${row.fileOrg}</a>(${row.size} kb)
                        	(${row.size} kb)
                    </c:forEach>
                	</td>
				</tr>
				<tr>
					<th>댓글 달기</th>
					<td>
						<div style="border: 2px solid gray; width: 600px; padding: 5px; margin-top: 5px; display: inline-block">
							<form action="createReply.do" method="post" name = "form3" onsubmit="return fn_passwordCheck();">
								이름 			<input name = "commentWriter" size=15 required>
								비밀번호		<input name = "password" type="password" size=10 required>
								비밀번호 확인	<input name = "passwordCheck" type="password" size=10 required><br/>
								<textarea name = "contents" class="input_write_comment" placeholder="댓글 쓰기" rows="4" cols="20" required></textarea>
								<input type ="hidden" name = "postId" value = "${post.id}">
								<input type="hidden" name = "searchText" size=15 value="${searchText}">
								<input type="submit" class="comment_submit" value="확인">
							</form>
						</div>
					</td>
				</tr>
				<tr>
					<th>댓글 리스트</th>
					<td>
						<c:forEach var="comment" items="${post.commentList}" varStatus="status">
							<div id = "${comment.id}"
								style="border: 2px solid gray; width: 600px; padding: 5px; margin-top: 5px;
          								margin-left: <c:out value="${30*comment.level}"/>px; display: inline-block">
								<c:if test="${comment.level != 1}">
          							re ▶
          						</c:if>
								${comment.writer}
								<fmt:formatDate value="${comment.date}" pattern="yyyy-MM-dd" />
								<a href="#" onclick="fn_replyDelete('${comment.id}','${post.id}', '${searchText}')">삭제</a>
								<a href="#" class="rere"> 댓글</a>
								<br /> ${comment.contents}

							</div>
							<br />
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th >
					</th>
					<td colspan=2>
						<div id="modifyDiv">
							<input id ="moButton" type="button" name="modifyPostButton" value="게시글 수정" onclick="fn_modifyPost()">
						</div>
						<div id="deleteDiv">
							<input id ="deleteButton" type="button" name="deletePostButton" value="게시글 삭제" onclick="fn_deletePost('${post.id}','${post.writer}')">
						</div>
						<div id="modifyCheckForm" style="display:none">
							<form action="modifyPost.do" method="post" name = "modifyForm" >
								<input type="hidden" id="modifyPostId" name="postId" value="${post.id}">
								이름 			<input id="modifyCommentWriter" name = "commentWriter" size=15 required>
								비밀번호		<input id="modifyPassword" name = "password" type="password" size=10 required>
								<input type="button" class="comment_submit" value="확인" onclick="fn_userCheck('${searchText}');">
							</form>	
						</div>
						<div id="deleteCheckForm" style="display:none">
							<form action="deletePost.do" method="post" name = "deleteForm" >
								<input type="hidden" id="deletePostId" name="postId" value="${post.id}">
								이름 			<input id="deletePostWriter" name = "deleteWriter" size=15 required>
								비밀번호		<input id="deletePassword" name = "deletePassword" type="password" size=10 required>
								<input type="button" class="delete_submit" value="확인" onclick="fn_userCheck_Delete('${searchText}');">
							</form>	
						</div>
						
					</td>
				</tr>

			</table>

		</section>

		<hr />
		<div>
			
		
			<a id="hasPrePostId" href="prevPost.do?postId=${post.id}&searchText=${searchText}" >이전게시물</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<a id="hasNextPostId" href="nextPost.do?postId=${post.id}&searchText=${searchText}" >다음게시물</a>
		
		</div>
		<footer>
			<p>ga2 board</p>
		</footer>

	</div>

</body>
</html>