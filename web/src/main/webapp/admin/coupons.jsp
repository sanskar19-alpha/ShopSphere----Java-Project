<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/coupons"/></c:if>
<c:set var="pageTitle" value="Coupons"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="coupons"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <h3 class="mb-3">Coupons</h3>
  <div class="card shadow-sm mb-4"><div class="card-body">
    <h6>New coupon</h6>
    <form class="row g-2" action="${ctx}/admin/coupons" method="post">
      <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="add">
      <div class="col-md-2"><input class="form-control" name="code" placeholder="CODE" required pattern="[A-Za-z0-9]{3,30}"></div>
      <div class="col-md-2"><select class="form-select" name="discountType"><option>PERCENT</option><option>FLAT</option></select></div>
      <div class="col-md-2"><input class="form-control" type="number" step="0.01" min="0.01" name="discountValue" placeholder="Value" required></div>
      <div class="col-md-2"><input class="form-control" type="number" step="1" min="0" name="minimumOrder" placeholder="Min order"></div>
      <div class="col-md-2"><input class="form-control" type="number" step="1" min="0" name="maximumDiscount" placeholder="Max discount"></div>
      <div class="col-md-2"><input class="form-control" type="date" name="expiryDate"></div>
      <div class="col-12"><button class="btn btn-primary" type="submit">Create</button></div>
    </form>
  </div></div>
  <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
    <thead><tr><th>Code</th><th>Type</th><th>Value</th><th>Min order</th><th>Max disc.</th><th>Expires</th><th>Status</th><th></th></tr></thead><tbody>
    <c:forEach var="c" items="${coupons}">
      <tr><td><strong><c:out value="${c.code}"/></strong></td><td>${c.discountType}</td><td>${c.discountValue}</td><td>${c.minimumOrder}</td><td>${c.maximumDiscount}</td>
        <td><c:out value="${c.expiryDate}"/></td><td><span class="badge text-bg-${c.status ? 'success' : 'secondary'}">${c.status ? 'ACTIVE' : 'OFF'}</span></td>
        <td><form action="${ctx}/admin/coupons" method="post"><input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="toggle"><input type="hidden" name="couponId" value="${c.couponId}"><input type="hidden" name="active" value="${c.status ? 0 : 1}">
          <button class="btn btn-sm btn-outline-secondary" type="submit">${c.status ? 'Disable' : 'Enable'}</button></form></td></tr>
    </c:forEach></tbody></table></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
