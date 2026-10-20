<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="JNDI"/>
<%@ include file="/fragments/header.jsp" %>
<h3>JNDI lookups</h3>
<p>The web container publishes resources under <code>java:comp/env</code>. Servlets find them with <code>InitialContext.lookup()</code>.</p>
<ul><c:forEach var="l" items="${jndiLines}"><li class="${fn:startsWith(l,'OK') ? 'text-success' : 'text-danger'}"><c:out value="${l}"/></li></c:forEach></ul>
<%@ include file="/fragments/footer.jsp" %>
