<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/products"/></c:if>
<c:set var="pageTitle" value="Products"/>
<%@ include file="/fragments/header.jsp" %>
<div class="row">
  <aside class="col-lg-3 mb-4">
    <form class="card card-body shadow-sm" method="get" action="${pageContext.request.contextPath}/products">
      <h6><fmt:message key="products.filter"/></h6>
      <input type="hidden" name="q" value="<c:out value='${q}'/>">
      <label class="form-label mt-2" for="category"><fmt:message key="products.category"/></label>
      <select class="form-select" id="category" name="category">
        <option value=""><fmt:message key="products.allCategories"/></option>
        <c:forEach var="cat" items="${categories}">
          <option value="${cat.categoryId}" ${cat.categoryId == selectedCategory ? 'selected' : ''}><c:out value="${cat.name}"/></option>
        </c:forEach>
      </select>
      <label class="form-label mt-2" for="min"><fmt:message key="products.minPrice"/></label>
      <input class="form-control" id="min" name="min" type="number" min="0" step="1" value="<c:out value='${min}'/>">
      <label class="form-label mt-2" for="max"><fmt:message key="products.maxPrice"/></label>
      <input class="form-control" id="max" name="max" type="number" min="0" step="1" value="<c:out value='${max}'/>">
      <label class="form-label mt-2" for="sort"><fmt:message key="products.sort"/></label>
      <select class="form-select" id="sort" name="sort">
        <option value="newest" ${sort == 'newest' ? 'selected' : ''}><fmt:message key="products.sort.newest"/></option>
        <option value="price_asc" ${sort == 'price_asc' ? 'selected' : ''}><fmt:message key="products.sort.priceAsc"/></option>
        <option value="price_desc" ${sort == 'price_desc' ? 'selected' : ''}><fmt:message key="products.sort.priceDesc"/></option>
        <option value="rating" ${sort == 'rating' ? 'selected' : ''}><fmt:message key="products.sort.rating"/></option>
        <option value="name" ${sort == 'name' ? 'selected' : ''}><fmt:message key="products.sort.name"/></option>
      </select>
      <button class="btn btn-primary mt-3" type="submit"><fmt:message key="products.apply"/></button>
      <a class="btn btn-link" href="${pageContext.request.contextPath}/products"><fmt:message key="products.reset"/></a>
    </form>
  </aside>
  <section class="col-lg-9">
    <h4 class="mb-3"><fmt:message key="products.title"/>
      <c:if test="${not empty q}"><small class="text-muted">&ndash; &ldquo;<c:out value="${q}"/>&rdquo;</small></c:if>
      <small class="text-muted fs-6">(${fn:length(products)})</small></h4>
    <c:choose>
      <c:when test="${empty products}"><div class="alert alert-warning"><fmt:message key="products.none"/></div></c:when>
      <c:otherwise>
        <div class="row g-3">
          <c:forEach var="p" items="${products}">
            <div class="col-6 col-md-4"><ss:productCard product="${p}"/></div>
          </c:forEach>
        </div>
      </c:otherwise>
    </c:choose>
  </section>
</div>
<%@ include file="/fragments/footer.jsp" %>
