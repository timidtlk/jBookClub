<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>jBookClub - Cadastrar</title>
    </head>
    <body>
        <h1>Cadastrar Usuário</h1>
        <p id="mensagem" style="color: red"><%= (request.getAttribute("erro") != null) ? request.getAttribute("erro") : "" %></p>
        <form action="UsuarioController" method="post">
            <div>
                <label for="nomeID">Nome: </label>
                <input type="text" name="nome" id="nomeID" required>
            </div>
            
            <div>
                <label for="loginID">Login: </label>
                <input type="text" name="login" id="loginID" required>
            </div>

            <div>
                <label for="emailID">E-mail: </label>
                <input type="email" name="email" id="emailID" required>
            </div>

            <div>
                <label for="cpfID">CPF: </label>
                <input maxlength="14" oninput="mascaraCPF(this)" type="text" name="cpf" id="cpfID" required>
            </div>

            <div>
                <label for="telefoneID">Telefone: </label>
                <input maxlength="15" onkeyup="handlePhone(event)" type="tel" name="telefone" id="telefoneID" required>
            </div>

            <div>
                <label for="passwordID">Senha: </label>
                <input type="password" name="password" id="passwordID" oninput="confirmarSenha(this)" required> 
            </div>

            <div>
                <label for="confirmID">Confirme sua senha: </label>
                <input type="password" name="confirm" id="confirmID" oninput="confirmarSenha(this)" required>
            </div>

            <div>
                <input type="submit" value="Cadastrar" name="operacao" id="submitID">
            </div>
        </form>

        <a href="index.html">Já possui uma conta na nossa aplicação?</a>
    </body>

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

                campoConfirma.style.borderColor = "black";
                campoSenha.style.borderColor = "black";
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
</html>