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
var gfv_count = 1;

$(document).ready(function(){
	
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


	
	
	
// 	$("a[class = 'addFile']").unbind("click").click(function(e){
// 		e.preventDefault();
		
// 		fn_addFile();
// 	});

// 	$("a[class = 'deleteFile']").unbind("click").click(function(e){
// 		alert("삭제");
// 		e.preventDefault();	
// 		fn_deleteFile($(this));
// 	});
// 	$("a[class = 'deleteFile']").on("click", function(e){
// 			alert("삭제");
// 			e.preventDefault();	
// 			fn_deleteFile($(this));
// 	});
	
// 	function fn_deleteFile(obj){
// 		alert("삭제 함수");
// 		obj.parent().remove();
// 	}

// 	function fn_addFile(){
// 		var str = 	"<div class = 'fileDiv'>";
// 			str +=		"<p><input type='file' name='file'>"
// 			str +=		"<a href='#' class='deleteFile'>파일 삭제</a> <br/>";
// 			str +=	"</div>"
// 		$("#fileListDiv").append(str);
// // 		$("a[class = 'deleteFile']").on("click", function(e){
// // 			alert("삭제");
// // 			e.preventDefault();	
// // 			fn_deleteFile($(this));
// // 		});
// 	}
	
	
 });




//게시물 비밀번호 일치 체크
function fn_passwordCheck(){
	var password = document.createPostForm.password.value;
	var passwordCheck = document.createPostForm.passwordCheck.value;
	 if(password != passwordCheck){
		 alert("비밀번호가 일치하지 않습니다.");
		 return false;
	 }
	 else{
		 return true;
	 }
	 
}
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
			<a href="javascript:history.back()">뒤로가기</a>
		</nav>

		<hr />

		<section id="container">
		
			<form action="createPost.do"  method="post" name = "createPostForm" 
					enctype="multipart/form-data" onsubmit="return fn_passwordCheck();">
				<table border="1" >
					<tr>
						<th>제목</th>
						<td><input name = "title" size=15 required></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td><input name = "writer" size=15 required></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input name = "password" type="password" size=10 required></td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td><input name = "passwordCheck" type="password" size=10 required></td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td>
								
							<div id="fileListDiv">
								<a href="#" class="addFile">파일 추가</a>
								<div class = "fileDiv">
									<input type="file" name="file">
									<a href="#" class="deleteFile">파일 삭제</a> <br/>
								</div>
							</div>					
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea name = "contents" class="input_write_comment" 
							placeholder="내용을 입력하세요" rows="30" cols="50" required></textarea></td>
					</tr>
					<tr>
						<td>
							<input type="submit" class="comment_submit" value="등록">
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