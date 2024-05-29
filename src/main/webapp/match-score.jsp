<%@ page import="com.solo83.tennisscoreboard.dto.MatchScoreModel" %>
<%@ page import="com.solo83.tennisscoreboard.service.OngoingMatchesService" %>
<%@ page import="java.util.UUID" %>
<%@ include file="/WEB-INF/header.jsp" %>

<%
    String player1name;
    String player2name;
    String uuid = request.getParameter("uuid");
    Integer player1score;
    Integer player2score;
    Integer player1Id;
    Integer player2Id;
    MatchScoreModel currentMatch = OngoingMatchesService.getInstance().getMatch(UUID.fromString(request.getParameter("uuid")));
    player1name = currentMatch.getMatch().getFirstPlayer().getName();
    player2name = currentMatch.getMatch().getSecondPlayer().getName();
    player1score = currentMatch.getFirstPlayerScore().getPoints();
    player2score = currentMatch.getSecondPlayerScore().getPoints();
    player1Id = currentMatch.getMatch().getFirstPlayer().getId();
    player2Id = currentMatch.getMatch().getSecondPlayer().getId();
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
                <th>
                    <%= player1name
                    %>
                </th>
                <th></th>
                <th>
                    <%= player2name
                    %>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%= player1score
                %>
                </td>
                <td>:</td>
                <td><%= player2score
                %>
                </td>
            </tr>
            <tr>
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
</div>
</body>
</html>
