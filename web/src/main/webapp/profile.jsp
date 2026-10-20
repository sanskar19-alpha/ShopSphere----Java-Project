<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/profile"/></c:if>
<c:set var="pageTitle" value="Profile"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<h3 class="mb-3"><fmt:message key="nav.profile"/></h3>
<div class="row g-4">
  <div class="col-lg-6">
    <div class="card shadow-sm mb-3"><div class="card-body">
      <h5>Personal details</h5>
      <form action="${ctx}/profile" method="post">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="update">
        <div class="mb-2"><label class="form-label">Email</label><input class="form-control" value="<c:out value='${sessionScope.user.email}'/>" disabled></div>
        <div class="mb-2"><label class="form-label" for="name">Name</label><input class="form-control" id="name" name="name" value="<c:out value='${sessionScope.user.name}'/>" required></div>
        <div class="mb-3"><label class="form-label" for="mobile">Mobile</label><input class="form-control" id="mobile" name="mobile" value="<c:out value='${sessionScope.user.mobile}'/>" pattern="[6-9][0-9]{9}" required></div>
        <button class="btn btn-primary" type="submit">Save</button>
      </form>
    </div></div>
    <div class="card shadow-sm"><div class="card-body">
      <h5>Change password</h5>
      <form action="${ctx}/profile" method="post">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="password">
        <input class="form-control mb-2" type="password" name="current" placeholder="Current password" required>
        <input class="form-control mb-2" type="password" name="next" placeholder="New password (8+ chars, letter and digit)" required minlength="8">
        <input class="form-control mb-3" type="password" name="confirm" placeholder="Repeat new password" required>
        <button class="btn btn-outline-primary" type="submit">Change password</button>
      </form>
    </div></div>
  </div>
  <div class="col-lg-6"><div class="card shadow-sm"><div class="card-body">
    <h5>Saved addresses</h5>
    <c:forEach var="a" items="${addresses}">
      <div class="d-flex justify-content-between align-items-start border-bottom py-2">
        <div><span class="badge text-bg-light"><c:out value="${a.addressType}"/></span> <c:out value="${a.fullAddress}"/></div>
        <form action="${ctx}/profile" method="post" data-confirm="Delete this address?">
          <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="deleteAddress"><input type="hidden" name="addressId" value="${a.addressId}">
          <button class="btn btn-sm btn-outline-danger" type="submit">Delete</button>
        </form>
      </div>
    </c:forEach>
    <c:if test="${empty addresses}"><p class="text-muted">No addresses saved yet.</p></c:if>
    <h6 class="mt-3">Add address</h6>
    <form class="row g-2" action="${ctx}/profile" method="post">
      <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="addAddress">
      <div class="col-12"><input class="form-control" name="addressLine" placeholder="House no, street, area" required minlength="5"></div>
      <div class="col-md-5"><input class="form-control" name="city" placeholder="City" required></div>
      <div class="col-md-4"><input class="form-control" name="state" placeholder="State" required></div>
      <div class="col-md-3"><input class="form-control" name="pincode" placeholder="Pincode" pattern="[1-9][0-9]{5}" required></div>
      <div class="col-md-4"><select class="form-select" name="addressType"><option>HOME</option><option>WORK</option></select></div>
      <div class="col-12"><button class="btn btn-primary btn-sm" type="submit">Add address</button></div>
    </form>
  </div></div></div>
</div>
<%@ include file="/fragments/footer.jsp" %>
