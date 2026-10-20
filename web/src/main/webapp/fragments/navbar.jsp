<%-- FRAGMENT: navbar. Included DYNAMICALLY (<jsp:include>) - it is its own JSP, so it declares its own taglibs. --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-ss sticky-top shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="${ctx}/home">&#128717; <fmt:message key="app.name"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav" aria-controls="mainNav" aria-expanded="false" aria-label="Menu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-3">
                <li class="nav-item"><a class="nav-link" href="${ctx}/home"><fmt:message key="nav.home"/></a></li>
                <li class="nav-item"><a class="nav-link" href="${ctx}/products"><fmt:message key="nav.products"/></a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item"><a class="nav-link" href="${ctx}/wishlist"><fmt:message key="nav.wishlist"/></a></li>
                    <li class="nav-item"><a class="nav-link" href="${ctx}/orders"><fmt:message key="nav.orders"/></a></li>
                </c:if>
            </ul>
            <form class="d-flex flex-grow-1 me-lg-3 my-2 my-lg-0" action="${ctx}/products" method="get" role="search">
                <input class="form-control me-2" type="search" name="q" value="<c:out value='${param.q}'/>" maxlength="60"
                       placeholder="<fmt:message key='nav.search.placeholder'/>" aria-label="Search">
                <button class="btn btn-warning" type="submit"><fmt:message key="nav.search.button"/></button>
            </form>
            <ul class="navbar-nav align-items-lg-center">
                <li class="nav-item">
                    <c:url var="langUrl" value="/lang">
                        <c:param name="to" value="${sessionScope.lang == 'hi' ? 'en' : 'hi'}"/>
                        <c:param name="back" value="${selfPath}"/>
                    </c:url>
                    <a class="nav-link" href="${langUrl}"><fmt:message key="lang.switch"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link position-relative" href="${ctx}/cart">&#128722; <fmt:message key="nav.cart"/>
                        <c:if test="${sessionScope.cartCount > 0}">
                            <span class="badge rounded-pill bg-warning text-dark">${sessionScope.cartCount}</span>
                        </c:if>
                    </a>
                </li>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                &#128100; <c:out value="${sessionScope.user.name}"/>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="${ctx}/profile"><fmt:message key="nav.profile"/></a></li>
                                <li><a class="dropdown-item" href="${ctx}/orders"><fmt:message key="nav.orders"/></a></li>
                                <c:if test="${sessionScope.role == 'ADMIN'}">
                                    <li><a class="dropdown-item" href="${ctx}/admin/dashboard"><fmt:message key="nav.admin"/></a></li>
                                </c:if>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <form action="${ctx}/logout" method="post" class="px-3">
                                        <input type="hidden" name="csrf" value="${sessionScope.csrfToken}">
                                        <button type="submit" class="btn btn-link p-0 text-decoration-none"><fmt:message key="nav.logout"/></button>
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item"><a class="nav-link" href="${ctx}/login"><fmt:message key="nav.login"/></a></li>
                        <li class="nav-item"><a class="btn btn-outline-light btn-sm ms-lg-2" href="${ctx}/register"><fmt:message key="nav.register"/></a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
