<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="Internationalization"/>
<%@ include file="/fragments/header.jsp" %>
<h3>Internationalization (i18n)</h3>
<p>Current locale: <b>${sessionScope.lang == 'hi' ? 'hi_IN' : 'en_IN'}</b> &mdash; switch with the language link in the navbar.
Texts come from <code>messages_en.properties</code> / <code>messages_hi.properties</code> through <code>ResourceBundle</code>.</p>
<div class="row g-3">
<div class="col-md-6"><div class="card card-body">
  <h6>fmt:message (ResourceBundle)</h6>
  <div><fmt:message key="home.hero.title"/></div>
  <div><fmt:message key="cart.title"/> / <fmt:message key="checkout.place"/></div>
  <div><fmt:message key="product.onlyLeft"><fmt:param value="3"/></fmt:message></div>
</div></div>
<div class="col-md-6"><div class="card card-body">
  <h6>Formatted values (java.text)</h6>
  <jsp:useBean id="now" class="java.util.Date"/>
  <div>Currency: <fmt:formatNumber value="1234567.5" type="currency" currencySymbol="&#8377;"/></div>
  <div>Number: <fmt:formatNumber value="1234567.891" maxFractionDigits="2"/></div>
  <div>Percent: <fmt:formatNumber value="0.256" type="percent"/></div>
  <div>Date: <fmt:formatDate value="${now}" dateStyle="full"/></div>
  <div>Time: <fmt:formatDate value="${now}" type="time" timeStyle="medium"/></div>
</div></div>
</div>
<%@ include file="/fragments/footer.jsp" %>
