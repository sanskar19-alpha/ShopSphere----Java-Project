<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="Syllabus demos"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<h3>RTU syllabus demonstrations</h3>
<p class="text-muted">Small, separate pages that show the syllabus topics. The main shopping flow does not depend on them.</p>
<div class="list-group shadow-sm">
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/jsp-elements.jsp"><b>JSP elements</b> &ndash; directives, declaration, scriptlet, expression, comment, implicit objects, jsp:include</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/jstl-functions.jsp"><b>JSTL core + functions + EL</b> &ndash; c:set, c:if, c:choose, c:forEach, fn:*</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/jstl-xml.jsp"><b>JSTL XML</b> &ndash; x:parse, x:forEach, x:out on an XML product feed</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/jstl-sql.jsp"><b>JSTL SQL</b> &ndash; sql:query (demonstration only; the app itself uses DAO/JDBC)</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/i18n.jsp"><b>Internationalization</b> &ndash; Locale, ResourceBundle, formatted numbers and dates</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/session-cookie.jsp"><b>Session and cookies</b> &ndash; HttpSession attributes, cookie reading/writing</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/request-info.jsp?name=Student&amp;topic=HTTP"><b>HTTP request / response</b> &ndash; headers, parameters, method, status</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/lifecycle"><b>Servlet lifecycle</b> &ndash; init/service/destroy, ServletConfig, ServletContext</a>
  <a class="list-group-item list-group-item-action" href="${ctx}/demo/jndi"><b>JNDI</b> &ndash; env-entry and DataSource lookup</a>
</div>
<%@ include file="/fragments/footer.jsp" %>
