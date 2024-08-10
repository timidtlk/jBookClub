Feito por Gustavo Gil e Emilly Caxias

Observação: Caso queira rodar no Eclipse o programa, substitua o arquivo context.xml pelo context_backup.xml 
(a diferença é que o eclipse deixa a primeira letra da tag context como minúscula, quando, na verdade, é maiúscula nas novas versões)

o nome de usuário é "livros" e a senha é "evertonmascote2007", o arquivo db.sql possui o código para a criação da tabela mysql

caso apresente problemas, crie um novo projeto no eclipse e mova a pasta src nele, adicione a dependência do mysql-connector no pom.xml e tudo funcionará corretamente (também remova as dependências do jakarta do pom.xml quando for executar)