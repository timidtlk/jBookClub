<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="entity.Livro, entity.Usuario" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="image/lista/livros.png">
    <link rel="stylesheet" href="css/index.css">
    <title>jBookClub</title>
</head>
<body>
    <c:set var="usuario" value="${sessionScope.usuario}"/>
    <c:if test="${empty usuario}" >
        <meta http-equiv="refresh" content="0; url=index.html">
    </c:if>
    <main>
        <div id="ultimoLivro">

            <c:set var="titulovar" value="titulo-${usuario.login}"/>
            <c:set var="autorvar" value="autor-${usuario.login}"/>
            <c:set var="avaliacaovar" value="avaliacao-${usuario.login}"/>

            <c:set var="titulo" value="Nenhum"/>
            <c:set var="autor" value="Nenhum"/>
            <c:set var="avaliacao" value="Nenhum"/>
            <c:forEach var="cookieVal" items="${cookie}">
                <c:choose>
                    <c:when test="${cookieVal.key == titulovar}">
                        <c:set var="titulo" value="${cookieVal.value.value}"/>
                    </c:when>

                    <c:when test="${cookieVal.key == autorvar}">
                        <c:set var="autor" value="${cookieVal.value.value}"/>
                    </c:when>

                    <c:when test="${cookieVal.key == avaliacaovar}">
                        <c:set var="avaliacao" value="${cookieVal.value.value}"/>
                    </c:when>
                </c:choose>
            </c:forEach>

            <h3>Último livro cadastrado: ${titulo}</h3>
            <p>Autor: ${autor}</p>
            <p>Avaliação: ${avaliacao}</p>
        </div>
        <section id="titulo">
            <h1>jBookClub</h1>
            <h2>Um lugar para organizar sua lista de leitura</h2>
        </section>
        <section id="botoes">
            <a href="cadastrar.html">
                <h3>CADASTRAR</h3>
            </a>
            <a href="LivroController?operacao=Listar">
                <h3>LISTAR</h3>
            </a>
        </section>
    </main>
    
</body>
</html>