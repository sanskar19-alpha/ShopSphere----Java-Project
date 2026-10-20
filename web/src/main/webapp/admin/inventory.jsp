<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/inventory"/></c:if>
<c:set var="pageTitle" value="Inventory"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="inventory"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Inventory <small class="text-muted fs-6">${onlyLow ? '(low stock only)' : '(all products)'}</small></h3>
    <a class="btn btn-outline-primary" href="${ctx}/admin/inventory${onlyLow ? '' : '?view=low'}">${onlyLow ? 'Show all' : 'Show low stock'}</a>
  </div>
  <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
    <thead><tr><th>#</th><th>Product</th><th>Category</th><th>Stock</th><th>Set stock</th></tr></thead><tbody>
    <c:forEach var="p" items="${products}">
      <tr class="${p.stock == 0 ? 'table-danger' : (p.stock <= 10 ? 'table-warning' : '')}">
        <td>${p.productId}</td><td><c:out value="${p.name}"/></td><td><c:out value="${p.categoryName}"/></td><td><strong>${p.stock}</strong></td>
        <td><form class="d-flex gap-1" action="${ctx}/admin/inventory" method="post" style="max-width:190px">
          <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="productId" value="${p.productId}"><input type="hidden" name="view" value="${onlyLow ? 'low' : ''}">
          <input class="form-control form-control-sm" type="number" min="0" name="stock" value="${p.stock}"><button class="btn btn-sm btn-primary" type="submit">Save</button></form></td></tr>
    </c:forEach></tbody></table></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
