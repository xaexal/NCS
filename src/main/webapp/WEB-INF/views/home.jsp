<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<style>/*
input[type=button] {
	width:50%; height: 50px; background: #166cea; color: #fff; font-size: 24px; border: none; border-radius: 25px;
	cursor: pointer;
}
table,input,select,textarea {
	font-size:24px;
}
input[type=button] {
	width:100%; height: 50px; background: #166cea; color: #fff; font-size: 20px; border: none; border-radius: 25px;
	cursor: pointer;
} */
.navbar-brand {
	color:white;
}
 /* Center image should be larger */
        .carousel-item img {
            transition: transform 0.5s ease;
        }

        /* Default image size */
        .carousel-inner img {
            width: 500px;
            height: auto;
        }

        /* Center image size */
        .carousel-item.active img {
            width: 500px;
            height: auto;
            transform: scale(1.2);
        }

        .carousel-inner {
            display: flex;
            justify-content: center;
            align-items: center;
        }
</style>
<!--<link rel="stylesheet" href="member.css">-->
<header>
<h1>프로그래밍 연습</h1>
<nav class='navbar navbar-expand-lg navbar_dark bg-primary'>
	<table style='width:100%'>
	<tr>
		<td style='width:50%;text-align:left;'>
			<a class="navbar-brand" href="/notice/list" style='color:white'>공지사항</a>&nbsp;&nbsp;
			<a class="navbar-brand" href="/freeboard" style='color:white'>자유게시판</a>&nbsp;&nbsp;
		</td>
		<td style='text-align:right;'>
<c:if test="${sessionScope.name == null}">
            <a class="navbar-brand" href='/login' style='color:white'>로그인</a>
</c:if>
<c:if test="${sessionScope.name != null}">
            <a class="navbar-brand" href='/personal' style='color:white'>${sessionScope.name}</a>
</c:if>
            <a class="navbar-brand" href='/signup' style='color:white'>회원가입</a>
		</td>
	</tr>
	</table>
</nav>
</header>
<section>
<div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item">
        	<a href='/class/python'>
            <img src="img/python.jpg" class="d-block" alt="Python">
            </a>
        </div>
        <div class="carousel-item active">
        	<a  href='/class/java'>
            <img src="img/java.jpg" class="d-block" alt="Java">
            </a>
        </div>
        <div class="carousel-item">
        	<a href='/class/react'>
            <img src="img/react.jpg" class="d-block" alt="React">
            </a>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">이전</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">다음</span>
    </button>
</div>

</section>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="footer.jsp" />