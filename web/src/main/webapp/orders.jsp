<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/orders"/></c:if>
<c:set var="pageTitle" value="My orders"/>
<%@ include file="/fragments/header.jsp" %>
<h3 class="mb-3"><fmt:message key="nav.orders"/></h3>
<c:choose>
<c:when test="${empty orders}"><div class="alert alert-info">You have not placed any orders yet. <a href="${pageContext.request.contextPath}/products"><fmt:message key="nav.products"/></a></div></c:when>
<c:otherwise>
<div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
  <thead><tr><th>Order</th><th>Date</th><th>Items</th><th>Total</th><th>Payment</th><th>Status</th><th></th></tr></thead>
  <tbody>
  <c:forEach var="o" items="${orders}">
    <tr>
      <td>#${o.orderId}</td>
      <td><fmt:formatDate value="${o.createdAt}" dateStyle="medium"/></td>
      <td>${fn:length(o.items)}</td>
      <td><sp:price amount="${o.totalAmount}"/></td>
      <td><c:out value="${o.paymentMethod}"/> <sp:statusBadge status="${o.paymentStatus}"/></td>
      <td><sp:statusBadge status="${o.orderStatus}"/></td>
      <td><a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/order?id=${o.orderId}">Details</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table></div>
</c:otherwise>
</c:choose>
<%@ include file="/fragments/footer.jsp" %>
