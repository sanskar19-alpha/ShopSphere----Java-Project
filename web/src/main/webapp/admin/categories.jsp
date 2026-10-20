<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/categories"/></c:if>
<c:set var="pageTitle" value="Categories"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="categories"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <h3 class="mb-3">Categories</h3>
  <div class="card shadow-sm mb-4"><div class="card-body">
    <h6>${empty form ? 'New category' : 'Edit category'}</h6>
    <form class="row g-2" action="${ctx}/admin/categories" method="post">
      <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="save">
      <input type="hidden" name="categoryId" value="${empty form ? 0 : form.categoryId}">
      <div class="col-md-4"><input class="form-control" name="name" placeholder="Name" value="<c:out value='${form.name}'/>" required></div>
      <div class="col-md-5"><input class="form-control" name="description" placeholder="Description" value="<c:out value='${form.description}'/>"></div>
      <div class="col-md-2 form-check pt-2 ms-2"><input class="form-check-input" type="checkbox" id="st" name="status" ${empty form or form.status ? 'checked' : ''}><label class="form-check-label" for="st">Active</label></div>
      <div class="col-auto"><button class="btn btn-primary" type="submit">Save</button>
        <c:if test="${not empty form}"><a class="btn btn-link" href="${ctx}/admin/categories">Cancel</a></c:if></div>
    </form>
  </div></div>
  <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
    <thead><tr><th>#</th><th>Name</th><th>Description</th><th>Status</th><th></th></tr></thead><tbody>
    <c:forEach var="c" items="${categories}">
      <tr><td>${c.categoryId}</td><td><c:out value="${c.name}"/></td><td><c:out value="${c.description}"/></td>
        <td><span class="badge text-bg-${c.status ? 'success' : 'secondary'}">${c.status ? 'ACTIVE' : 'HIDDEN'}</span></td>
        <td class="text-nowrap"><a class="btn btn-sm btn-outline-primary" href="${ctx}/admin/categories?edit=${c.categoryId}">Edit</a>
          <form class="d-inline" action="${ctx}/admin/categories" method="post" data-confirm="Delete this category?">
            <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="delete"><input type="hidden" name="categoryId" value="${c.categoryId}">
            <button class="btn btn-sm btn-outline-danger" type="submit">Delete</button></form></td></tr>
    </c:forEach></tbody></table></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
