<%@ page import="com.solo83.tennisscoreboard.dto.OngoingMatch" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="/WEB-INF/header.jsp" %>

<%
    OngoingMatch currentMatch = (OngoingMatch) request.getAttribute("currentMatch");
    String player1name = currentMatch.getFirstPlayer().getName();
    String player2name = currentMatch.getSecondPlayer().getName();
    String winnerName = currentMatch.getWinner().getName();

    ArrayList<Integer> p1scores = currentMatch.getFirstPlayerScore().getGameScores();
    ArrayList<Integer> p2scores = currentMatch.getSecondPlayerScore().getGameScores();
%>

<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>
<html>
<body id="body">
<div id="content">
    <table id="finish_table"> Match finished
        <thead>
        <tr>
            <th>SET</th>
            <%for (int i = 0; i < p1scores.size(); i++) {%>
            <th><%=i + 1%>
            </th>
            <%}%>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><%= player1name%>
                    <%for(int i = 0; i<p1scores.size(); i++){%>
            <td><%=p1scores.get(i) %>
            </td>
            <%}%>
        </tr>
        <tr>
            <td><%= player2name%>
                    <%for(int i = 0; i<p2scores.size(); i++){%>
            <td><%=p2scores.get(i) %>
            </td>
            <%}%>
        </tr>
        </tbody>
    </table>

    <p>
        WINNER IS - <%= winnerName%>
    </p>

</div>
</body>
</html>
