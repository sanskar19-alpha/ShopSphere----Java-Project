<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/dashboard"/></c:if>
<c:set var="pageTitle" value="Dashboard"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="dashboard"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <h3 class="mb-3">Dashboard</h3>
  <div class="row g-3 mb-4">
    <div class="col-6 col-md-3"><div class="card stat-card bg-grad1"><div class="card-body"><div>Total orders</div><div class="fs-2 fw-bold">${totalOrders}</div></div></div></div>
    <div class="col-6 col-md-3"><div class="card stat-card bg-grad2"><div class="card-body"><div>Revenue</div><div class="fs-2 fw-bold"><sp:price amount="${revenue}"/></div></div></div></div>
    <div class="col-6 col-md-3"><div class="card stat-card bg-grad3"><div class="card-body"><div>Customers</div><div class="fs-2 fw-bold">${customerCount}</div></div></div></div>
    <div class="col-6 col-md-3"><div class="card stat-card bg-grad4"><div class="card-body"><div>Active products</div><div class="fs-2 fw-bold">${productCount}</div></div></div></div>
  </div>
  <div class="row g-3">
    <div class="col-md-6"><div class="card shadow-sm"><div class="card-header">Orders by status</div>
      <ul class="list-group list-group-flush">
        <c:forEach var="e" items="${statusCounts}">
          <li class="list-group-item d-flex justify-content-between"><a class="text-decoration-none" href="${ctx}/admin/orders?status=${e.key}"><sp:statusBadge status="${e.key}"/></a><strong>${e.value}</strong></li>
        </c:forEach>
      </ul></div></div>
    <div class="col-md-6"><div class="card shadow-sm"><div class="card-header">Top selling products</div>
      <ul class="list-group list-group-flush">
        <c:forEach var="t" items="${topProducts}"><li class="list-group-item d-flex justify-content-between"><span><c:out value="${t[0]}"/></span><span>${t[1]} sold &middot; <sp:price amount="${t[2]}"/></span></li></c:forEach>
        <c:if test="${empty topProducts}"><li class="list-group-item text-muted">No sales yet.</li></c:if>
      </ul></div></div>
    <div class="col-12"><div class="card shadow-sm"><div class="card-header d-flex justify-content-between">Low stock (10 or fewer)<a href="${ctx}/admin/inventory?view=low">View all</a></div>
      <ul class="list-group list-group-flush">
        <c:forEach var="p" items="${lowStock}"><li class="list-group-item d-flex justify-content-between"><span><c:out value="${p.name}"/></span><span class="${p.stock == 0 ? 'text-danger fw-bold' : 'text-warning'}">${p.stock} left</span></li></c:forEach>
        <c:if test="${empty lowStock}"><li class="list-group-item text-muted">All products are well stocked.</li></c:if>
      </ul></div></div>
  </div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
