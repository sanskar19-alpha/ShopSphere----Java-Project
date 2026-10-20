<%-- FRAGMENT: admin sidebar. Included dynamically: <jsp:include page="/fragments/sidebar.jsp"><jsp:param name="active" value="products"/></jsp:include> --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="list-group admin-nav shadow-sm mb-4">
    <a class="list-group-item list-group-item-action ${param.active == 'dashboard' ? 'active' : ''}" href="${ctx}/admin/dashboard">&#128202; Dashboard</a>
    <a class="list-group-item list-group-item-action ${param.active == 'products' ? 'active' : ''}" href="${ctx}/admin/products">&#128230; Products</a>
    <a class="list-group-item list-group-item-action ${param.active == 'categories' ? 'active' : ''}" href="${ctx}/admin/categories">&#128193; Categories</a>
    <a class="list-group-item list-group-item-action ${param.active == 'inventory' ? 'active' : ''}" href="${ctx}/admin/inventory">&#128207; Inventory</a>
    <a class="list-group-item list-group-item-action ${param.active == 'orders' ? 'active' : ''}" href="${ctx}/admin/orders">&#128668; Orders</a>
    <a class="list-group-item list-group-item-action ${param.active == 'users' ? 'active' : ''}" href="${ctx}/admin/users">&#128101; Customers</a>
    <a class="list-group-item list-group-item-action ${param.active == 'coupons' ? 'active' : ''}" href="${ctx}/admin/coupons">&#127903; Coupons</a>
</div>
