
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileValidator {

    public static void validateFile(Path path) {

        if (path == null) {
            throw new IllegalArgumentException(
                    "Сначала выберите файл!"
            );
        }

        try {
            if (!Files.exists(path)) {
                throw new IllegalArgumentException(
                        "Файл не существует!"
                );
            }

            if (!Files.isRegularFile(path)) {
                throw new IllegalArgumentException(
                        "Выбранный объект не является файлом!"
                );
            }

            if (!Files.isReadable(path)) {
                throw new IllegalArgumentException(
                        "Нет доступа для чтения файла!"
                );
            }

            if (Files.size(path) == 0) {
                throw new IllegalArgumentException(
                        "Файл пустой!"
                );
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Не удалось проверить файл: " + e.getMessage()
            );
        }
    }

    public static void validateVigenereKey(String key) {

        if (key == null || !key.matches("[A-Z]+")) {
            throw new IllegalArgumentException(
                    "Ключ должен содержать английские заглавные буквы A-Z!"
            );
        }
    }

    public static int validateCaesarKey(String key) {

        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Сдвиг Цезаря должен быть целым числом!"
            );
        }
    }
}
