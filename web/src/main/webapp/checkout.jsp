<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/checkout"/></c:if>
<c:set var="pageTitle" value="Checkout"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<h3 class="mb-3"><fmt:message key="checkout.title"/></h3>
<div class="row g-4">
  <div class="col-lg-8">
    <%-- 1. address --%>
    <div class="card shadow-sm mb-3"><div class="card-body">
      <h5><fmt:message key="checkout.address"/></h5>
      <form id="placeForm" action="${ctx}/checkout" method="post">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
        <input type="hidden" name="action" value="place">
        <c:forEach var="a" items="${addresses}" varStatus="st">
          <div class="form-check mb-2">
            <input class="form-check-input" type="radio" name="addressId" id="addr${a.addressId}" value="${a.addressId}" ${st.first ? 'checked' : ''} required>
            <label class="form-check-label" for="addr${a.addressId}"><span class="badge text-bg-light"><c:out value="${a.addressType}"/></span> <c:out value="${a.fullAddress}"/></label>
          </div>
        </c:forEach>
        <c:if test="${empty addresses}"><div class="alert alert-warning">Add a delivery address below to continue.</div></c:if>

        <hr><h5><fmt:message key="checkout.payment"/></h5>
        <div class="form-check"><input class="form-check-input" type="radio" name="paymentMethod" id="payCOD" value="COD" checked><label class="form-check-label" for="payCOD">Cash on delivery</label></div>
        <div class="form-check"><input class="form-check-input" type="radio" name="paymentMethod" id="payUPI" value="UPI"><label class="form-check-label" for="payUPI">UPI (mock)</label></div>
        <div class="form-check"><input class="form-check-input" type="radio" name="paymentMethod" id="payCARD" value="CARD"><label class="form-check-label" for="payCARD">Credit / debit card (mock)</label></div>
        <div class="mt-2 d-none" data-pay="UPI"><input class="form-control" name="upiId" placeholder="name@bank"></div>
        <div class="mt-2 d-none row g-2" data-pay="CARD">
          <div class="col-8"><input class="form-control" name="cardNumber" inputmode="numeric" maxlength="19" placeholder="16-digit card number (demo only, not stored)" autocomplete="off"></div>
          <div class="col-4"><input class="form-control" name="cvv" inputmode="numeric" maxlength="3" placeholder="CVV" autocomplete="off"></div>
        </div>
        <button class="btn btn-success btn-lg mt-3" type="submit" ${empty addresses ? 'disabled' : ''}><fmt:message key="checkout.place"/></button>
      </form>
    </div></div>

    <div class="card shadow-sm"><div class="card-body">
      <h6>Add a new address</h6>
      <form class="row g-2" action="${ctx}/checkout" method="post">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
        <input type="hidden" name="action" value="addAddress">
        <div class="col-12"><input class="form-control" name="addressLine" placeholder="House no, street, area" required minlength="5"></div>
        <div class="col-md-4"><input class="form-control" name="city" placeholder="City" required></div>
        <div class="col-md-4"><input class="form-control" name="state" placeholder="State" required></div>
        <div class="col-md-2"><input class="form-control" name="pincode" placeholder="Pincode" pattern="[1-9][0-9]{5}" required></div>
        <div class="col-md-2"><select class="form-select" name="addressType"><option>HOME</option><option>WORK</option></select></div>
        <div class="col-12"><button class="btn btn-outline-primary btn-sm" type="submit">Save address</button></div>
      </form>
    </div></div>
  </div>

  <div class="col-lg-4">
    <div class="card shadow-sm mb-3"><div class="card-body">
      <h5><fmt:message key="order.summary"/></h5>
      <c:forEach var="item" items="${cart.items}">
        <div class="d-flex justify-content-between small"><span><c:out value="${item.product.name}"/> &times; ${item.quantity}</span><span><sp:price amount="${item.lineTotal}"/></span></div>
      </c:forEach>
      <hr>
      <div class="d-flex justify-content-between"><span><fmt:message key="cart.subtotal"/></span><span><sp:price amount="${cart.subtotal}"/></span></div>
      <c:if test="${discount > 0}"><div class="d-flex justify-content-between text-success"><span><fmt:message key="order.discount"/> (<c:out value="${couponCode}"/>)</span><span>&minus;<sp:price amount="${discount}"/></span></div></c:if>
      <div class="d-flex justify-content-between fs-5 mt-1"><strong><fmt:message key="order.total"/></strong><strong><sp:price amount="${total}"/></strong></div>
    </div></div>
    <div class="card shadow-sm"><div class="card-body">
      <h6><fmt:message key="checkout.coupon"/></h6>
      <c:if test="${not empty couponError}"><div class="alert alert-warning py-1 small"><c:out value="${couponError}"/></div></c:if>
      <c:choose>
        <c:when test="${not empty couponCode}">
          <form action="${ctx}/checkout" method="post" class="d-flex justify-content-between align-items-center">
            <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="removeCoupon">
            <span class="badge text-bg-success"><c:out value="${couponCode}"/></span>
            <button class="btn btn-sm btn-link text-danger" type="submit">Remove</button>
          </form>
        </c:when>
        <c:otherwise>
          <form action="${ctx}/checkout" method="post" class="d-flex gap-2">
            <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="applyCoupon">
            <input class="form-control" name="code" placeholder="e.g. WELCOME10" maxlength="30">
            <button class="btn btn-outline-primary" type="submit">Apply</button>
          </form>
        </c:otherwise>
      </c:choose>
    </div></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
