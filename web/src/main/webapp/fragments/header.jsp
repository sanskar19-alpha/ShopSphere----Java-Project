<%-- FRAGMENT: header. Included STATICALLY with <%@ include file="/fragments/header.jsp" %> at the top of every page.
     The including page must declare the c, fmt and fn taglibs first and may set ${pageTitle}. --%>
<fmt:setLocale value="${sessionScope.lang == 'hi' ? 'hi_IN' : 'en_IN'}" scope="request"/>
<fmt:setBundle basename="messages" scope="request"/>
<%-- path (relative to the context) of the current page incl. query string - used for "back" links --%>
<c:set var="selfUri" value="${empty requestScope['jakarta.servlet.forward.request_uri'] ? pageContext.request.requestURI : requestScope['jakarta.servlet.forward.request_uri']}" scope="request"/>
<c:set var="selfQuery" value="${requestScope['jakarta.servlet.forward.query_string']}" scope="request"/>
<c:set var="selfPath" value="${fn:substringAfter(selfUri, pageContext.request.contextPath)}${empty selfQuery ? '' : '?'}${selfQuery}" scope="request"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${empty pageTitle ? 'Home' : pageTitle}"/> | <fmt:message key="app.name"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/fragments/navbar.jsp"/>
<main class="container py-4">
<c:if test="${not empty sessionScope.flashMsg}">
    <div class="alert alert-${empty sessionScope.flashType ? 'info' : sessionScope.flashType} alert-dismissible fade show ${sessionScope.flashType == 'success' ? 'auto-dismiss' : ''}" role="alert">
        <c:out value="${sessionScope.flashMsg}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <c:remove var="flashMsg" scope="session"/>
    <c:remove var="flashType" scope="session"/>
</c:if>
