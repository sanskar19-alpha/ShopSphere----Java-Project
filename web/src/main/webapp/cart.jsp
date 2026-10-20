<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/cart"/></c:if>
<c:set var="pageTitle" value="Cart"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<h3 class="mb-3"><fmt:message key="cart.title"/></h3>
<c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
<c:choose>
<c:when test="${empty cart.items}">
  <div class="alert alert-info"><fmt:message key="cart.empty"/> <a href="${ctx}/products"><fmt:message key="cart.continue"/></a></div>
</c:when>
<c:otherwise>
<div class="row g-4">
  <div class="col-lg-8">
    <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
      <thead><tr><th>Product</th><th>Price</th><th style="width:150px">Qty</th><th>Total</th><th></th></tr></thead>
      <tbody>
      <c:forEach var="item" items="${cart.items}">
        <tr>
          <td><a class="text-decoration-none" href="${ctx}/product?id=${item.productId}"><c:out value="${item.product.name}"/></a>
              <c:if test="${item.product.stock < item.quantity}"><div class="small text-danger">Only ${item.product.stock} in stock</div></c:if></td>
          <td><sp:price amount="${item.product.finalPrice}"/></td>
          <td>
            <form class="d-flex gap-1" action="${ctx}/cart" method="post">
              <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
              <input type="hidden" name="action" value="update">
              <input type="hidden" name="productId" value="${item.productId}">
              <input class="form-control form-control-sm" type="number" name="quantity" value="${item.quantity}" min="0" max="10">
              <button class="btn btn-sm btn-outline-primary" type="submit">Update</button>
            </form>
          </td>
          <td><strong><sp:price amount="${item.lineTotal}"/></strong></td>
          <td>
            <form action="${ctx}/cart" method="post" data-confirm="Remove this item?">
              <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
              <input type="hidden" name="action" value="remove">
              <input type="hidden" name="productId" value="${item.productId}">
              <button class="btn btn-sm btn-outline-danger" type="submit">&times;</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table></div>
  </div>
  <div class="col-lg-4"><div class="card shadow-sm"><div class="card-body">
    <h5><fmt:message key="order.summary"/></h5>
    <div class="d-flex justify-content-between"><span>Items</span><span>${cart.itemCount}</span></div>
    <div class="d-flex justify-content-between fs-5"><span><fmt:message key="cart.subtotal"/></span><strong><sp:price amount="${cart.subtotal}"/></strong></div>
    <a class="btn btn-primary w-100 mt-3" href="${ctx}/checkout"><fmt:message key="cart.checkout"/></a>
    <a class="btn btn-link w-100" href="${ctx}/products"><fmt:message key="cart.continue"/></a>
  </div></div></div>
</div>
</c:otherwise>
</c:choose>
<%@ include file="/fragments/footer.jsp" %>
