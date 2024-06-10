<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/header.jsp" %>
<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>
<html>
<body id="body">
<div id="content">

    <table id="finish_table"> All matches
        <div>
            <form action="matches">
                <input type="text" placeholder="input Player Name..." name="filter_by_player_name">
                <button id="redefined_button" type="submit">Find Matches</button>
            </form>
        </div>

        <thead>
        <tr>
            <th>ID</th>
            <th>P1 Name</th>
            <th>P2 Name</th>
            <th>Winner Name</th>
        </tr>
        </thead>

        <c:forEach var="match" items="${matchesList}">
            <tr>
                <td>${match.getId()}</td>
                <td>${match.getFirstPlayer().getName()}</td>
                <td>${match.getSecondPlayer().getName()}</td>
                <td>${match.getWinner().getName()}</td>
            </tr>
        </c:forEach>
    </table>

    <table id="finish_table">
        <c:if test="${noOfPages>1}">
            <tr>
                <td id="redefined_a">Page:</td>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage == i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="matches?filter_by_player_name=${playerName}&page=${i}">${i}</a></td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </tr>
        </c:if>
    </table>


    <c:if test="${currentPage != 1}">
        <td><a href="matches?filter_by_player_name=${playerName}&page=${currentPage - 1}">Previous</a></td>
    </c:if>


    <c:if test="${currentPage lt noOfPages}">
        <td><a href="matches?filter_by_player_name=${playerName}&page=${currentPage + 1}">Next</a></td>
    </c:if>

    <c:if test="${not empty error}">
        <div id="error">
                ${error}
        </div>
    </c:if>

</div>
</body>
</html>
