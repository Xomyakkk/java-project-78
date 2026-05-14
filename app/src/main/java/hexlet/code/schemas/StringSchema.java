package hexlet.code.schemas;

/**
 * Конкретная схема валидации строковых значений.
 *
 * <p>Наследует {@link BaseSchema} и реализует проверку строк на основе
 * следующих критериев:</p>
 *
 * <ul>
 *   <li><b>Обязательность</b> — если вызван метод {@link #required()}, значение не должно быть {@code null}
 *       и пустой строкой.</li>
 *   <li><b>Минимальная длина</b> — при использовании метода {@link #minLength(int)} проверяется,
 *       что строка содержит хотя бы заданное количество символов.</li>
 *   <li><b>Подстрока</b> — метод {@link #contains(String)} указывает, чтобы в строке присутствовала
 *       определённая подстрока.</li>
 * </ul>
 *
 * @author hexlet.code
 */
public final class StringSchema extends BaseSchema<String> {
    private int minLength;
    private String substring;

    /**
     * Помечает схему как требующую ненулевое значение.
     *
     * <p>Переопределяет метод {@link BaseSchema#required()} для уточнения
     * возвращаемого типа при цепочном вызове методов (fluent API).</p>
     *
     * @return текущий экземпляр схемы
     */
    @Override
    public StringSchema required() {
        super.required();
        return this;
    }

    /**
     * Устанавливает минимальную длину строки, необходимую для валидного значения.
     *
     * <p>При последующей проверке {@link #isValid(Object)} строка будет считаться
     * недействительной, если её длина меньше заданной.</p>
     *
     * @param length минимальное число символов в строке
     * @return текущий экземпляр схемы
     */
    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    /**
     * Указывает, что строка должна содержать определенную подстроку.
     *
     * <p>При проверке {@link #isValid(Object)} наличие этой подстроки будет учитываться.</p>
     *
     * @param substr требуемая подстрока
     * @return текущий экземпляр схемы
     */
    public StringSchema contains(String substr) {
        substring = substr;
        return this;
    }

    /**
     * Проверяет валидность переданного значения согласно заданным правилам.
     *
     * <p>Проверка включает:</p>
     *
     * <ul>
     *   <li>Если значение {@code null} и флаг {@link #required} установлен, возвращается {@code false}.
     *   <li>Если строка пустая и она обязательна, возвращается {@code false}.
     *   <li>Если длина строки меньше заданного значения {@link #minLength}, возвращается {@code false}.
     *   <li>Если строка не содержит требуемую подстроку {@link #substring}, возвращается {@code false}.
     * </ul>
     *
     * @param value объект для проверки (ожидается {@link String})
     * @return {@code true} если значение соответствует всем правилам схемы,
     *         {@code false} иначе
     */
    @Override
    public boolean isValid(Object value) {
        if (value == null) {
            return !required;
        }

        String str = (String) value;

        if (required && str.isEmpty()) {
            return false;
        }

        if (minLength > 0 && str.length() < minLength) {
            return false;
        }

        if (substring != null && !str.contains(substring)) {
            return false;
        }

        return true;
    }
}
