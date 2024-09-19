# API E-commerce - Servidor

Este diretório contém o código-fonte do servidor da API de E-commerce. Ele foi construído com Java e Spring Boot, seguindo uma arquitetura de camadas bem estruturada. A API oferece funcionalidades essenciais para um sistema de e-commerce, incluindo operações com produtos, usuários, pedidos, e segurança.

## Índice

1. [Recursos da API](#recursos-da-api)
2. [Estrutura do Projeto](#estrutura-do-projeto)
3. [Configuração](#configuração)
4. [Endpoints](#endpoints)
5. [Segurança](#segurança)
6. [Tratamento de Erros](#tratamento-de-erros)
7. [Como Contribuir](#como-contribuir)
8. [Licença](#licença)

## Recursos da API

- Gerenciamento de produtos (CRUD)
- Gerenciamento de usuários (CRUD)
- Sistema de pedidos
- Autenticação e autorização com JWT
- Tratamento centralizado de erros

## Estrutura do Projeto

- **server/**
  - **controllers/**: Controladores da API
  - **entities/**: Classes de entidade mapeadas para o banco de dados
  - **exceptions/**: Tratamento de exceções personalizadas
  - **interfaces/**: Interfaces de repositórios e serviços
  - **security/**: Configurações de segurança (autenticação e autorização)
  - **services/**: Implementação da lógica de negócios



## Configuração

### Pré-requisitos

- Java JDK 11+
- Maven
- Banco de Dados (MySQL, PostgreSQL, etc.)

### Passo a Passo

1. **Clonar o Repositório**:
    ```bash
    git clone https://github.com/danpqdan/ECommerceFullStack.git
    cd ECommerceFullStack/java/apiecomerce
    ```

2. **Configurar Banco de Dados**:
    - Crie um banco de dados para a aplicação.
    - Configure as credenciais do banco de dados em `application.properties` ou `application.yml`:
    ```properties
    # Exemplo para MySQL
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```

3. **Executar o Servidor**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Acessar a API**:
    - A API estará rodando em `http://localhost:8080`.

## Endpoints

### Endpoints

```Swagger
localhost:8080/swagger-ui/index.html#/
```


> **Nota**: Para endpoints protegidos, é necessário fornecer um token JWT na requisição.

## Segurança

A segurança da API é configurada usando Spring Security e JWT (JSON Web Token). As principais funcionalidades incluem:

- **Autenticação**: Os usuários devem se autenticar usando suas credenciais (por exemplo, usuario e senha) para obter um token JWT.
- **Autorização**: O token JWT é usado para acessar endpoints protegidos. Os tokens têm um tempo de expiração e devem ser enviados no cabeçalho `Authorization` como `Bearer {token}`.

### Exemplo de Requisição Autenticada
```http
GET /api/produtos HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}


