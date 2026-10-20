<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%
    // Write a session attribute and a cookie, then show what the browser sent back.
    Integer visits = (Integer) session.getAttribute("demoVisits");
    session.setAttribute("demoVisits", visits == null ? 1 : visits + 1);
    jakarta.servlet.http.Cookie c = new jakarta.servlet.http.Cookie("demoCookie", "hello-" + System.currentTimeMillis() % 10000);
    c.setMaxAge(300); c.setHttpOnly(true); c.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
    response.addCookie(c);
%>
<c:set var="pageTitle" value="Session and cookies"/>
<%@ include file="/fragments/header.jsp" %>
<h3>Session and cookies</h3>
<ul>
  <li>Session id (server side): <code><c:out value="${pageContext.session.id}"/></code>, new? <%= session.isNew() %></li>
  <li>Session attribute <code>demoVisits</code> = <b>${sessionScope.demoVisits}</b> (reload the page)</li>
  <li>Max inactive interval: ${pageContext.session.maxInactiveInterval} seconds</li>
  <li>Cookies sent by the browser:
    <ul><c:forEach var="ck" items="${cookie}"><li><code><c:out value="${ck.key}"/></code> = <c:out value="${ck.value.value}"/></li></c:forEach></ul>
    <small class="text-muted">The session cookie JSESSIONID is HttpOnly so it is hidden from JavaScript. ShopSphere also uses cookies "lang" and "recentlyViewed" (non-sensitive only).</small></li>
</ul>
<%@ include file="/fragments/footer.jsp" %>
