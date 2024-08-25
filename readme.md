Organização no GitHub:
Histórico: Branch onde todo o histórico de desenvolvimento do projeto é armazenado.
Final: Branch contendo os arquivos finalizados do projeto.
Arquivo .RAR: Arquivo compactado com todos os arquivos finais do projeto.

Os arquivos finais no github são:

API: soundsynth
Front: Thymeleaf
Em caso de dúvidas, são os arquivos mais recentes.

Requisitos para rodar o projeto:
Java 22 (JRE e JDK);
Uma IDE compatível com Java 22, Spring Boot e Thymeleaf (recomendamos o IntelliJ);
PostgreSQL 16 ou superior instalado;
Banco de dados configurado com o nome soundsynthDB.
Instruções para inicialização:
Para rodar o projeto, execute a API e o frontend em abas separadas da IDE. A API utiliza, por padrão, a porta 8080, enquanto o frontend roda na porta 8090. As portas podem ser modificadas no arquivo application.properties.
Se o banco de dados estiver vazio ao inicializar o projeto, crie um carrinho de compras utilizando a requisição POST http://localhost:8080/carrinho/novo. Essa requisição pode ser feita pelo navegador ou por ferramentas como Postman ou Insomnia.

Todas as outras funcionalidades do sistema podem ser acessadas pela interface do usuário.

Links:
Download do projeto: https://drive.google.com/file/d/1laDkjlICDe5Nsn5K2Mr9l_sSjy56el5m/view?usp=drive_link 
GitHub: 
https://github.com/SrMinikoski/ProjetoFinal-AcademiaJava 
Apresentação em vídeo:
https://youtu.be/ML47Ew1zOdI 
Arquitetura do projeto:
O sistema é dividido em duas partes principais:

API: API REST desenvolvida em Spring Boot com Java 22, responsável pelo gerenciamento dos dados e comunicação com o banco de dados PostgreSQL. A persistência é feita com Hibernate e JPA, utilizando Lombok para simplificação do código.

Principais funcionalidades da API:
Cadastrar, exibir, alterar e excluir produtos;
Buscar produtos por categoria ou nome;
Gerenciar imagens de produtos;
Criar e gerenciar carrinhos de compras (incluir, remover e alterar a quantidade de itens);
Calcular subtotais à vista e a prazo.
Nota: Antes de excluir um produto, o sistema remove o item do carrinho para evitar inconsistências.

Frontend: Desenvolvido em Spring Boot com Thymeleaf, o frontend utiliza o padrão MVC para consumir e exibir os dados da API. A interface é organizada com componentes como Navbar e Footer, que são reutilizados em todas as páginas.

Principais páginas:
vitrine.html: Lista de produtos, com filtro baseado em categorias e pesquisas;
produto.html: Página de detalhes do produto;
index.html: Página inicial com carrossel de produtos em promoção e principais categorias;
carrinho.html: Exibe os itens do carrinho com funcionalidades de aumentar/diminuir quantidade, remover itens e calcular subtotal;
login.html: Tela de login para administração;
vitrineAdm.html: Lista de produtos com opções de alteração e exclusão;
Formulários para cadastro e edição de produtos (cadastrarproduto.html, editarproduto.html).
O frontend também conta com uma camada básica de segurança, implementada com Spring Security, exigindo login para acesso às funções administrativas.

Credenciais administrativas:
Usuário: admin
Senha: admin

Implementações futuras:
Modo escuro;
Cadastro e validação de usuários;
Vinculação entre usuários e carrinhos de compras;
Finalização de compras com integração da API dos Correios para cálculo de frete;
Controle de estoque com alerta de esgotamento;
Maior detalhamento e categorização dos produtos;
Suporte a múltiplas imagens por produto;
Melhoria nas descrições e especificações dos produtos;
Validações mais completas de segurança, como OAuth;
Abstração em mais componentes para facilitar a manutenção e extensões futuras;
Melhorias visuais no design do site.


Considerações finais:
Foi um projeto muito enriquecedor, que ofereceu diversos desafios e aprendizados. É uma ideia que permite um grande leque de novas funções e implementações.
O foco principal do projeto foi chegar a um visual agradável e uma identidade forte, que se diferencia de outros sites similares, mas possuindo a mesma essência. Bem como uma interface de fácil utilização.
Agradeço imensamente pela oportunidade de participar da sétima academia Java, entrego este projeto não apenas como uma tarefa final, mas como uma forma de demonstrar o quanto pude aproveitar dos conhecimentos a mim transmitidos.
Muito obrigado.
João Lucas Andrade Minikoski da Silva – Sétima Academia Java

