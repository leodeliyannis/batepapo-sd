# batepapo-sd

### Sobre:
ApiBanco desenvolvida em NodeJS, é responsável por fazer todas as operações no banco(MongoDB), criado com o objetivo de centralizar a logica crud.

A ideia é criar uma api unica e padronizada para solicitações ao banco de dados, utilizando como ponto forte uma biblioteca que chamam de evolução do rest, o GraphQL é uma linguagem de consulta, criada pelo Facebook com o objetivo de criar aplicativos clientes baseados em sintaxe intuitiva e flexível, para descrever seus requisitos e interações de dados.

Um dos principais desafios das chamadas REST tradicionais é a incapacidade do cliente de solicitar um conjunto de dados personalizado (limitado ou expandido). Na maioria dos casos, quando o cliente solicita informações do servidor, ele recebe todos ou nenhum dos campos.

Outra dificuldade é trabalhar e manter vários pontos de extremidade. Como uma plataforma cresce, consequentemente o número aumentará. Portanto, os clientes geralmente precisam solicitar dados de diferentes endpoints.

Ao construir um servidor GraphQL, é necessário apenas ter um URL para todos os dados buscando e sofrendo mutação. Assim, um cliente pode solicitar um conjunto de dados enviando uma string de consulta, descrevendo o que deseja, para um servidor.

Note que todos os pontos serão executados em container docker, dito isso não o citarei novamente.

### Origem das requisições:
- API SOAP
- API REST
- API P2P

### Estrutura:

![alt text](https://github.com/leodeliyannis/batepapo-sd/blob/master/imagens/ApiBanco%20-%20MongoDB.png)

### Tecnologias utilizadas:
- Docker - Gerenciador de containers, fornece uma camada adicional de abstração e automação de virtualização de nível de sistema operacional, cada parte do projeto(os micro serviços) serão executados em um ou mais container. Site oficial: https://www.docker.com/
- Nginx - Servidor proxy HTTP e reverso, bem como um servidor de proxy de email. O Nginx é um servidor web rápido, leve, e com inúmeras possibilidades de configuração para melhor performance, responsável pelo loadbalancer para gerenciar os varios nodos da apiBanco. Site oficial: https://nginx.org/en/
- NodeJS - Interpretador de código JavaScript, focado em migrar o Javascript do lado do cliente para servidores, é a ApiBanco propriamente dita, responsável pela execução da logica com o banco de dados. Site oficial: https://nodejs.org/en/
- MongoDB - Banco de dados orientado a documentos. Classificado como um programa de banco de dados NoSQL, o MongoDB usa documentos semelhantes a JSON com esquemas. Site oficial: https://www.mongodb.com/

### Dependencias NodeJS:
- PM2 - Para manter 2 (ou mais) processos do NodeJS executando dentro do container, caso o processo 'morrer' o pm2 inicia um novo, também contem um loadbalancer próprio para os processos.

[![NPM](https://nodei.co/npm/pm2.png?downloads=true&downloadRank=true)](https://nodei.co/npm/pm2/)

- Express - É um framework para aplicativo da web do Node.js mínimo e flexível que fornece um conjunto robusto de recursos para aplicativos web e móvel.

[![NPM](https://nodei.co/npm/express.png?downloads=true&downloadRank=true)](https://nodei.co/npm/express/)

- GraphQL - É uma linguagem de consulta criada pelo Facebook em 2012 e lançada publicamente em 2015. É considerada uma alternativa para arquiteturas REST, além de oferecer um serviço runtime para rodar comandos e consumir uma API.

[![NPM](https://nodei.co/npm/graphql.png?downloads=true&downloadRank=true)](https://nodei.co/npm/graphql/)

- Helmet - Ajuda você a proteger seus aplicativos Express configurando vários cabeçalhos HTTP. Não é uma bala de prata , mas pode ajudar.

[![NPM](https://nodei.co/npm/helmet.png?downloads=true&downloadRank=true)](https://nodei.co/npm/helmet/)

- Mongoose - Fornece uma solução direta e baseada em esquema para modelar os dados do seu aplicativo. Ele inclui conversão de tipo incorporada, validação, criação de consulta, ganchos de lógica de negócios e muito mais, fora da caixa.

[![NPM](https://nodei.co/npm/mongoose.png?downloads=true&downloadRank=true)](https://nodei.co/npm/mongoose/)

### Querys para Usuário:

- getUsuarios - Retorna todos os usuários cadastrado.
```bash
query{getUsuarios {_id Nome IPaddres Configuracao{usuario dt_criacao Atualizacoes{usuario dt_atualizacao}}}
```

- createUsuario - Cria novo usuário.
```bash
mutation{createUsuario (input: {Nome IPaddres}) {cdRetorno dsRetorno}}
```

- updateUsuario - Atualiza usuário existente, utilizando o 'id' como chave de identificação.
```bash
mutation{updateUsuario (input: {_id Nome IPaddres}) {cdRetorno dsRetorno}}
```

- deleteUsuario - Deleta usuário existente, utilizando o 'id' como chave de identificação.
```bash
mutation{deleteUsuario (input: {_id}) {cdRetorno dsRetorno}}
```
