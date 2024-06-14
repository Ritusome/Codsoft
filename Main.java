import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main extends JFrame {
    private static final Set<String> STOP_WORDS = new HashSet<>();
    private JTextArea textArea;
    private JLabel wordCountLabel;
    private JLabel uniqueWordCountLabel;
    private JTextArea wordFrequencyArea;

    static {
        // Add common stop words
        String[] stopWordsArray = {"a", "an", "and", "the", "is", "in", "at", "of", "on", "for", "to", "with"};
        for (String word : stopWordsArray) {
            STOP_WORDS.add(word);
        }
    }

    public Main() {
        setTitle("Word Counter");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // UI Components
        textArea = new JTextArea();
        wordCountLabel = new JLabel("Total word count (excluding stop words): 0");
        uniqueWordCountLabel = new JLabel("Unique words count: 0");
        wordFrequencyArea = new JTextArea();
        wordFrequencyArea.setEditable(false);

        // Layout
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        bottomPanel.add(wordCountLabel);
        bottomPanel.add(uniqueWordCountLabel);

        JPanel frequencyPanel = new JPanel();
        frequencyPanel.setLayout(new BorderLayout());
        frequencyPanel.add(new JLabel("Word frequencies:"), BorderLayout.NORTH);
        frequencyPanel.add(new JScrollPane(wordFrequencyArea), BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(frequencyPanel, BorderLayout.EAST);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open File");
        JMenuItem countItem = new JMenuItem("Count Words");

        fileMenu.add(openItem);
        fileMenu.add(countItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        // Event Handlers
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        countItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countWords();
            }
        });
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                textArea.setText(scanner.useDelimiter("\\Z").next());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void countWords() {
        String inputText = textArea.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No text provided.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] words = inputText.split("\\W+");
        int wordCount = 0;
        Map<String, Integer> wordFrequency = new HashMap<>();

        for (String word : words) {
            if (!word.trim().isEmpty()) {
                word = word.toLowerCase();
                if (!STOP_WORDS.contains(word)) {
                    wordCount++;
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }

        wordCountLabel.setText("Total word count (excluding stop words): " + wordCount);
        uniqueWordCountLabel.setText("Unique words count: " + wordFrequency.size());

        StringBuilder frequencyText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            frequencyText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        wordFrequencyArea.setText(frequencyText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
