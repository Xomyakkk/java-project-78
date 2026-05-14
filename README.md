### Hexlet tests and linter status:
[![Actions Status](https://github.com/Xomyakkk/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Xomyakkk/java-project-78/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Xomyakkk_java-project-78&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Xomyakkk_java-project-78)

# Validator

Библиотека для валидации данных, вдохновлённая [yup](https://github.com/jquense/yup).

## Возможности

- **StringSchema** — проверка строк: обязательность, минимальная длина, содержание подстроки
- **NumberSchema** — проверка чисел: обязательность, положительность, диапазон значений
- **MapSchema** — проверка Map: обязательность, размер, форма (shape) со вложенными схемами

## Быстрый старт

```java
Validator v = new Validator();

// Строки
StringSchema schema = v.string().required().minLength(5).contains("hex");
schema.isValid("hexlet"); // true

// Числа
NumberSchema numSchema = v.number().positive().range(1, 100);
numSchema.isValid(50); // true

// Map
MapSchema mapSchema = v.map().required().sizeof(2);
mapSchema.isValid(Map.of("k1", "v1", "k2", "v2")); // true

// Вложенная валидация с shape
Map<String, BaseSchema<?>> schemas = new HashMap<>();
schemas.put("name", v.string().required());
schemas.put("age", v.number().positive());

MapSchema personSchema = v.map().shape(schemas);
```

## Архитектура

Проект построен на принципах ООП с использованием fluent-интерфейса. Иерархия классов:

- `BaseSchema<T>` — базовый абстрактный класс для всех схем
- `StringSchema` — валидация строк
- `NumberSchema` — валидация чисел
- `MapSchema` — валидация Map-объектов

## Сборка

```bash
cd app
./gradlew build
```

## Тестирование

```bash
./gradlew test
```

Для анализа покрытия кода тестами используется [JaCoCo](https://github.com/jacoco/jacoco).
