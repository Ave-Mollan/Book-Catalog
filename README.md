# Book Catalog — Лабораторна робота

> **Студент:** Ян Левицький  
> **Група:** 121-22-3  
> **Дата:** 03.04.2026  

---

## 📋 Зміст

- [Мета роботи](#мета-роботи)
- [Технологічний стек](#технологічний-стек)
- [Архітектура](#архітектура)
- [База даних](#база-даних)
- [Функціонал](#функціонал)
- [Структура проєкту](#структура-проєкту)
- [Результати](#результати)
- [Висновки](#висновки)

---

## Мета роботи

Розробити повноцінний клієнт-серверний застосунок на Java, що реалізує **каталог книг** з підтримкою CRUD-операцій, фільтрації, пошуку та імпорту даних із зовнішнього API (**Open Library Search API**).

---

## Технологічний стек

| Компонент | Технологія |
|-----------|-----------|
| **Мова** | Java 17 |
| **Фреймворк** | Spring Boot 4.0.5 |
| **ORM** | Spring Data JPA / Hibernate 7.2.7 |
| **База даних** | H2 (in-memory) |
| **Шаблонізатор** | Thymeleaf + Bootstrap 5 |
| **Зовнішній API** | Open Library Search API (JSON) |
| **Парсинг JSON** | Jackson (`ObjectMapper`) |
| **HTTP клієнт** | `RestTemplate` |
| **Збірка** | Maven |
| **Допоміжні бібліотеки** | Lombok |

---

## Архітектура

Застосунок побудований за **багатошаровою архітектурою**:

```mermaid
flowchart TD
    Browser -->|HTTP Request| Controller
    Controller -->|DTO| Service
    Service -->|Entity| Repository
    Repository -->|JPA/SQL| H2[(H2 Database)]
    Service -->|RestTemplate + Jackson| OpenLibrary[Open Library API]
    Controller -->|Model + DTO| Thymeleaf
    Thymeleaf -->|HTML Response| Browser
```

### Шари застосунку

- **`controller`** — обробка HTTP-запитів (`@Controller`, `@RequestMapping`)
- **`service`** — бізнес-логіка, звернення до зовнішнього API
- **`repository`** — робота з базою даних через Spring Data JPA
- **`entity`** — JPA-сутності (`@Entity`, `@Table`)
- **`dto`** — об'єкти передачі даних між шарами
- **`mapper`** — конвертація між `Entity` ↔ `DTO`
- **`config`** — конфігурація бінів (`RestTemplate`, `ObjectMapper`)

---

## База даних

Реалізовано **дві пов'язані таблиці** зі зв'язком `One-to-Many`:

```mermaid
erDiagram
    GENRES {
        bigint id PK
        varchar name
    }
    BOOKS {
        bigint id PK
        varchar title
        int publish_year
        varchar author
        varchar description
        varchar cover_url
        float rating
        bigint genre_id FK
    }
    GENRES ||--o{ BOOKS : "has many"
```

### Таблиця `genres`

| Поле | Тип | Опис |
|------|-----|------|
| `id` | `BIGINT` | Первинний ключ (auto increment) |
| `name` | `VARCHAR` | Назва жанру (унікальна) |

### Таблиця `books`

| Поле | Тип | Опис |
|------|-----|------|
| `id` | `BIGINT` | Первинний ключ (auto increment) |
| `title` | `VARCHAR` | Назва книги |
| `publish_year` | `INTEGER` | Рік видання |
| `author` | `VARCHAR` | Автор |
| `description` | `VARCHAR` | Короткий опис |
| `cover_url` | `VARCHAR` | Посилання на обкладинку |
| `rating` | `FLOAT` | Рейтинг (локальне поле) |
| `genre_id` | `BIGINT` | Зовнішній ключ → `genres.id` |

---

## Функціонал

### ✅ CRUD операції

Реалізовано повний набір операцій для сутності `Book`:

- **Create** — додавання нової книги через форму
- **Read** — перегляд списку книг
- **Update** — редагування даних книги
- **Delete** — видалення книги з підтвердженням

Для сутності `Genre` реалізовано:

- створення жанру
- перегляд списку жанрів
- видалення жанру

### 🔍 Пошук та фільтрація

В `BookRepository` реалізовано спеціальні методи:

```java
List<Book> findByTitleContainingIgnoreCase(String title);
List<Book> findByGenreId(Long genreId);
```

### 🌐 Імпорт із зовнішнього API (Open Library)

Сервіс звертається до `https://openlibrary.org/search.json` через `RestTemplate`, отримує JSON-відповідь і парсить її через `ObjectMapper`.

Імпорт заповнює:

- назву книги (`title`)
- автора (`author_name`)
- рік першої публікації (`first_publish_year`)
- URL обкладинки за `cover_i`

### 🎭 DTO патерн

Для передачі даних між шарами використовується `BookDto`, який містить поле `genreName` — це спрощує відображення списку книг у шаблонах без додаткової логіки у view.

---

## Структура проєкту

```text
src/
├── main/
│   ├── java/com/moviecatalog/_21223levytskyi/
│   │   ├── Application.java
│   │   ├── config/
│   │   │   └── AppConfig.java
│   │   ├── controller/
│   │   │   ├── BookController.java
│   │   │   └── GenreController.java
│   │   ├── dto/
│   │   │   ├── BookDto.java
│   │   │   └── GenreDto.java
│   │   ├── entity/
│   │   │   ├── Book.java
│   │   │   └── Genre.java
│   │   ├── mapper/
│   │   │   └── BookMapper.java
│   │   ├── repository/
│   │   │   ├── BookRepository.java
│   │   │   └── GenreRepository.java
│   │   └── service/
│   │       ├── BookService.java
│   │       └── GoogleBooksParserService.java
│   └── resources/
│       ├── application.properties
│       ├── data.sql
│       └── templates/
│           ├── books/
│           │   ├── list.html
│           │   ├── form.html
│           │   └── search.html
│           └── genres/
│               └── list.html
└── pom.xml
```

---

## Результати

### Каталог книг — `/books`

<img width="1920" height="1080" alt="Снимок экрана 2026-04-03 214249" src="https://github.com/user-attachments/assets/0201fd35-4937-4593-8607-b71a1359596a" />

### Форма додавання/редагування книги — `/books/new`

<img width="1920" height="1080" alt="Снимок экрана 2026-04-03 214303" src="https://github.com/user-attachments/assets/294687d0-c6c0-4148-a627-8bbab9e48f8a" />

### Імпорт із Open Library — `/books/search-openlibrary`

<img width="1920" height="1080" alt="Снимок экрана 2026-04-03 214319" src="https://github.com/user-attachments/assets/2da9bbb2-7286-41c0-b98f-bd4253ee9cad" />

### Управління жанрами — `/genres`

<img width="1920" height="1080" alt="Снимок экрана 2026-04-03 214335" src="https://github.com/user-attachments/assets/402d6a44-280d-42d8-84c8-8e0f4ca638df" />

---

## Висновки

В ході виконання лабораторної роботи було розроблено повноцінний клієнт-серверний застосунок на **Spring Boot** із реалізацією основних вимог:

- ✅ Багатошарова архітектура з використанням **DTO патерну**
- ✅ CRUD-операції для сутності `Book`
- ✅ Робота з пов'язаними сутностями `Genre` → `Book` (`One-to-Many`)
- ✅ Інтеграція із зовнішнім **JSON API (Open Library)** через `RestTemplate` та `Jackson`
- ✅ Серверна візуалізація на базі **Thymeleaf** + Bootstrap 5
- ✅ Зберігання даних у реляційній БД **H2** через **Spring Data JPA**
- ✅ Пошук за назвою та фільтрація за жанром через кастомні методи репозиторію
