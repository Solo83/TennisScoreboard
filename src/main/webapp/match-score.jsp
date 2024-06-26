<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.solo83.tennisscoreboard.dto.OngoingMatch" %>
<%@ page import="com.solo83.tennisscoreboard.service.OngoingMatchesService" %>
<%@ page import="java.util.UUID" %>
<%@ include file="/WEB-INF/header.jsp" %>

<%
    String player1name;
    String player2name;
    String uuid = request.getParameter("uuid");
    String player1points;
    String  player2points;
    Integer player1set;
    Integer player2set;
    Integer player1game;
    Integer player2game;
    Integer player1Id;
    Integer player2Id;
    boolean isAdvantage;
    boolean isTieBreak;
    String points = "POINTS";
    OngoingMatch currentMatch = OngoingMatchesService.getInstance().getMatch(UUID.fromString(request.getParameter("uuid")));
    isAdvantage = currentMatch.isAdvantage();
    isTieBreak = currentMatch.isTieBreak();
    player1name = currentMatch.getFirstPlayer().getName();
    player2name = currentMatch.getSecondPlayer().getName();
    player1set = currentMatch.getFirstPlayerScore().getSets();
    player2set = currentMatch.getSecondPlayerScore().getSets();
    player1game = currentMatch.getFirstPlayerScore().getGame();
    player2game = currentMatch.getSecondPlayerScore().getGame();
    player1points = String.valueOf(currentMatch.getFirstPlayerScore().getPoints());
    player2points = String.valueOf(currentMatch.getSecondPlayerScore().getPoints());
    player1Id = currentMatch.getFirstPlayer().getId();
    player2Id = currentMatch.getSecondPlayer().getId();
    if (isAdvantage) {points = "Advantage Started";};
    if (isTieBreak) {points = "TieBreak Started";}
    if (isAdvantage && player1points.equals("0")) {player1points = "40";} else if (isAdvantage && player1points.equals("1")) {player1points = "AD";};
    if (isAdvantage && player2points.equals("0")) {player2points = "40";} else if (isAdvantage && player2points.equals("1")) {player2points = "AD";};

%>

<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>
<html>
<body id="body">
    <div id="content">
        <table id="table"> Match Started
            <thead>
            <tr>
                <th></th>
                <th>P1:
                    <%= player1name
                    %>
                </th>
                <th></th>
                <th>P2:
                    <%= player2name
                    %>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>SETS</td>
                <td><%= player1set
                %>
                </td>
                <td>:</td>
                <td><%= player2set
                %>
                </td>
            </tr>
            <tr>
                <td>GAMES</td>
                <td><%= player1game
                %>
                </td>
                <td>:</td>
                <td><%= player2game
                %>
                </td>
            </tr>
            <tr>
                <td><%= points
                %></td>
                <td><%= player1points
                %>
                </td>
                <td>:</td>
                <td><%= player2points
                %>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <form action="match-score?uuid=<%=uuid%>" method="post">
                        <input name="playerId" type="hidden"
                               value=<%=player1Id%>>
                        <input type="submit" value="P1 Win Point">
                    </form>
                </td>
                <td></td>
                <td>
                    <form action="match-score?uuid=<%=uuid%>" method="post">
                        <input name="playerId" type="hidden"
                               value=<%=player2Id%>>
                        <input type="submit" value="P2 Win Point">
                    </form>
                </td>
            </tr>
            </tbody>
        </table>
        <c:if test="${not empty error}">
            <div id="error">
                    ${error}
            </div>
        </c:if>
</div>
</body>
</html>
