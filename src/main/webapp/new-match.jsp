<%@ include file="/WEB-INF/header.jsp" %>
<style><%@include file="/WEB-INF/css/style.css"%></style>
<html>

<body id="body">
    <div id="content">Start new match
        <form action="new-match" method="post">

        <p><input type="text" id="player1" name="player1name">
            <label for="player1">Player 1 Name</label></p>

        <p><input type="text" id="player2" name="player2name">
            <label for="player2">Player 2 Name</label></p>

            <input type="submit" value="Start">

            <div id="error">
                <%
                    String error;
                    if(request.getAttribute("error")!=null)
                    {
                        error = (String)request.getAttribute("error");
                    }else
                    {
                        error = "";
                    }
                %>
                <%= error
                %>
            </div>
        </form>
    </div>
</body>

</html>