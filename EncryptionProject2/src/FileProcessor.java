
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileProcessor {

    private static final int BUFFER_SIZE = 8192;

    public static void processLargeFile(
            Path inputPath,
            String key,
            int shift,
            boolean encrypt
    ) throws IOException {

        shift = ((shift % 26) + 26) % 26;

        Path outputPath = encrypt
                ? inputPath.resolveSibling("encrypted.txt")
                : inputPath.resolveSibling("decrypted.txt");

        try (
                BufferedReader reader = Files.newBufferedReader(
                        inputPath, StandardCharsets.UTF_8);

                BufferedWriter writer = Files.newBufferedWriter(
                        outputPath, StandardCharsets.UTF_8)
        ) {

            char[] buffer = new char[BUFFER_SIZE];
            int charsRead;
            int keyIndex = 0;

            while ((charsRead = reader.read(buffer)) != -1) {

                for (int i = 0; i < charsRead; i++) {

                    char c = buffer[i];

                    if (c >= 'A' && c <= 'Z') {

                        int value = c - 'A';

                        int keyShift =
                                key.charAt(keyIndex % key.length()) - 'A';

                        if (encrypt) {

                            // Шифрование Виженером
                            value = (value + keyShift) % 26;

                            // Шифрование Цезарем
                            value = (value + shift) % 26;

                        } else {

                            // Расшифровка Цезаря
                            value = (value - shift + 26) % 26;

                            // Расшифровка Виженера
                            value = (value - keyShift + 26) % 26;
                        }

                        c = (char) ('A' + value);

                        keyIndex++;
                    }

                    writer.write(c);
                }
            }
        }
    }
}
