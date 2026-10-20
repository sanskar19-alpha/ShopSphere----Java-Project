<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page import="java.util.Date, java.text.SimpleDateFormat" %>
<%-- JSP comment: not sent to the browser --%>
<%! // DECLARATION: becomes a field/method of the generated servlet class (shared by all requests)
    private int hitCount = 0;
    private String shout(String s) { return s.toUpperCase() + "!"; }
%>
<% // SCRIPTLET: goes into _jspService() (runs per request)
    hitCount++;
    String student = request.getParameter("name") == null ? "Student" : request.getParameter("name");
    String today = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss").format(new Date());
%>
<c:set var="pageTitle" value="JSP elements"/>
<%@ include file="/fragments/header.jsp" %>
<h3>JSP elements</h3>
<table class="table table-bordered bg-white">
  <tr><th style="width:30%">Element</th><th>Result</th></tr>
  <tr><td>Directive <code>&lt;%@ page %&gt;</code> / <code>&lt;%@ include %&gt;</code></td><td>page imports + static include of header/footer fragments</td></tr>
  <tr><td>Declaration <code>&lt;%! %&gt;</code></td><td>hitCount field = <b><%= hitCount %></b> (shared across requests), <%= shout("jsp") %></td></tr>
  <tr><td>Scriptlet <code>&lt;% %&gt;</code></td><td>local variable <code>student</code> set from ?name=</td></tr>
  <tr><td>Expression <code>&lt;%= %&gt;</code></td><td>Hello <b><%= student.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;") %></b>, now: <%= today %></td></tr>
  <tr><td>Expression Language</td><td>&#36;{1 + 2} = <b>${1 + 2}</b>, &#36;{param.name} = <b><c:out value="${param.name}"/></b></td></tr>
  <tr><td>Implicit objects</td><td>request URI: <code><%= request.getRequestURI() %></code>; session id exists: <%= session.getId() != null %>; application: <%= application.getServerInfo() %></td></tr>
  <tr><td><code>&lt;jsp:include&gt;</code> (dynamic)</td><td><jsp:include page="/demo/included-snippet.jsp"><jsp:param name="who" value="the including page"/></jsp:include></td></tr>
</table>
<p>JSP life cycle: translation to a servlet &rarr; compilation &rarr; <code>jspInit()</code> &rarr; <code>_jspService()</code> for each request &rarr; <code>jspDestroy()</code>.</p>
<%@ include file="/fragments/footer.jsp" %>
