<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/orders"/></c:if>
<c:set var="pageTitle" value="Order ${order.orderId}"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="d-flex justify-content-between align-items-center mb-2">
  <h3 class="mb-0">Order #${order.orderId}</h3>
  <span><sp:statusBadge status="${order.orderStatus}"/></span>
</div>
<p class="text-muted">Placed on <fmt:formatDate value="${order.createdAt}" dateStyle="long" timeStyle="short" type="both"/></p>

<c:choose>
  <c:when test="${order.orderStatus == 'CANCELLED'}">
    <div class="alert alert-danger">This order was cancelled. Payment status: <sp:statusBadge status="${order.paymentStatus}"/></div>
  </c:when>
  <c:otherwise>
    <c:set var="stage" value="${order.orderStatus == 'PLACED' ? 1 : (order.orderStatus == 'PACKED' ? 2 : (order.orderStatus == 'SHIPPED' ? 3 : 4))}"/>
    <div class="timeline">
      <c:forEach var="label" items="${['Placed','Packed','Shipped','Delivered']}" varStatus="st">
        <div class="step ${st.count <= stage ? 'done' : ''}"><div class="dot">${st.count <= stage ? '&#10003;' : st.count}</div>${label}</div>
      </c:forEach>
    </div>
  </c:otherwise>
</c:choose>

<div class="card shadow-sm mb-3"><div class="table-responsive"><table class="table mb-0 align-middle">
  <thead><tr><th>Product</th><th>Price</th><th>Qty</th><th>Total</th></tr></thead>
  <tbody>
  <c:forEach var="i" items="${order.items}">
    <tr><td><a class="text-decoration-none" href="${ctx}/product?id=${i.productId}"><c:out value="${i.productName}"/></a></td>
        <td><sp:price amount="${i.price}"/></td><td>${i.quantity}</td><td><sp:price amount="${i.lineTotal}"/></td></tr>
  </c:forEach>
  </tbody>
  <tfoot>
    <c:if test="${order.discount > 0}"><tr><td colspan="3" class="text-end">Discount</td><td class="text-success">&minus;<sp:price amount="${order.discount}"/></td></tr></c:if>
    <tr><th colspan="3" class="text-end">Total paid / payable</th><th><sp:price amount="${order.totalAmount}"/></th></tr>
  </tfoot>
</table></div></div>
<p>Payment: <c:out value="${order.paymentMethod}"/> <sp:statusBadge status="${order.paymentStatus}"/></p>

<c:if test="${order.orderStatus == 'PLACED'}">
  <form action="${ctx}/order" method="post" data-confirm="Cancel this order?">
    <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
    <input type="hidden" name="action" value="cancel"><input type="hidden" name="id" value="${order.orderId}">
    <button class="btn btn-outline-danger" type="submit">Cancel order</button>
  </form>
</c:if>
<a class="btn btn-link" href="${ctx}/orders">&larr; All orders</a>
<%@ include file="/fragments/footer.jsp" %>
