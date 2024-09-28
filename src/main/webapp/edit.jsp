<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Livro, java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="image/lista/livros.png">
        <link rel="stylesheet" href="css/cadastrar.css">
        <title>jBookClub - Edite seu livro</title>
    </head>
    <body>

        <%
    		Livro livro = (Livro) request.getAttribute("livro");
	    	LocalDate localDate = livro.getAnoLancamento();
	    	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
	    	String ano = localDate.format(formatter);
    	%>

        <main>
            <section>
                <article id="titulo_pag">
                    <input type="text" id="id" name="id" readonly placeholder="ID" value="<%= livro.getId() %>" style="width: 2em; height: 2em; margin-left: 1em;">
                    <h1>Edite seu livro</h1>
                </article>
                <article id="formulario">
                    <form action="LivroController" method="post">

                        <input type="text" id="id" name="id" readonly placeholder="ID" value="<%= livro.getId() %>" style="display: none">
                        <p>
                            <label for="titulo"></label>
                            <input type="text" id="titulo" name="titulo" placeholder="Título do livro" value="<%= livro.getTitulo() %>" required>
                        </p>
                        <p>
                            <label for="autor"></label>
                            <input type="text" id="autor" name="autor" placeholder="Autor" value="<%= livro.getAutor() %>" required>
                        </p>
                        <p>
                            <label for="genero"></label>
                            <input type="text" id="genero" name="genero" placeholder="Gênero" value="<%= livro.getGenero() %>" required>
                        </p>
                        <p>
                            <label for="editora"></label>
                            <input type="text" id="editora" name="editora" placeholder="Editora" value="<%= livro.getEditora() %>" required>
                        </p>
                        <p>
                            <label for="ano"></label>
                            <input type="date" id="ano" name="anoLancamento" value="<%= ano %>" required>
                        </p>
                        <p>
                            <label for="qPag"></label>
                            <input type="number" id="qPag" name="qtdPgsTotal" placeholder="Quantidade de páginas" min="0" value="<%= livro.getQtdPaginasTotal() %>" required>
                        </p>
                        <p>
                            <label for="qPagLidas"></label>
                            <input type="number" id="qPagLidas" name="qtdPgsLidas" placeholder="Quantidade de páginas lidas" min="0" value="<%= livro.getQtdPaginasLidas() %>" required>
                        </p>
                        <p>
                            <label for="linguas"></label>
                            <input type="text" id="linguas" name="linguas" placeholder="Línguas disponíveis" value="<%= livro.getLinguas() %>" required>
                        </p>
                        <p>
                            <label for="avaliacao"></label>
                            <input type="text" id="avaliacao" name="avaliacao" placeholder="Avaliação" value="<%= livro.getAvaliacao() %>" required>
                        </p>
                        <p id="botoes">
                            <a href="Livro?operacao=Listar">
                                <input type="button" value="CANCELAR" id="botao">
                            </a>
                            <input type="submit" value="ATUALIZAR" name="operacao" id="botao">
                        </p>
                    </form>
                </article>
            </section>
        </main>
        <script>
            var ano = document.getElementById("ano")
            ano.max = new Date().toISOString().split('T')[0]
        </script>
    </body>
</html>