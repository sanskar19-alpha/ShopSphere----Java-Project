<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ss" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sp" uri="http://shopsphere.com/tags" %>
<c:if test="${empty requestScope.loaded}"><c:redirect url="/admin/products"/></c:if>
<c:set var="pageTitle" value="Products"/>
<%@ include file="/fragments/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="row">
  <div class="col-lg-2">
    <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="products"/></jsp:include>
  </div>
  <div class="col-lg-10">
  <c:if test="${not empty error}"><div class="alert alert-danger"><c:out value="${error}"/></div></c:if>
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Products</h3><a class="btn btn-primary" href="${ctx}/admin/products?edit=new">+ New product</a>
  </div>
  <c:if test="${not empty form}">
    <div class="card shadow-sm mb-4"><div class="card-body">
      <h5>${form.productId > 0 ? 'Edit product' : 'New product'}</h5>
      <form class="row g-2" action="${ctx}/admin/products" method="post">
        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="save">
        <input type="hidden" name="productId" value="${form.productId}">
        <div class="col-md-6"><label class="form-label">Name</label><input class="form-control" name="name" value="<c:out value='${form.name}'/>" required></div>
        <div class="col-md-3"><label class="form-label">Brand</label><input class="form-control" name="brand" value="<c:out value='${form.brand}'/>"></div>
        <div class="col-md-3"><label class="form-label">Category</label>
          <select class="form-select" name="categoryId" required>
            <c:forEach var="c" items="${categories}"><option value="${c.categoryId}" ${c.categoryId == form.categoryId ? 'selected' : ''}><c:out value="${c.name}"/></option></c:forEach>
          </select></div>
        <div class="col-12"><label class="form-label">Description</label><textarea class="form-control" name="description" rows="2"><c:out value="${form.description}"/></textarea></div>
        <div class="col-md-3"><label class="form-label">Price</label><input class="form-control" type="number" step="0.01" min="0.01" name="price" value="${form.price > 0 ? form.price : ''}" required></div>
        <div class="col-md-3"><label class="form-label">Discount %</label><input class="form-control" type="number" step="0.01" min="0" max="90" name="discount" value="${form.discount}"></div>
        <div class="col-md-3"><label class="form-label">Stock</label><input class="form-control" type="number" min="0" name="stock" value="${form.stock}" required></div>
        <div class="col-md-3"><label class="form-label">Image path</label><input class="form-control" name="imageUrl" value="<c:out value='${form.imageUrl}'/>" placeholder="images/placeholder.svg"></div>
        <div class="col-12 form-check ms-2"><input class="form-check-input" type="checkbox" id="status" name="status" ${form.status ? 'checked' : ''}><label class="form-check-label" for="status">Visible in store</label></div>
        <div class="col-12"><button class="btn btn-primary" type="submit">Save</button> <a class="btn btn-link" href="${ctx}/admin/products">Cancel</a></div>
      </form>
    </div></div>
  </c:if>
  <div class="table-responsive card shadow-sm"><table class="table align-middle mb-0">
    <thead><tr><th>#</th><th>Name</th><th>Category</th><th>Price</th><th>Disc.</th><th>Stock</th><th>Status</th><th></th></tr></thead>
    <tbody>
    <c:forEach var="p" items="${products}">
      <tr>
        <td>${p.productId}</td><td><c:out value="${p.name}"/></td><td><c:out value="${p.categoryName}"/></td>
        <td><sp:price amount="${p.price}"/></td><td>${p.discount}%</td>
        <td class="${p.stock <= 10 ? 'text-danger fw-bold' : ''}">${p.stock}</td>
        <td><span class="badge text-bg-${p.status ? 'success' : 'secondary'}">${p.status ? 'ACTIVE' : 'HIDDEN'}</span></td>
        <td class="text-nowrap">
          <a class="btn btn-sm btn-outline-primary" href="${ctx}/admin/products?edit=${p.productId}">Edit</a>
          <c:if test="${p.status}">
            <form class="d-inline" action="${ctx}/admin/products" method="post" data-confirm="Remove this product from the store?">
              <input type="hidden" name="csrf" value="${sessionScope.csrfToken}"><input type="hidden" name="action" value="deactivate"><input type="hidden" name="productId" value="${p.productId}">
              <button class="btn btn-sm btn-outline-danger" type="submit">Remove</button>
            </form>
          </c:if>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table></div>
  </div>
</div>
<%@ include file="/fragments/footer.jsp" %>
