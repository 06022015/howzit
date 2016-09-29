<%@ include file="pages/common/taglib.jsp" %>
<page:applyDecorator name="default">
    <title><fmt:message key="400.title"/></title>
    <content tag="heading"><fmt:message key="400.title"/></content>
    <p>
        <fmt:message key="400.message">
            <fmt:param><c:url value="/"/></fmt:param>
        </fmt:message>
    </p>
</page:applyDecorator>