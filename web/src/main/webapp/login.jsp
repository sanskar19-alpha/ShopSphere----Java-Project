<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/login"/></c:if>
<c:set var="pageTitle" value="${adminMode ? 'Admin login' : 'Login'}"/>
<%@ include file="/fragments/header.jsp" %>
<div class="row justify-content-center">
  <div class="col-md-5">
    <div class="card shadow-sm"><div class="card-body p-4">
      <h3 class="mb-3"><c:choose><c:when test="${adminMode}">Admin login</c:when><c:otherwise><fmt:message key="nav.login"/></c:otherwise></c:choose></h3>
      <c:if test="${param.loggedout == '1'}"><div class="alert alert-info">You have been logged out.</div></c:if>
      <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
      <form method="post" action="${pageContext.request.contextPath}${adminMode ? '/admin-login' : '/login'}">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
        <input type="hidden" name="next" value="<c:out value='${next}'/>">
        <div class="mb-3"><label class="form-label" for="email">Email</label>
          <input class="form-control" id="email" type="email" name="email" value="<c:out value='${email}'/>" required autofocus></div>
        <div class="mb-3"><label class="form-label" for="password">Password</label>
          <input class="form-control" id="password" type="password" name="password" required></div>
        <button class="btn btn-primary w-100" type="submit"><fmt:message key="nav.login"/></button>
      </form>
      <hr>
      <c:choose>
        <c:when test="${adminMode}"><a href="${pageContext.request.contextPath}/login">Customer login</a></c:when>
        <c:otherwise>
          New here? <a href="${pageContext.request.contextPath}/register"><fmt:message key="nav.register"/></a> &middot;
          <a href="${pageContext.request.contextPath}/admin-login">Admin login</a>
        </c:otherwise>
      </c:choose>
    </div></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
