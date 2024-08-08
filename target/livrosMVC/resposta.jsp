<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html lang="pt-br">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="icon" type="image/x-icon" href="./img/livro.png">
            <link rel="stylesheet" href="./css/resultado.css">
            <title>Lista</title>
        </head>
        <body>
            <header>
                <section>
                    <img src="./img/livros.png" id="livros">
                    <h1>Lista de livros</h1>
                </section>
                <section>
                    <h3>Página inicial</h3>
                    <a href="index.html">
                        <img src="./img/adicionar.png" id="adicionar">
                    </a>
                </section>
            </header>
            <main>
                <section>
                    <article>
                        <img src="./img/livro.png">
                    </article>
                    <article>
                        <h1><%= request.getAttribute("titulo") %></h1>
                        <h2><%= request.getAttribute("autor") %></h2>
                        <p>Este livro possui <%= request.getAttribute("idade") %> anos</p>
                        <%
                            
                            int[] array = (int[]) request.getAttribute("estimativa");
                        
                        %>
                        <p>Considerando que a média de leitura de uma página é 1 minuto, o seu livro levará <%= array[0] %> horas, <%= array[1] %> minutos e <%= array[2] %> segundos para ser completo</p>
                        <p>Você já leu <%= request.getAttribute("porcentagem") %>% do livro</p>
                        <p>Este livro está disponível em <%= request.getAttribute("linguas") %> línguas (ou mais)</p>
                    </article>
                </section>
            </main>
        </body>
    </html>