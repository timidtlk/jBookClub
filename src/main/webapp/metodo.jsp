<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="image/lista/livros.png">
        <link rel="stylesheet" href="css/metodo.css">
        <title>jBookClub</title>
    </head>   

    <body>
        <main>
            <section>
                <article>
                    <img src="image/lista/livro.png">
                </article>
                <article>
                    <article>
                        <h1 id="tituloModal">${requestScope.titulo}</h1>
                        <h2 id="autorModal">${requestScope.autor}</h2>
                        <p id="mensagemModal">${requestScope.metodo}</p>
                    </article>
                    <a href="LivroController?operacao=Listar">
                        <input type="button" value="VOLTAR">
                    </a>
                </article>
            </section>
        </main>
    </body>
</html>