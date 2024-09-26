<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="../image/lista/livros.png">
    <link rel="stylesheet" href="css/login.css">
    <title>jBookClub - Login</title>
</head>
<body>
    <main>
        <section>
            <article>
                <a href="../index.html"><img src="../image/lista/livros.png"></a>
            </article>
            <article>
                <p id="mensagem" style="color: red"><%= (request.getAttribute("erro") != null) ? request.getAttribute("erro") : "" %></p>
                <form action="UsuarioController" method="post">
                    <label for="username"></label>
                    <input type="text" id="username" name="login" placeholder="Nome de usuário" required>
                    <label for="senha"></label>
                    <input type="password" id="senha" name="password" placeholder="Senha" required>
                    <input type="submit" value="ENTRAR" id="botao">
                </form>
                <p>Ainda não possui uma conta? <a href="cadastro.html">Cadastre-se</a></p>
            </article>
        </section>
    </main>
</body>
</html>