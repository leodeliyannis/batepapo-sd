# batepapo-sd

### Sobre:
ApiBanco desenvolvida em NodeJS é responsável por fazer todas as operações no banco(MongoDB), criado com o objetivo de centralizar a logica crud.
Note que todos os pontos serão executados em container docker, dito isso não o citarei novamente.

### Origem das requisições:
- API SOAP
- API REST
- API P2P

### Estrutura:

![alt text](https://github.com/leodeliyannis/batepapo-sd/blob/master/imagens/ApiBanco%20-%20MongoDB.png)

### Tecnologias utilizadas:
- Docker - Gerenciador de containers, o Docker fornece uma camada adicional de abstração e automação de virtualização de nível de sistema operacional, cada parte do projeto(os micro serviços) serão executados em um container. Site oficial: https://www.docker.com/
- Nginx - Servidor proxy HTTP e reverso, bem como um servidor de proxy de email. O Nginx é um servidor web rápido, leve, e com inúmeras possibilidades de configuração para melhor performance, responsável pelo loadbalancer para gerenciar os varios nodos da apiBanco. Site oficial: https://nginx.org/en/
- NodeJS - Interpretador de código JavaScript, focado em migrar o Javascript do lado do cliente para servidores, é a ApiBanco propriamente dita, responsável pela execução da logica com o banco de dados. Site oficial: https://nodejs.org/en/
- MongoDB - Banco de dados orientado a documentos. Classificado como um programa de banco de dados NoSQL, o MongoDB usa documentos semelhantes a JSON com esquemas. Site oficial: https://www.mongodb.com/

### Dependencias NodeJS:
- PM2 - Para manter 2 (ou mais) processos do NodeJS executando dentro do container, caso o processo 'morrer' o pm2 inicia um novo, também contem um loadbalancer próprio para os processos.
[![NPM](https://nodei.co/npm/pm2.png?downloads=true&downloadRank=true)](https://nodei.co/npm/pm2/)

- Express - É um framework para aplicativo da web do Node.js mínimo e flexível que fornece um conjunto robusto de recursos para aplicativos web e móvel.
[![NPM](https://nodei.co/npm/pm2.png?downloads=true&downloadRank=true)](https://nodei.co/npm/express/)

- GraphQL - É uma linguagem de consulta criada pelo Facebook em 2012 e lançada publicamente em 2015. É considerada uma alternativa para arquiteturas REST, além de oferecer um serviço runtime para rodar comandos e consumir uma API.
[![NPM](https://nodei.co/npm/pm2.png?downloads=true&downloadRank=true)](https://nodei.co/npm/graphql/)

- Helmet - Ajuda você a proteger seus aplicativos Express configurando vários cabeçalhos HTTP. Não é uma bala de prata , mas pode ajudar.
[![NPM](https://nodei.co/npm/pm2.png?downloads=true&downloadRank=true)](https://nodei.co/npm/helmet/)

- Mongoose - Fornece uma solução direta e baseada em esquema para modelar os dados do seu aplicativo. Ele inclui conversão de tipo incorporada, validação, criação de consulta, ganchos de lógica de negócios e muito mais, fora da caixa.
[![NPM](https://nodei.co/npm/pm2.png?downloads=true&downloadRank=true)](https://nodei.co/npm/mongoose/)
