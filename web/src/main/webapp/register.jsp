<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/register"/></c:if>
<c:set var="pageTitle" value="Register"/>
<%@ include file="/fragments/header.jsp" %>
<div class="row justify-content-center"><div class="col-md-6">
  <div class="card shadow-sm"><div class="card-body p-4">
    <h3 class="mb-3"><fmt:message key="nav.register"/></h3>
    <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
    <form method="post" action="${pageContext.request.contextPath}/register">
      <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
      <div class="mb-3"><label class="form-label" for="name">Full name</label>
        <input class="form-control" id="name" name="name" value="<c:out value='${name}'/>" required minlength="2" maxlength="100"></div>
      <div class="mb-3"><label class="form-label" for="email">Email</label>
        <input class="form-control" id="email" type="email" name="email" value="<c:out value='${email}'/>" required></div>
      <div class="mb-3"><label class="form-label" for="mobile">Mobile (10 digits)</label>
        <input class="form-control" id="mobile" name="mobile" value="<c:out value='${mobile}'/>" required pattern="[6-9][0-9]{9}"></div>
      <div class="row">
        <div class="col-md-6 mb-3"><label class="form-label" for="password">Password</label>
          <input class="form-control" id="password" type="password" name="password" required minlength="8">
          <div class="form-text">8+ characters with a letter and a digit.</div></div>
        <div class="col-md-6 mb-3"><label class="form-label" for="confirm">Confirm password</label>
          <input class="form-control" id="confirm" type="password" name="confirm" required></div>
      </div>
      <button class="btn btn-primary w-100" type="submit"><fmt:message key="nav.register"/></button>
    </form>
    <hr>Already registered? <a href="${pageContext.request.contextPath}/login"><fmt:message key="nav.login"/></a>
  </div></div>
</div></div>
<%@ include file="/fragments/footer.jsp" %>
