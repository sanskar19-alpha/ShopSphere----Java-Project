<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="JSTL core and functions"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="text" value="  Advanced Java Programming  "/>
<c:set var="langs" value="${['Java','Servlet','JSP','JDBC','RMI']}"/>
<c:set var="score" value="72"/>
<h3>JSTL core, functions and EL</h3>
<div class="row g-3">
<div class="col-md-6"><div class="card card-body">
  <h6>Core tags</h6>
  <c:choose>
    <c:when test="${score >= 80}">Grade A</c:when>
    <c:when test="${score >= 60}">Grade B (score ${score})</c:when>
    <c:otherwise>Grade C</c:otherwise>
  </c:choose>
  <c:if test="${fn:length(langs) > 3}"><div>The list has more than 3 items.</div></c:if>
  <ul class="mb-0"><c:forEach var="l" items="${langs}" varStatus="s"><li>${s.count}. ${l} ${s.last ? '(last)' : ''}</li></c:forEach></ul>
  <c:forTokens var="t" items="a,b,c" delims=","><span class="badge text-bg-secondary me-1">${t}</span></c:forTokens>
</div></div>
<div class="col-md-6"><div class="card card-body">
  <h6>Function tags</h6>
  <div>fn:trim &rarr; "<b>${fn:trim(text)}</b>"</div>
  <div>fn:toUpperCase &rarr; ${fn:toUpperCase(fn:trim(text))}</div>
  <div>fn:length(text) &rarr; ${fn:length(text)}</div>
  <div>fn:contains(text,'Java') &rarr; ${fn:contains(text,'Java')}</div>
  <div>fn:replace &rarr; ${fn:replace(fn:trim(text),'Java','JEE')}</div>
  <div>fn:split + join &rarr; ${fn:join(fn:split('a-b-c','-'),' | ')}</div>
  <div>fn:substring(text,2,10) &rarr; ${fn:substring(text,2,10)}</div>
  <div>fn:escapeXml &rarr; ${fn:escapeXml('<b>bold</b>')}</div>
  <div>EL operators &rarr; ${10 mod 3}, ${empty langs}, ${5 gt 3 and 2 lt 1}</div>
</div></div>
</div>
<%@ include file="/fragments/footer.jsp" %>
