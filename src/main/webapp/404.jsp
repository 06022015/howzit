<%@ include file="pages/common/taglib.jsp" %>
<page:applyDecorator name="default">
    <title><fmt:message key="404.title"/></title>
    <content tag="heading"><fmt:message key="404.title"/></content>
    <p>
        <fmt:message key="404.message">
            <fmt:param><c:url value="/auth/home.html"/></fmt:param>
        </fmt:message>
    </p>
</page:applyDecorator>