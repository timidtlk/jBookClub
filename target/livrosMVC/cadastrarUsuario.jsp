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
        <link rel="stylesheet" href="css/cadastro.css">
        <title>jBookClub - Cadastro</title>
    </head>
    <body>
        <main>
            <section>
                <article>
                    <a href="index.html"><img src="image/lista/livros.png"></a>
                </article>
                <article>
                    <p id="mensagem" style="color: red">${(not empty requestScope.erro) ? requestScope.erro : ""}</p>
                    <form action="UsuarioController" method="post">
                        <p>
                            <label for="nome"></label>
                            <input type="text" id="nome" name="nome" placeholder="Nome" required>
                        </p>
                        <p>
                            <label for="username"></label>
                            <input type="text" id="username" name="login" placeholder="Nome de usuário" required>
                        </p>
                        <p>
                            <label for="senha"></label>
                            <input type="password" id="passwordID" oninput="confirmarSenha(this)" name="password" placeholder="Senha" required>
                        </p>
                        <p>
                            <label for="senhaConfirma"></label>
                            <input type="password" id="confirmID" oninput="confirmarSenha(this)" name="confirm" placeholder="Confirme sua senha" required>
                        </p>
                        <p>
                            <label for="cpf"></label>
                            <input type="text" maxlength="14" oninput="mascaraCPF(this)" id="cpf" name="cpf" placeholder="CPF: xxx.xxx.xxx-xx" required>
                        </p>
                        <p>
                            <label for="email"></label>
                            <input type="email" id="email" name="email" placeholder="E-mail" required>
                        </p>
                        <p>
                            <label for="telefone"></label>
                            <input type="tel" id="telefone" maxlength="15" onkeyup="handlePhone(event)" name="telefone" placeholder="Telefone: (xx)xxxxx-xxxx" required>
                        </p>
                        <p>
                            <input type="submit" value="CADASTRAR" name="operacao" id="submitID">
                        </p>
                    </form>
                </article>
            </section>
        </main>

        <script>

            function confirmarSenha(e) {
                var campoSenha = document.getElementById("passwordID");
                var campoConfirma = document.getElementById("confirmID");
                var botao = document.getElementById("submitID");
                var mensagem = document.getElementById("mensagem");

                if (campoSenha.value != campoConfirma.value) {
                    if (!botao.hasAttribute("disabled"))
                        botao.setAttribute("disabled", "disabled");

                    campoConfirma.style.borderColor = "red";
                    campoSenha.style.borderColor = "red";
                    mensagem.innerText = "As senhas não coincidem!";
                } else {
                    if (botao.hasAttribute("disabled"))
                        botao.removeAttribute("disabled");

                    campoConfirma.style.borderColor = "#af6041";
                    campoSenha.style.borderColor = "#af6041";
                    mensagem.innerText = "";
                }
            }

            function mascaraCPF(cpf){
                const elementoAlvo = cpf
                const cpfAtual = cpf.value   

                if(isNaN(cpfAtual[cpfAtual.length-1])){ // impede entrar outro caractere que não seja número
                    cpf.value = cpfAtual.substring(0, cpfAtual.length-1);
                    return;
                }

                let cpfAtualizado;

                cpfAtualizado = cpfAtual.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, 
                function( regex, argumento1, argumento2, argumento3, argumento4 ) {
                        return argumento1 + '.' + argumento2 + '.' + argumento3 + '-' + argumento4;
                })  
                elementoAlvo.value = cpfAtualizado; 
            }

            const handlePhone = (event) => {
                let input = event.target
                input.value = phoneMask(input.value)
            }

            const phoneMask = (value) => {
                if (!value) return ""
                value = value.replace(/\D/g,'')
                value = value.replace(/(\d{2})(\d)/,"($1) $2")
                value = value.replace(/(\d)(\d{4})$/,"$1-$2")
                return value
            }
        </script>
    </body>
</html>