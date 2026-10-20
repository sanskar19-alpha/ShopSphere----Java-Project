<%@ tag description="Star rating display" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="value" required="true" type="java.lang.Double" description="Average rating 0-5" %>
<%@ attribute name="count" required="false" type="java.lang.Integer" description="Number of reviews" %>
<%-- TAG FILE: usage  <ss:rating value="${p.avgRating}" count="${p.reviewCount}"/> --%>
<c:set var="rounded" value="${value + 0.5 - ((value + 0.5) % 1)}"/>
<span class="stars" title="${value} / 5">
    <c:forEach var="i" begin="1" end="5">${i <= rounded ? '&#9733;' : '&#9734;'}</c:forEach>
</span>
<c:if test="${not empty count}"><small class="text-muted">(${count})</small></c:if>
