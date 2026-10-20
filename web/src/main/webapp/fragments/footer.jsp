<%-- FRAGMENT: footer. Included statically at the bottom of every page (closes <main>, <body>, <html>). --%>
</main>
<footer class="mt-auto py-4">
    <div class="container d-flex flex-column flex-md-row justify-content-between align-items-center gap-2">
        <div>&copy; <fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy"/> <fmt:message key="app.name"/>. <fmt:message key="footer.rights"/></div>
        <div>
            <fmt:message key="footer.support"/>: <a href="mailto:${initParam.supportEmail}"><c:out value="${initParam.supportEmail}"/></a>
            &middot; <a href="${pageContext.request.contextPath}/demo/index.jsp">Syllabus demos</a>
        </div>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
