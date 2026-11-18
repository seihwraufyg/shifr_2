
import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class Main extends JFrame {

    private JTextField fileField;
    private JTextField keyField;
    private JTextField shiftField;
    private JTextArea resultArea;
    private JComboBox<String> modeBox;
    private Path inputPath;

    public Main() {
        setTitle("Шифрование файлов");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(0, 1, 8, 8));

        // Выбор файла
        add(new JLabel("Выбранный файл:"));

        fileField = new JTextField();
        fileField.setEditable(false);
        add(fileField);

        JButton chooseButton = new JButton("Выбрать файл");
        add(chooseButton);

        chooseButton.addActionListener(e -> chooseFile());

        // Ключ Виженера
        add(new JLabel("Ключ Виженера (английские буквы):"));

        keyField = new JTextField();
        add(keyField);

        // Сдвиг Цезаря
        add(new JLabel("Сдвиг Цезаря:"));

        shiftField = new JTextField();
        add(shiftField);

        // Режим
        add(new JLabel("Режим обработки:"));

        modeBox = new JComboBox<>(
                new String[]{"Зашифровать", "Расшифровать"}
        );
        add(modeBox);

        // Кнопка запуска
        JButton startButton = new JButton("Начать обработку");
        add(startButton);

        startButton.addActionListener(e -> startProcessing());

        // Результат
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            inputPath = chooser.getSelectedFile().toPath();
            fileField.setText(inputPath.toString());
            resultArea.setText("Файл выбран.");
        }
    }

    private void startProcessing() {
        try {
            String key = keyField.getText().trim();
            int shift;

            // Проверка файла
            FileValidator.validateFile(inputPath);

            // Проверка ключа Виженера
            FileValidator.validateVigenereKey(key);

            // Проверка ключа Цезаря
            shift = FileValidator.validateCaesarKey(
                    shiftField.getText().trim()
            );

            boolean encrypt = modeBox.getSelectedIndex() == 0;

            // Обработка файла
            FileProcessor.processLargeFile(
                    inputPath,
                    key,
                    shift,
                    encrypt
            );

            if (encrypt) {
                resultArea.setText(
                        "Шифрование завершено!\n" +
                                "Результат сохранён в encrypted.txt"
                );
            } else {
                resultArea.setText(
                        "Расшифровка завершена!\n" +
                                "Результат сохранён в decrypted.txt"
                );
            }

        } catch (Exception ex) {
            resultArea.setText("Ошибка: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main window = new Main();
            window.setVisible(true);
        });
    }
}
