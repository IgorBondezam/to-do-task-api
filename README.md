# 📝 ToDoTask

Projeto Java Spring Boot para gerenciamento de tarefas com autenticação JWT, integração com GraphQL, e documentação via Swagger/OpenAPI.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security**
- **Spring Validation**
- **Spring GraphQL**
- **H2 Database (em memória)**
- **JWT (Java Web Token) - com `java-jwt`**
- **GraphQL**
- **Swagger - SpringDoc OpenAPI**

---

## 🛠️ Ferramentas

- **Maven** – gerenciamento de dependências e build
- **H2 Console** – banco de dados em memória acessível via navegador
- **GraphiQL** – interface para testes GraphQL
- **Swagger UI** – documentação e testes de endpoints REST

---

## 🔐 Autenticação

O projeto utiliza **autenticação baseada em JWT (JSON Web Token)**.

### 🔒 Como funciona?

1. O usuário se autentica com credenciais válidas via endpoint de login.
2. Se autenticado com sucesso, o sistema gera um **token JWT assinado** usando o segredo definido no `application.properties`:
   ```properties
   api.security.token=SEGREDOOO
   ```
3. Esse token deve ser utilizado nas requisições subsequentes para acessar os recursos protegidos.

---

## 🧪 Como Testar

### ✅ Subir a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## 🛠️ H2 Console

Acesse o banco de dados em memória via navegador:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:tododb`
- Usuário: `sa`
- Senha: `password`

---

## 🧬 GraphQL via GraphiQL

- **URL**: [http://localhost:8080/graphiql](http://localhost:8080/graphiql)

### 🔐 Usando autenticação no GraphiQL

1. Clique em **"Headers"** no painel lateral do GraphiQL.
2. Adicione o seguinte cabeçalho:

```json
{
  "Authorization": "Bearer <SEU_TOKEN_JWT>"
}
```

3. Agora você pode fazer requisições protegidas autenticadas com seu token.

---

## 📑 Swagger UI

- **URL**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 🔐 Usando autenticação no Swagger

1. Clique em **Authorize** (ícone de cadeado).
2. Insira o token no seguinte formato:

```
Bearer <SEU_TOKEN_JWT>
```

3. Clique em **Authorize** novamente para salvar.
4. Agora você pode testar endpoints protegidos diretamente pelo Swagger UI.

---

## 📂 Estrutura esperada

```bash
src/
 └── main/
      └── java/com/igor/bondezam/todotask/
            ├── config/
            ├── context/
            ├── controller/
            ├── converter/
            ├── domain/
            ├── dto/
            ├── exception/
            ├── repository/
            ├── service/
            └── ToDoTaskApplication.java
```

---

## 🧪 Testes

O projeto possui suporte a testes com:

- `spring-boot-starter-test`
- `spring-security-test`

Execute com:

```bash
./mvnw test
```

---