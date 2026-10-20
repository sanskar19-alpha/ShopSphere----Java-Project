<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/orders"/></c:if>
<c:set var="pageTitle" value="Orders"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="orders"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Orders</h3>
    <form method="get" action="${ctx}/admin/orders"><select class="form-select" name="status" data-autosubmit>
      <option value="">All statuses</option>
      <c:forEach var="s" items="${statuses}"><option value="${s}" ${s == filter ? 'selected' : ''}>${s}</option></c:forEach>
    </select></form>
  </div>
  <c:if test="${not empty order}">
    <div class="card shadow-sm mb-3"><div class="card-body">
      <h5>Order #${order.orderId} &ndash; <c:out value="${order.userName}"/></h5>
      <ul class="mb-0"><c:forEach var="i" items="${order.items}"><li><c:out value="${i.productName}"/> &times; ${i.quantity} @ <sp:price amount="${i.price}"/></li></c:forEach></ul>
    </div></div>
  </c:if>
  <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
    <thead><tr><th>Order</th><th>Customer</th><th>Date</th><th>Total</th><th>Payment</th><th>Status</th><th>Update</th></tr></thead><tbody>
    <c:forEach var="o" items="${orders}">
      <tr>
        <td><a href="${ctx}/admin/orders?id=${o.orderId}">#${o.orderId}</a></td><td><c:out value="${o.userName}"/></td>
        <td><fmt:formatDate value="${o.createdAt}" dateStyle="medium"/></td><td><sp:price amount="${o.totalAmount}"/></td>
        <td><c:out value="${o.paymentMethod}"/> <sp:statusBadge status="${o.paymentStatus}"/></td><td><sp:statusBadge status="${o.orderStatus}"/></td>
        <td>
          <c:if test="${o.orderStatus != 'DELIVERED' and o.orderStatus != 'CANCELLED'}">
          <form class="d-flex gap-1" action="${ctx}/admin/orders" method="post">
            <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="orderId" value="${o.orderId}">
            <select class="form-select form-select-sm" name="status"><c:forEach var="s" items="${statuses}"><option ${s == o.orderStatus ? 'selected' : ''}>${s}</option></c:forEach></select>
            <button class="btn btn-sm btn-primary" type="submit">Set</button>
          </form></c:if>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty orders}"><tr><td colspan="7" class="text-muted">No orders.</td></tr></c:if>
    </tbody></table></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
