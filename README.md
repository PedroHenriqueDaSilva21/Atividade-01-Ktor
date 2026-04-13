# 📚 LDDM Biblioteca API

> **Atividade LDDM — Laboratório de Desenvolvimento de Dispositivos Móveis**
>
> **Autor:** Pedro Henrique da Silva

API REST desenvolvida com **Ktor (Kotlin)** para gerenciamento de Autores e Livros. Utiliza PostgreSQL via Docker, Flyway para migrations e Swagger para documentação interativa.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Kotlin | 1.9.25 |
| Ktor | 2.3.12 |
| Exposed ORM | 0.44.1 |
| Flyway | 10.10.0 |
| PostgreSQL | 16 |
| Swagger UI | 2.3.4 |
| JVM Runtime | JBR-21 (JetBrains Runtime) |
| Build Tool | Gradle 8.7 |

---

## 📋 Pré-requisitos

- **Java 21** (JetBrains Runtime 21 — [Download IntelliJ](https://www.jetbrains.com/idea/))
- **Docker** e **Docker Compose**
- **Git**

---

## ⚙️ Configuração do Ambiente

### 1. Clone o repositório

```bash
git clone https://github.com/SEU_USUARIO/lddm-atividade.git
cd lddm-atividade
```

### 2. Configure o arquivo `.env`

Copie o exemplo e ajuste se necessário:

```bash
cp .env.example .env
```

Conteúdo padrão do `.env`:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=lddm_db
DB_USER=lddm_user
DB_PASSWORD=lddm_pass
```

---

## 🐳 Subindo o Banco de Dados (Docker)

```bash
docker-compose up -d
```

Aguarde o healthcheck ficar saudável (~10 segundos):

```bash
docker-compose ps
```

---

## ▶️ Executando a API

```bash
./gradlew run
```

> No Windows use: `gradlew.bat run`

A aplicação iniciará em **`http://localhost:8080`**.

As migrations Flyway rodam automaticamente ao iniciar — as tabelas e o seed data serão criados.

---

## 📖 Documentação Swagger

Acesse a documentação interativa:

```
http://localhost:8080/swagger
```

---

## 🔗 Endpoints

### Authors (`/api/authors`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/authors` | Lista todos os autores |
| `POST` | `/api/authors` | Cria um novo autor |
| `PUT` | `/api/authors/{id}` | Atualiza um autor |
| `DELETE` | `/api/authors/{id}` | Remove um autor |

**Exemplo de corpo para POST/PUT:**
```json
{
  "name": "Machado de Assis",
  "nationality": "Brasileiro"
}
```

---

### Books (`/api/books`)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/books` | Lista todos os livros |
| `POST` | `/api/books` | Cria um novo livro |
| `PUT` | `/api/books/{id}` | Atualiza um livro |
| `DELETE` | `/api/books/{id}` | Remove um livro |

**Exemplo de corpo para POST/PUT:**
```json
{
  "title": "Dom Casmurro",
  "isbn": "978-8535902778",
  "year": 1899,
  "authorId": 1
}
```

---

## 🗄️ Estrutura do Banco de Dados

```sql
authors(id, name, nationality, created_at)
books(id, title, isbn, year, author_id → authors.id, created_at)
```

Seed data: **5 autores** e **10 livros** clássicos inseridos automaticamente pela migration V2.

---

## 📁 Estrutura do Projeto

```
src/main/kotlin/com/lddm/
├── Application.kt
├── plugins/
│   ├── Database.kt
│   ├── Routing.kt
│   ├── Serialization.kt
│   └── Swagger.kt
├── models/
│   ├── Author.kt
│   └── Book.kt
├── repositories/
│   ├── AuthorRepository.kt
│   └── BookRepository.kt
├── services/
│   ├── AuthorService.kt
│   └── BookService.kt
└── routes/
    ├── AuthorRoutes.kt   ← Route 1
    └── BookRoutes.kt     ← Route 2
```

---

## 🛑 Parando os serviços

```bash
# Parar o banco de dados
docker-compose down

# Parar e remover o volume (apaga os dados)
docker-compose down -v
```
