<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/orders"/></c:if>
<c:set var="pageTitle" value="Order placed"/>
<%@ include file="/fragments/header.jsp" %>
<div class="text-center py-4">
  <div class="display-3 text-success">&#10004;</div>
  <h2>Thank you, <c:out value="${sessionScope.user.name}"/>!</h2>
  <p class="lead">Your order <strong>#${order.orderId}</strong> has been placed.</p>
  <p>Total: <strong><sp:price amount="${order.totalAmount}"/></strong> &middot; Payment: <c:out value="${order.paymentMethod}"/> <sp:statusBadge status="${order.paymentStatus}"/></p>
  <a class="btn btn-primary" href="${pageContext.request.contextPath}/order?id=${order.orderId}">Track order</a>
  <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/products"><fmt:message key="cart.continue"/></a>
</div>
<%@ include file="/fragments/footer.jsp" %>
