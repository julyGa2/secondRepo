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

// addFile

$(document).ready(function(){
	

	var gfv_count =  document.getElementById("listLength").value;
	//alert(gfv_count);
	$(document).on("click","a[class=addFile]",function(e){
		console.log($(this).closest('div').attr('id'));
		var myDiv = $(this).closest('div');
		var str = 	"<div class = 'fileDiv'>";
		str +=		"<p><input type='file' name='file_";
		str += (gfv_count++)
		str += "'>";
		str +=		"<a href='#' class='deleteFile'>파일 삭제</a> <br/>";
		str +=	"</div>"
		myDiv.append(str);
	});
	
	$(document).on("click",".deleteFile",function(e){
		
		//alert("삭제");	
		$(this).parent().remove();
		
	});
	
 });



function fn_userCheck(){

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
				location.href = "modify.do?id="+postId
			}
			else{
				alert("사용자 이름과 비밀번호를 확인하세요");
			}
		},
		error : errorCallback

	});
	
	var errorCallback = function(data) {
		console.log(data);
		alert("수행중 오류가 발생했습니다.")
	};
	
};


</script>

</head>
<body>

	<div id="root">
		<header>
			<h1>ga2 board</h1>
		</header>

		<hr />

		<nav>
		<nav>
			<a href="listPage.do">목록으로</a>   				
			<a href="javascript:history.back()">뒤로가기</a>
		</nav>
			
			
			
		</nav>

		<hr />

		<section id="container">
			
		 
			<form action="modify.do" method="post" name="modifyForm" enctype="multipart/form-data" >
<%-- 			<input type="hidden" name="searchText" value="${searchText}"> --%>
			<input name="searchText" value="${searchText}">
				<table border="1">
					<tr>
						<th>제목</th>
						<td><input name = "title" size=15 required value="${post.title}"></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td><input type="hidden" name="writer" value="${post.writer}" >${post.writer}</td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea name = "contents" class="input_write_comment"
							placeholder="내용을 입력하세요" rows="30" cols="50" required >${post.content}</textarea></td>
					</tr>
					<tr>
						<th>첨부파일</th>
							<td colspan="3">
								<div id="fileListDiv">
									<a href="#" class="addFile">파일 추가</a><br/>
									<input type="hidden" id="listLength" value="${list.size()}">
				                    <c:forEach var="row" items="${list}" varStatus="var">
				                    	<div class = "fileDiv">
					                        <input type="hidden" id="IDX_${row.id}" name="beforeFile" value="${row.id}">
					                        <a href="#" id="name_${row.id}">${row.fileOrg}</a>
<%-- 					                        <input type="file" id="file_${var.index}" name="file_${var.index}"> --%>
					                        	(${row.size} kb)
					                        <a href="#" id="delete_${var.index}" class="deleteFile">파일 삭제</a> <br/>
					                    </div>	
				                    </c:forEach>
			                    </div>
			                    
		                	</td>
					</tr>

					<tr>
						<th >
						</th>
						<td>
							<input type="hidden" name="postId" value="${post.id}" >
							<input type="submit" class="comment_submit" value="수정">
						</td>
					</tr>
	
				</table>
			</form>
		</section>

		<hr />

		<footer>
			<p>ga2 board</p>
		</footer>

	</div>

</body>
</html>