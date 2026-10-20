<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="HTTP request"/>
<%@ include file="/fragments/header.jsp" %>
<h3>HTTP request / response</h3>
<table class="table table-sm bg-white">
  <tr><th>Method</th><td>${pageContext.request.method}</td></tr>
  <tr><th>Protocol</th><td>${pageContext.request.protocol}</td></tr>
  <tr><th>Request URI</th><td><c:out value="${pageContext.request.requestURI}"/></td></tr>
  <tr><th>Query string</th><td><c:out value="${pageContext.request.queryString}"/></td></tr>
  <tr><th>Remote address</th><td>${pageContext.request.remoteAddr}</td></tr>
  <tr><th>Parameters</th><td><c:forEach var="p" items="${param}"><code><c:out value="${p.key}"/></code>=<c:out value="${p.value}"/> </c:forEach></td></tr>
  <c:forEach var="h" items="${header}"><tr><th><c:out value="${h.key}"/></th><td><c:out value="${h.value}"/></td></tr></c:forEach>
</table>
<%@ include file="/fragments/footer.jsp" %>
