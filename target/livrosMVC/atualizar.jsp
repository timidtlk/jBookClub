<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.LivroDTO, java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/x-icon" href="livros.png">
        <link rel="stylesheet" href="css/index.css">
        <title>Atualizar o livro</title>
    </head>
    <body>
    
    	<%
    		LivroDTO livro = (LivroDTO) request.getAttribute("livro");
	    	LocalDate localDate = livro.getAnoLancamento();
	    	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
	    	String ano = localDate.format(formatter);
    	%>
    
        <main>
            <section id="titulo_pag">
                <h1>Atualize um livro da sua lista</h1>
            </section>
            <section id="formulario">
                <form action="Livro" method="post">
                	<p>
                        <label for="id"></label>
                        <input type="number" id="id" name="id" value="<%= livro.getId() %>" style="height: 15px; width: 30px" readonly>
                    </p>
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
                        <input type="date" id="ano" name="anoLancamento" placeholder="Ano de lançamento" value="<%= livro.getAnoLancamento() %>" required>
                    </p>
                    <p>
                        <label for="qtdPgsTotal"></label>
                        <input type="number" id="qtdPgsTotal" name="qtdPgsTotal" placeholder="Quantidade de páginas" value="<%= (int) livro.getQtdPaginasTotal() %>" min="0" required>
                    </p>
                    <p>
                        <label for="qtdPgsLidas"></label>
                        <input type="number" id="qtdPgsLidas" name="qtdPgsLidas" placeholder="Quantidade de páginas lidas" value="<%= (int) livro.getQtdPaginasLidas() %>" min="0" required>
                    </p>
                    <p>
                        <label for="linguas"></label>
                        <input type="text" id="linguas" name="linguas" placeholder="Línguas disponíveis" value="<%= livro.getLinguas() %>" required>
                    </p>
                    <p>
                        <label for="avaliacao"></label>
                        <input type="text" id="avaliacao" name="avaliacao" placeholder="Avaliação" value="<%= livro.getAvaliacao() %>" required>
                    </p>                  
                    <p>
                        <input type="submit" value="ATUALIZAR" class="botao" name="operacao">
                    </p>
                </form>
            </section>
        </main>
    </body>
</html>