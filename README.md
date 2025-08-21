# JobFinder

## Descrição / Propósito
JobFinder é um sistema de gerenciamento de vagas que centraliza o cadastro de usuários e a administração de oportunidades de emprego. Ele oferece tanto uma API REST quanto páginas renderizadas via Thymeleaf para que candidatos e administradores possam interagir com o catálogo de vagas de forma segura. O projeto é voltado a equipes de desenvolvimento que precisam de uma base para acelerar a criação de portais de emprego ou módulos de RH.

## Funcionalidades
- **Autenticação e Autorização**
    - Login via API que retorna JWT, permitindo autenticação sem estado para clientes externos
    - Formulários de login e registro renderizados pelo servidor, com validação dos dados
    - Configuração de segurança separada para páginas web e endpoints REST
- **Gerenciamento de Perfil**
    - Edição de dados pessoais e alteração de senha
    - Serviço de usuários centraliza registro, atualização e mudança de senha
- **CRUD de Vagas**
    - Endpoints REST para listar, criar, atualizar e remover vagas de emprego
    - Modelo `Job` com atributos de tipo de trabalho e data de publicação
- **Documentação da API**
    - Configuração OpenAPI/Swagger com esquema de segurança bearer

## Tecnologias Utilizadas
### Backend
- Java 24
- Spring Boot 3.5.4 (Web, Data JPA, Security, Validation)
- Thymeleaf
- JWT (jjwt)
- PostgreSQL

### Frontend
- Templates HTML renderizados com Thymeleaf

### Ferramentas de apoio
- Maven (wrapper incluso)
- Lombok
- Spring Boot DevTools

## Como Iniciar o Projeto
### Pré-requisitos
- Java 24 configurado no `PATH`
- PostgreSQL em execução
- Maven (ou wrapper `mvnw`)

### Configuração do Banco de Dados
1. Crie um banco PostgreSQL local.
2. Ajuste as credenciais em `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/jobfinder
   spring.datasource.username=usuario
   spring.datasource.password=senha
   ```
3. O Hibernate está configurado para atualizar o esquema automaticamente com `spring.jpa.hibernate.ddl-auto=update`.

### Backend
```bash
# tornar o wrapper executável
chmod +x mvnw

# compilar o projeto
./mvnw clean package

# executar a aplicação
./mvnw spring-boot:run
```
A API estará disponível em `http://localhost:8080` e a documentação Swagger em `/swagger-ui.html`.

### Frontend
As páginas HTML (login, cadastro, dashboard) são servidas pelo próprio backend; não há passos adicionais para um cliente separado.

### Testes
```bash
./mvnw test
```

## Estrutura de Pastas
```
.
├── src
│   ├── main
│   │   ├── java          # código-fonte do backend
│   │   └── resources     # configurações e templates
│   └── test
│       └── java          # testes automatizados
├── pom.xml               # configuração Maven
└── LICENSE               # licença do projeto
```

## Contribuição
Pull requests são bem-vindos. Para mudanças significativas, abra primeiro uma issue para discutir o que você gostaria de alterar.

## Licença
Distribuído sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.
