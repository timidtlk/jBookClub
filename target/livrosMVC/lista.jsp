<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.LivroDTO, java.util.ArrayList" %>
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
        <div id="fundo">
            <div>
                <section id="modal">
                    <article>
                        <img src="image/lista/livro.png">
                    </article>
                    <article>
                        <h1>Título do livro</h1>
                        <h2>Autor</h2>
                        <p>Mensagem do método</p>
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
            <section>
                <a href="index.html">
                    <img src="image/lista/sair.png" id="sair">
                </a>
            </section>
        </header>
            
        <script>
            function changeSelectValue(id) {
                var e = document.getElementById("select"+id);
                var text = e.options[e.selectedIndex].text;
                
                document.getElementById("calcular" + id).href = "Livro?metodo=" + text + "&id=" + id;
            }
        </script>
        
        <main>
        
            <%
                @SuppressWarnings("unchecked")
                ArrayList<LivroDTO> lista = (ArrayList<LivroDTO>) request.getAttribute("lista"); 
            
                for (LivroDTO livro : lista) {
                    out.println("<section>"
                                +"<article>"
                                        +"<img src=\"image/lista/livro.png\" class=\"capa\">"
                                +"</article>");
                    out.println("<article>");
                    out.println("<h1>"+ livro.getTitulo() +"</h1>");
                    out.println("<h2>"+ livro.getAutor() +"</h2>");
                    out.println("<select id=\"select"+ livro.getId() +"\" onchange=\"changeSelectValue("+ livro.getId() +")\">");
                    out.println("<option value=\"idade\">Obter idade do livro</option>");
                    out.println("<option value=\"media\">Média de leitura</option>");
                    out.println("<option value=\"porcento\">Porcentagem já lida</option>");
                    out.println("<option value=\"qtd\">Quantidade de línguas</option>");
                    out.println("</select>");
                    out.println("<input type=\"button\" value=\"OK\" id=\"selecionar_metodo\" onclick=\"modal()\">");
                    out.println("</article>");
                    out.println("<article id=\"editar_excluir\">");
                    out.println("<a href=\"Livro?operacao=Remover&id="+ livro.getId() +"\"><img src=\"image/lista/excluir.png\"></a>");
                    out.println("<a href=\"Livro?operacao=Editar&id="+ livro.getId() +"\"><img src=\"image/lista/editar.png\" class=\"operacoes\"></a>");
                    out.println("</article>");
                    out.println("</section>");
                }
            %>               
            
        </main>
    </body>

    <script>
        function modal(){
            let modal = document.querySelector("div#fundo")
            modal.style.display = "block"
        }

        function fecharModal(){
            let modal = document.querySelector("div#fundo")
            let fechar = document.querySelector("img#fechar")
            modal.style.display = "none"
        }
    </script>

</html>