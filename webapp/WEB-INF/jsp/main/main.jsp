<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>ga2 board</title>
</head>
<body>

	<div id="root">
		<header>
			<h1>ga2 board</h1>
		</header>
		
		<hr />
		<nav>
			<a href="listPage.do">목록으로</a>  <a href="postWrite.do">글쓰기</a>
			<hr />
			<form action="search.do" method="get" name="searchForm" >
				<input name = "searchText" size=15 required>
				<input type="submit" class="comment_submit" value="검색">
			</form>
		</nav>
		<hr />
		
		<section id="container">
			<h2>글 목록</h2>

			<table border="1">
				<tr>
					<th>글 번호</th>
					<th>글 제목</th>
					<th>작성자</th>
					<th>작성일자</th>
					<th>조회수</th>
				</tr>

				<c:forEach items="${list}" var="post">
					<tr>
						<td>${post.id}</td>
						<td><a href="postDetail.do?id=${post.id}">${post.title}</a></td>
						<td>${post.writer}</td>
						<td><fmt:formatDate value="${post.date}" pattern="yyyy-MM-dd" /></td>
						<td>${post.view}</td>
					</tr>
				</c:forEach>

			</table>
			
			<div>
				<ul>
					<c:if test="${pageMaker.prev}">
						<li><a href="listPage.do${pageMaker.makeQuery(pageMaker.startPage-1)}">이전</a></li>
					</c:if>
					
					<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
						<li><a href="listPage.do${pageMaker.makeQuery(idx)}">${idx}</a></li>
					</c:forEach>
					
					<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
						<li><a href="listPage.do${pageMaker.makeQuery(pageMaker.endPage + 1)}">다음</a></li>
					</c:if>
				</ul>
			
			</div>

		</section>

		<hr />
		<footer>
			<p>ga2 board</p>
		</footer>
	</div>

</body>
</html>