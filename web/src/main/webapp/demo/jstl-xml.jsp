<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<c:set var="pageTitle" value="JSTL XML"/>
<%@ include file="/fragments/header.jsp" %>
<h3>JSTL XML: product feed</h3>
<%-- The XML document is given as the body of x:parse (a product feed, like one received from a supplier). --%>
<x:parse var="feed"><feed>
  <product id="1"><name>Wireless Earbuds Pro</name><price>2999</price><category>Electronics</category></product>
  <product id="8"><name>Advanced Java Programming</name><price>549</price><category>Books</category></product>
  <product id="14"><name>Yoga Mat 6mm</name><price>699</price><category>Sports</category></product>
  <product id="5"><name>Cotton Casual T-Shirt</name><price>599</price><category>Fashion</category></product>
</feed></x:parse>
<p>Feed contains <b><x:out select="count($feed/feed/product)"/></b> products.</p>
<table class="table table-striped bg-white"><thead><tr><th>ID</th><th>Name</th><th>Category</th><th>Price</th></tr></thead><tbody>
<x:forEach select="$feed/feed/product" var="p">
  <tr><td><x:out select="$p/@id"/></td><td><x:out select="$p/name"/></td><td><x:out select="$p/category"/></td>
    <td><x:choose>
      <x:when select="$p/price > 1000"><span class="badge text-bg-warning">premium</span> </x:when>
      <x:otherwise><span class="badge text-bg-light">budget</span> </x:otherwise></x:choose>
      <x:out select="$p/price"/></td></tr>
</x:forEach></tbody></table>
<%@ include file="/fragments/footer.jsp" %>
