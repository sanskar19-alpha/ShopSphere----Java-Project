<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/users"/></c:if>
<c:set var="pageTitle" value="Customers"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="users"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <h3 class="mb-3">Customers</h3>
  <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
    <thead><tr><th>#</th><th>Name</th><th>Email</th><th>Mobile</th><th>Joined</th><th>Status</th><th></th></tr></thead><tbody>
    <c:forEach var="u" items="${customers}">
      <tr><td>${u.userId}</td><td><c:out value="${u.name}"/></td><td><c:out value="${u.email}"/></td><td><c:out value="${u.mobile}"/></td>
        <td><fmt:formatDate value="${u.createdAt}" dateStyle="medium"/></td>
        <td><span class="badge text-bg-${u.status ? 'success' : 'danger'}">${u.status ? 'ACTIVE' : 'BLOCKED'}</span></td>
        <td><form action="${ctx}/admin/users" method="post">
          <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="userId" value="${u.userId}">
          <button class="btn btn-sm btn-outline-${u.status ? 'danger' : 'success'}" name="action" value="${u.status ? 'block' : 'unblock'}" type="submit">${u.status ? 'Block' : 'Unblock'}</button>
        </form></td></tr>
    </c:forEach>
    <c:if test="${empty customers}"><tr><td colspan="7" class="text-muted">No customers yet.</td></tr></c:if>
    </tbody></table></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
