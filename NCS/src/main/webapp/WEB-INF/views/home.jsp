<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="base.jsp" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style>
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
}
.navbar-brand {
	color:white;
}
 /* Center image should be larger */
        .carousel-item img {
            transition: transform 0.5s ease;
        }

        /* Default image size */
        .carousel-inner img {
            width: 100px;
            height: auto;
        }

        /* Center image size */
        .carousel-item.active img {
            width: 300px;
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
<h1>해피 프로그래밍</h1>	
</header>
<section>
    <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="img/python.jpg" class="d-block" alt="First Image">
        </div>
        <div class="carousel-item">
            <img src="img/java.jpg" class="d-block" alt="Second Image">
        </div>
        <div class="carousel-item">
            <img src="img/react.jpg" class="d-block" alt="Third Image">
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

</section>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<jsp:include page="footer.jsp" />