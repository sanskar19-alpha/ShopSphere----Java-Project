<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>
<c:set var="pageTitle" value="JSTL SQL"/>
<%@ include file="/fragments/header.jsp" %>
<h3>JSTL SQL (syllabus demonstration)</h3>
<div class="alert alert-warning">SQL inside JSP breaks the N-tier design. ShopSphere itself uses JSP &rarr; Servlet &rarr; Service &rarr; DAO &rarr; JDBC. This page exists only to show <code>sql:setDataSource</code> and <code>sql:query</code>.</div>
<c:catch var="dbError">
  <sql:setDataSource var="ds" dataSource="jdbc/ShopSphereDB"/>
  <sql:query var="rs" dataSource="${ds}" maxRows="5">
    SELECT product_id, name, stock FROM products WHERE stock <= ? ORDER BY stock
    <sql:param value="${20}"/>
  </sql:query>
  <table class="table table-striped bg-white"><thead><tr><th>ID</th><th>Product</th><th>Stock</th></tr></thead><tbody>
  <c:forEach var="row" items="${rs.rows}"><tr><td>${row.product_id}</td><td><c:out value="${row.name}"/></td><td>${row.stock}</td></tr></c:forEach>
  </tbody></table>
  <p>Rows returned: ${rs.rowCount}</p>
</c:catch>
<c:if test="${not empty dbError}"><div class="alert alert-danger">Could not query the database through JNDI: <c:out value="${dbError.message}"/><br>Check META-INF/context.xml and copy the MySQL driver into Tomcat's lib folder.</div></c:if>
<%@ include file="/fragments/footer.jsp" %>
