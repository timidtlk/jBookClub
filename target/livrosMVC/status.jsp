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
        <link rel="stylesheet" href="css/mensagem.css">
        <title>jBookClub</title>
    </head>

    <c:set var="operacao" value="${requestScope.operacao}"/>
    <c:choose>
        <c:when test="${requestScope.status}">
            <c:set var="mensagem" value="Livro ${operacao}do com sucesso!" />
        </c:when>
        
        <c:otherwise>
            <c:set var="mensagem" value="Erro! Não foi possível ${operacao}r o livro" />
        </c:otherwise>
    </c:choose>
    

    <body onload="${(requestScope.status) ? "imagens_cadastrado()" : "imagens_nao_cadastrado()"}" >
        <main>
            
            <section>
                <article>
                    <img src="">
                </article>
                <article style="${(requestScope.status) ? "color: #5a932b;" : "color: rgb(189, 55, 55);"}">
                    <h1>${mensagem}</h1>
                    <a href="infoLivros.html">
                        <input type="button" value="VOLTAR">
                    </a>
                </article>
            </section>
        </main>
    </body>
    <script>

        function imagens_cadastrado(){
            var fundo = [1,2,3,4][Math.floor(Math.random()*4)];
            var img = document.querySelector("img");
            img.src = "image/cadastrado/fundo" + fundo + ".png"
        }
        function imagens_nao_cadastrado(){
            var fundo = [1,2,3,4][Math.floor(Math.random()*4)];
            var img = document.querySelector("img");
            img.src = "image/nao_cadastrado/fundo" + fundo + ".png"
        }
    </script>
</html>