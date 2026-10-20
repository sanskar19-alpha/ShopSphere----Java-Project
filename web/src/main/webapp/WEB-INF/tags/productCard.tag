<%@ tag description="Reusable product card" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<%@ attribute name="product" required="true" type="com.shopsphere.model.Product" %>
<%-- TAG FILE: usage  <ss:productCard product="${p}"/>  (combines tag file + tag handler + JSTL + EL) --%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="card product-card">
    <a href="${ctx}/product?id=${product.productId}">
        <img src="${ctx}/${empty product.imageUrl ? 'images/placeholder.svg' : product.imageUrl}" class="card-img-top" alt="<c:out value='${product.name}'/>">
    </a>
    <div class="card-body d-flex flex-column">
        <small class="text-muted"><c:out value="${product.brand}"/></small>
        <h6 class="card-title mb-1">
            <a class="text-decoration-none text-dark" href="${ctx}/product?id=${product.productId}"><c:out value="${product.name}"/></a>
        </h6>
        <div class="mb-2"><ss:rating value="${product.avgRating}" count="${product.reviewCount}"/></div>
        <div class="mb-2">
            <span class="price"><sp:price amount="${product.finalPrice}"/></span>
            <c:if test="${product.discount > 0}">
                <span class="old-price ms-1"><sp:price amount="${product.price}"/></span>
                <span class="badge text-bg-success ms-1"><fmt:message key="product.off"><fmt:param value="${product.discount.intValue()}"/></fmt:message></span>
            </c:if>
        </div>
        <div class="mt-auto">
            <c:choose>
                <c:when test="${product.stock <= 0}">
                    <span class="badge text-bg-secondary"><fmt:message key="product.outOfStock"/></span>
                </c:when>
                <c:otherwise>
                    <c:if test="${product.lowStock}">
                        <div class="small text-danger mb-1"><fmt:message key="product.onlyLeft"><fmt:param value="${product.stock}"/></fmt:message></div>
                    </c:if>
                    <form action="${ctx}/cart" method="post" class="d-grid">
                        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <input type="hidden" name="back" value="${requestScope.selfPath}">
                        <button class="btn btn-primary btn-sm" type="submit"><fmt:message key="product.addToCart"/></button>
                    </form>
                </c:otherwise>
            </c:choose>
            <form action="${ctx}/wishlist" method="post" class="d-grid mt-1">
                <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="${product.productId}">
                <input type="hidden" name="back" value="${requestScope.selfPath}">
                <button class="btn btn-outline-secondary btn-sm" type="submit">&#9825; <fmt:message key="product.addToWishlist"/></button>
            </form>
        </div>
    </div>
</div>
