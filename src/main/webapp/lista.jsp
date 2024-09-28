<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Livro, java.util.ArrayList, model.Usuario" %>
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
        <%
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                response.sendRedirect("/index.jsp");
            }
        %>

        <div id="fundo">
            <div>
                <section id="modal">
                    <article>
                        <img src="image/lista/livro.png">
                    </article>
                    <article>
                        <h1 id="tituloModal">Título do livro</h1>
                        <h2 id="autorModal">Autor</h2>
                        <p id="mensagemModal">Mensagem do método</p>
                    </article>
                    <article>
                        <img src="image/lista/fechar.png" id="fechar" onclick="fecharModal()">
                    </article>
                </section>
            </div>
        </div>
        <header>
            <section>
                <img src="image/lista/livros.png" id="livros">
                <h1>jBookClub</h1>
            </section>
            <section id="menu_perfil">
                <article>
                    <h3>Olá, <%= usuario.login() %></h3>
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
        
            <%
                @SuppressWarnings("unchecked")
                ArrayList<Livro> lista = (ArrayList<Livro>) request.getAttribute("lista"); 

                if (lista == null) 
                    out.println("");
                else {
                    for (Livro livro : lista) {

                        int idade = livro.getIdadeLivro();
                        String estimativa = String.format("%d horas, %d minutos e %d segundos", livro.getEstimativaLeitura()[0], livro.getEstimativaLeitura()[1], livro.getEstimativaLeitura()[2]);
                        String progresso = String.format("%.2f", livro.getProgressoPcent());
                        int qtdLinguas = livro.getQtdLinguas();

                        out.println("<section>"
                                    +"<article>"
                                            +"<img src=\"image/lista/livro.png\" class=\"capa\">"
                                    +"</article>");
                        out.println("<article>");
                        out.println("<h1>"+ livro.getTitulo() +"</h1>");
                        out.println("<h2>"+ livro.getAutor() +"</h2>");
                        out.println("<select id=\"select"+ livro.getId() +"\">");
                        out.println("<option value=\"idade\">Obter idade do livro</option>");
                        out.println("<option value=\"media\">Média de leitura</option>");
                        out.println("<option value=\"porcento\">Porcentagem já lida</option>");
                        out.println("<option value=\"qtd\">Quantidade de línguas</option>");
                        out.println("</select>");
                        out.println("<input type=\"button\" value=\"OK\" class=\"selecionar_metodo\" onclick=\"modal('"+ livro.getId()+ "', '" + livro.getTitulo() +"', '" + livro.getAutor() + "', '" + idade +"', '"+ estimativa +"', '"+ progresso +"', '"+ qtdLinguas + "')\">");
                        out.println("</article>");
                        out.println("<article class=\"editar_excluir\">");
                        out.println("<a href=\"LivroController?operacao=Remover&id="+ livro.getId() +"\"><img src=\"image/lista/excluir.png\"></a>");
                        out.println("<a href=\"LivroController?operacao=Editar&id="+ livro.getId() +"\"><img src=\"image/lista/editar.png\" class=\"operacoes\"></a>");
                        out.println("</article>");
                        out.println("</section>");
                    }
                }
            %>               
            
        </main>
    </body>

    <script>
        function modal(id, titulo, autor, idade, estimativa, progresso, qtdLinguas){

            var e = document.getElementById("select" + id);
            var metodo = e.options[e.selectedIndex].value;

            document.getElementById("tituloModal").innerText = titulo;
            document.getElementById("autorModal").innerText = autor;

            switch (metodo) {
                case "idade":
                    document.getElementById("mensagemModal").innerText = "O livro tem " + idade + " anos";
                    break;
                case "media":
                    document.getElementById("mensagemModal").innerText = "Considerando que cada página levará, em média, um minuto, a estimativa do tempo de leitura é de " + estimativa;
                    break;
                case "porcento":
                    document.getElementById("mensagemModal").innerText = "Você já leu "+ progresso +"% do livro";
                    break;
                case "qtd":
                    document.getElementById("mensagemModal").innerText = "O livro está disponível em "+ qtdLinguas +" línguas";
                    break;
            }

            let modal = document.querySelector("div#fundo")
            modal.style.display = "block"
        }

        function fecharModal(){
            let modal = document.querySelector("div#fundo")
            let fechar = document.querySelector("img#fechar")
            modal.style.display = "none"
        }

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