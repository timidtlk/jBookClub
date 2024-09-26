<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>jBookClub - Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <p id="mensagem" style="color: red"><%= (request.getAttribute("erro") != null) ? request.getAttribute("erro") : "" %></p>
        <form action="UsuarioController" method="post">
            <label for="loginID">Login: </label>
            <input type="text" name="login" id="loginID">

            <label for="passwordID">Senha: </label>
            <input type="password" name="password" id="passwordID">

            <input type="submit" value="Entrar" name="operacao">
        </form>

        <a href="cadastrarUsuario.jsp">Não possui uma conta na nossa aplicação?</a>
    </body>
</html>