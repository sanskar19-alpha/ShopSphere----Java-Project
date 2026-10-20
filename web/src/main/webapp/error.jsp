<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<c:set var="pageTitle" value="Error"/>
<%@ include file="/fragments/header.jsp" %>
<div class="text-center py-5">
  <div class="display-1 text-danger">${requestScope['jakarta.servlet.error.status_code']}</div>
  <h3>
    <c:choose>
      <c:when test="${requestScope['jakarta.servlet.error.status_code'] == 403}">Access denied</c:when>
      <c:when test="${requestScope['jakarta.servlet.error.status_code'] == 404}">Page not found</c:when>
      <c:otherwise>Something went wrong</c:otherwise>
    </c:choose>
  </h3>
  <p class="text-muted"><c:out value="${requestScope['jakarta.servlet.error.message']}"/></p>
  <a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Back to home</a>
</div>
<%@ include file="/fragments/footer.jsp" %>
