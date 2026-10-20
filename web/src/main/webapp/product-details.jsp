<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/products"/></c:if>
<c:set var="pageTitle" value="${product.name}"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<nav aria-label="breadcrumb"><ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${ctx}/home"><fmt:message key="nav.home"/></a></li>
  <li class="breadcrumb-item"><a href="${ctx}/products?category=${product.categoryId}"><c:out value="${product.categoryName}"/></a></li>
  <li class="breadcrumb-item active"><c:out value="${product.name}"/></li>
</ol></nav>
<div class="row g-4">
  <div class="col-md-5"><div class="card shadow-sm p-3">
    <img class="img-fluid" style="max-height:340px;object-fit:contain" src="${ctx}/${empty product.imageUrl ? 'images/placeholder.svg' : product.imageUrl}" alt="<c:out value='${product.name}'/>">
  </div></div>
  <div class="col-md-7">
    <small class="text-muted"><c:out value="${product.brand}"/></small>
    <h2><c:out value="${product.name}"/></h2>
    <ss:rating value="${product.avgRating}" count="${product.reviewCount}"/>
    <div class="my-3">
      <span class="price fs-3"><sp:price amount="${product.finalPrice}"/></span>
      <c:if test="${product.discount > 0}">
        <span class="old-price ms-2"><sp:price amount="${product.price}"/></span>
        <span class="badge text-bg-success ms-2"><fmt:message key="product.off"><fmt:param value="${product.discount.intValue()}"/></fmt:message></span>
      </c:if>
    </div>
    <p><c:out value="${product.description}"/></p>
    <c:choose>
      <c:when test="${product.stock <= 0}"><span class="badge text-bg-secondary fs-6"><fmt:message key="product.outOfStock"/></span></c:when>
      <c:otherwise>
        <p class="text-success mb-1"><fmt:message key="product.inStock"/>
          <c:if test="${product.lowStock}"> &ndash; <span class="text-danger"><fmt:message key="product.onlyLeft"><fmt:param value="${product.stock}"/></fmt:message></span></c:if></p>
        <form class="d-flex gap-2 align-items-center my-3" action="${ctx}/cart" method="post">
          <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
          <input type="hidden" name="action" value="add">
          <input type="hidden" name="productId" value="${product.productId}">
          <input type="hidden" name="back" value="${selfPath}">
          <input class="form-control" style="width:90px" type="number" name="quantity" value="1" min="1" max="${product.stock < 10 ? product.stock : 10}">
          <button class="btn btn-primary" type="submit"><fmt:message key="product.addToCart"/></button>
        </form>
      </c:otherwise>
    </c:choose>
    <form action="${ctx}/wishlist" method="post">
      <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
      <input type="hidden" name="action" value="add">
      <input type="hidden" name="productId" value="${product.productId}">
      <input type="hidden" name="back" value="${selfPath}">
      <button class="btn btn-outline-secondary" type="submit">&#9825; <fmt:message key="product.addToWishlist"/></button>
    </form>
  </div>
</div>

<hr class="my-4">
<h4><fmt:message key="product.reviews"/></h4>
<c:if test="${canReview}">
  <form class="card card-body mb-3" action="${ctx}/review" method="post">
    <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
    <input type="hidden" name="productId" value="${product.productId}">
    <div class="rating-input mb-2">
      <c:forEach var="i" begin="1" end="5">
        <c:set var="star" value="${6 - i}"/>
        <input type="radio" id="star${star}" name="rating" value="${star}" ${star == 5 ? 'checked' : ''}><label for="star${star}" title="${star}">&#9733;</label>
      </c:forEach>
    </div>
    <textarea class="form-control mb-2" name="reviewText" rows="2" maxlength="500" placeholder="Share your experience"></textarea>
    <button class="btn btn-primary btn-sm align-self-start" type="submit">Submit review</button>
  </form>
</c:if>
<c:forEach var="r" items="${reviews}">
  <div class="card card-body mb-2">
    <div><strong><c:out value="${r.userName}"/></strong> <ss:rating value="${r.rating}"/>
      <small class="text-muted ms-2"><fmt:formatDate value="${r.createdAt}" dateStyle="medium"/></small></div>
    <div><c:out value="${r.reviewText}"/></div>
  </div>
</c:forEach>
<c:if test="${empty reviews}"><p class="text-muted">No reviews yet.</p></c:if>

<c:if test="${not empty related}">
  <h4 class="mt-4"><fmt:message key="product.related"/></h4>
  <div class="row g-3">
    <c:forEach var="p" items="${related}"><div class="col-6 col-md-3"><ss:productCard product="${p}"/></div></c:forEach>
  </div>
</c:if>
<%@ include file="/fragments/footer.jsp" %>
