<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/wishlist"/></c:if>
<c:set var="pageTitle" value="Wishlist"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<h3 class="mb-3"><fmt:message key="nav.wishlist"/></h3>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<c:if test="${empty items}"><div class="alert alert-info">Your wishlist is empty. <a href="${ctx}/products"><fmt:message key="nav.products"/></a></div></c:if>
<div class="row g-3">
<c:forEach var="w" items="${items}">
  <div class="col-6 col-md-4 col-lg-3">
    <div class="card product-card"><div class="card-body">
      <h6><a class="text-decoration-none text-dark" href="${ctx}/product?id=${w.productId}"><c:out value="${w.product.name}"/></a></h6>
      <div class="price mb-2"><sp:price amount="${w.product.finalPrice}"/></div>
      <form action="${ctx}/wishlist" method="post" class="d-grid gap-1">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="productId" value="${w.productId}">
        <button class="btn btn-primary btn-sm" name="action" value="toCart" type="submit" ${w.product.stock <= 0 ? 'disabled' : ''}>Move to cart</button>
        <button class="btn btn-outline-danger btn-sm" name="action" value="remove" type="submit">Remove</button>
      </form>
    </div></div>
  </div>
</c:forEach>
</div>
<%@ include file="/fragments/footer.jsp" %>
