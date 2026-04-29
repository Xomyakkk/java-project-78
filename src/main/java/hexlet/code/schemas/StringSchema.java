package hexlet.code.schemas;

/**
 * Схема для проверки строковых значений.
 *
 * <p>Класс позволяет задавать несколько критериев валидности строки:
 * <ul>
 *   <li><b>required</b> — обязательность значения (если true, то пустая строка считается
 *       недействительной).</li>
 *   <li><b>minLength</b> — минимальная длина строки.</li>
 *   <li><b>substring</b> — наличие определённого подстроки в строке.</li>
 * </ul>
 *
 * Методы возвращают текущий объект {@code StringSchema}, что позволяет
 * использовать цепочку вызовов (fluent interface).
 */
public class StringSchema {
    /** Флаг, указывающий, что значение обязательно. */
    private boolean required = false;

    /** Минимальная длина строки. Ноль означает отсутствие ограничения. */
    private int minLength = 0;

    /** Подстрока, которую должно содержать валидное значение. */
    private String substring = null;

    /**
     * Устанавливает обязательность значения.
     *
     * @return текущий объект {@code StringSchema}
     */
    public StringSchema required() {
        required = true;
        return this;
    }

    /**
     * Задает минимальную длину строки.
     *
     * @param length минимальная длина (>= 0)
     * @return текущий объект {@code StringSchema}
     */
    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    /**
     * Устанавливает требуемую подстроку, которую должно содержать строковое значение.
     *
     * @param substr подстрока
     * @return текущий объект {@code StringSchema}
     */
    public StringSchema contains(String substr) {
        substring = substr;
        return this;
    }

    /**
     * Проверяет валидность заданного значения по всем установленным критериям.
     *
     * <p>Алгоритм проверки:
     * <ol>
     *   <li>Если значение {@code null} или пустое, то оно считается недействительным,
     *       если поле {@code required} равно {@code true}. В противном случае
     *       возвращается {@code true}.
     *   </li>
     *   <li>Если {@code required == true} и строка пустая — сразу возврат {@code false}
     *       (этот блок немного избыточен, но повышает читаемость).
     *   </li>
     *   <li>Проверяем длину: если {@code minLength > 0} и реальная длина строки меньше
     *       заданной — возвращаем {@code false}.
     *   </li>
     *   <li>Если есть требуемая подстрока, проверяем её наличие методом {@link String#contains}.
     *       При отсутствии — возвращаем {@code false}.
     *   </li>
     *   <li>При прохождении всех условий возвращаем {@code true}. Это простое
     *       «конвейер» проверки, каждый блок отвечает за отдельный критерий.
     * </ol>
     *
     * @param value строковое значение для проверки
     * @return {@code true}, если значение удовлетворяет всем условиям,
     *         иначе {@code false}
     */
    public boolean isValid(String value) {
        // Если значение пустое или null, проверяем обязательность.
        if (value == null || value.isEmpty()) {
            return !required;
        }

        // Проверка обязательности в случае пустой строки (избыточная,
        // но повышает читаемость).
        if (required && value.isEmpty()) {
            return false;
        }

        // Проверяем минимальную длину.
        if (minLength > 0 && value.length() < minLength) {
            return false;
        }

        // Проверяем наличие подстроки, если она задана.
        if (substring != null && !value.contains(substring)) {
            return false;
        }

        // Если все условия выполнены — значение валидно.
        return true;
    }
}
