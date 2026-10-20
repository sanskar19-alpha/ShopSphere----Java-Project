<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><jsp:forward page="/home"/></c:if>
<c:set var="pageTitle" value="Home"/>
<%@ include file="/fragments/header.jsp" %>

<section class="hero mb-4">
    <h1 class="display-5 fw-bold"><fmt:message key="home.hero.title"/></h1>
    <p class="lead"><fmt:message key="home.hero.subtitle"/></p>
    <a class="btn btn-warning btn-lg" href="${pageContext.request.contextPath}/products"><fmt:message key="home.hero.cta"/></a>
</section>

<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>

<h4 class="mb-3"><fmt:message key="home.categories"/></h4>
<div class="mb-4 d-flex flex-wrap gap-2">
    <c:forEach var="cat" items="${categories}">
        <a class="btn btn-outline-primary cat-pill" href="${pageContext.request.contextPath}/products?category=${cat.categoryId}"><c:out value="${cat.name}"/></a>
    </c:forEach>
</div>

<h4 class="mb-3"><fmt:message key="home.featured"/></h4>
<div class="row g-3 mb-4">
    <c:forEach var="p" items="${featured}">
        <div class="col-6 col-md-4 col-lg-3"><ss:productCard product="${p}"/></div>
    </c:forEach>
</div>

<c:if test="${not empty recent}">
    <h4 class="mb-3"><fmt:message key="home.recent"/></h4>
    <div class="row g-3">
        <c:forEach var="p" items="${recent}">
            <div class="col-6 col-md-4 col-lg-3"><ss:productCard product="${p}"/></div>
        </c:forEach>
    </div>
</c:if>
<%@ include file="/fragments/footer.jsp" %>
