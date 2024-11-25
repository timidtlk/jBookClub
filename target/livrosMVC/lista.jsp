<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@ page import="entity.Livro, java.util.ArrayList, entity.Usuario" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="image/lista/livros.png">
        <link rel="stylesheet" href="css/lista.css">
        <title>jBookClub - Lista de livros</title>
    </head>
    <body>
        <c:set var="usuario" value="${sessionScope.usuario}"/>
        <c:if test="${empty usuario}" >
            <meta http-equiv="refresh" content="0; url=index.html">
        </c:if>

        <header>
            <section>
                <img src="image/lista/livros.png" id="livros">
                <h1>jBookClub</h1>
            </section>
            <section id="menu_perfil">
                <article>
                    <h3>Olá, ${usuario.login}</h3>
                    <img src="image/lista/perfil.png" id="img_perfil" onclick="menu()">
                </article>
                <article id="menu">
                    <ul>
                        <li>
                            <a href="cadastrar.html">
                                <span class="texto_menu">NOVO LIVRO</span>
                                <span class="img_menu"><img src="image/lista/adicionar_livros.png"></span>
                            </a>
                        </li>
                        <hr>
                        <li>
                            <a href="UsuarioController?operacao=Sair">
                                <span class="texto_menu">SAIR</span>
                                <span class="img_menu"><img src="image/lista/sair.png"></span>
                            </a>
                        </li>
                        <hr>
                        <li id="remover">
                            <a href="UsuarioController?operacao=Excluir">
                                <span class="texto_menu">REMOVER CONTA</span>
                                <span class="img_menu"><img src="image/lista/excluir-usuario.png"></span>
                            </a>
                        </li>
                    </ul>
                </article>
            </section>
        </header>
        
        <main>
        
            <c:set var="lista" value="${requestScope.lista}"/>

            <c:choose>
                <c:when test="${empty lista}">
                    <p></p>
                </c:when>
                
                <c:otherwise>
                    <c:forEach items="${lista}" var="livro">

                        <c:set var="idade" value="${livro.getIdadeLivro()}"/>
                        <c:set var="estimativa" value="${livro.getEstimativaLeitura()}"/>
                        <c:set var="progresso" value="${livro.getProgressoPcent()}" />
                        <c:set var="linguas" value="${livro.getQtdLinguas()}" />

                        <section>
                            <article>
                                <img src="image/lista/livro.png" class="capa">
                            </article>
                            <article>
                                <h1>${livro.titulo}</h1>
                                <h2>${livro.autor}</h2>
                                <form action="LivroController" method="get">
                                    <input type="text" name="id" value="${livro.id}" hidden>
                                    <select name="select">
                                        <option value="idade">Obter idade do livro</option>
                                        <option value="media">Média de leitura</option>
                                        <option value="porcento">Porcentagem já lida</option>
                                        <option value="qtd">Quantidade de línguas</option>
                                    </select>

                                    <input type="text" name="operacao" value="Metodo" hidden>

                                    <input type="submit" value="OK" class="selecionar_metodo">
                                </form>
                            </article>
                            <article class="editar_excluir">
                                <a href="LivroController?operacao=Remover&id=${livro.id}"><img src="image/lista/excluir.png"></a>
                                <a href="LivroController?operacao=Editar&id=${livro.id}"><img src="image/lista/editar.png" class="operacoes"></a>
                            </article>
                        </section>
                    </c:forEach>
                </c:otherwise>
            </c:choose>            
        </main>
    </body>

    <script>
        function menu(){
            let menu = document.getElementById('menu')

            if(menu.style.visibility == "visible"){
                menu.style.visibility = "hidden"
                menu.style.opacity = "0"
            } else{
                menu.style.visibility = "visible"
                menu.style.opacity = "1"
            }
        }
    </script>

</html>