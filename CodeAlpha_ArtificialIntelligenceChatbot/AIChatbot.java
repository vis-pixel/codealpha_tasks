import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class FAQDatabase {
    private static final Map<String, String> faq = new HashMap<>();

    static {
        faq.put("what is codealpha",
                "CodeAlpha is a software development company offering internships in Java, Python, Web Dev, AI and more.");
        faq.put("internship perks",
                "You will receive an Offer Letter, Completion Certificate, Unique ID Certificate, LOR (performance based), and job support.");
        faq.put("how to submit tasks",
                "Upload your code to GitHub, post a LinkedIn video, and submit using the official form.");
        faq.put("contact information", "Email: services@codealpha.tech, WhatsApp: +91 8052293611");
        faq.put("tasks list", "Student Grade Tracker, Stock Trading Platform, AI Chatbot, Hotel Reservation System.");
    }

    public static Map<String, String> getFAQMap() {
        return faq;
    }

    public static String getAnswer(String question) {
        return faq.getOrDefault(question, null);
    }
}

class IntentProcessor {
    public String process(String input) {
        input = input.toLowerCase().trim();
        if (contains(input, new String[] { "hi", "hello", "hey" }))
            return "Hello! How can I assist you today?";
        if (contains(input, new String[] { "time", "current time" }))
            return "Current time is: " + new Date().toString();
        if (contains(input, new String[] { "weather" }))
            return "The weather seems pleasant today! (This is a simulated response).";
        if (contains(input, new String[] { "thank you", "thanks" }))
            return "You're welcome! Happy to help.";
        if (contains(input, new String[] { "joke", "funny" }))
            return "Why don't programmers like nature? Too many bugs!";
        for (String q : FAQDatabase.getFAQMap().keySet()) {
            if (input.contains(q))
                return FAQDatabase.getAnswer(q);
        }
        if (input.contains("internship"))
            return "CodeAlpha internships help you learn real-world development with hands-on projects.";
        if (input.contains("java"))
            return "Java is a powerful OOP language widely used everywhere.";
        return "I'm not sure I understand, but I'm learning! Try asking differently.";
    }

    private boolean contains(String input, String[] keywords) {
        for (String k : keywords)
            if (input.contains(k))
                return true;
        return false;
    }
}

public class AIChatbot extends JFrame implements ActionListener {
    private JTextPane chatPane;
    private JTextField inputField;
    private JButton sendButton;
    private IntentProcessor processor;
    private StyledDocument doc;

    public AIChatbot() {
        processor = new IntentProcessor();

        // Frame setup
        setTitle("AI Chatbot - CodeAlpha");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat Pane
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        doc = chatPane.getStyledDocument();
        JScrollPane scrollPane = new JScrollPane(chatPane);
        add(scrollPane, BorderLayout.CENTER);

        // Input + Send button panel at top-right
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setPreferredSize(new Dimension(300, 30));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.setBackground(new Color(0, 102, 204));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setPreferredSize(new Dimension(90, 30));
        sendButton.addActionListener(this);

        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        add(inputPanel, BorderLayout.NORTH); // Top-right placement

        // Welcome message
        appendBotMessage("+--------------------------------------+\n");
        appendBotMessage("|         AI CHATBOT ACTIVATED         |\n");
        appendBotMessage("|        Type your message below       |\n");
        appendBotMessage("+--------------------------------------+\n\n");

        setVisible(true);
        inputField.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty())
            return;

        appendUserMessage("You: " + userInput + "\n");

        if (userInput.equalsIgnoreCase("exit")) {
            appendBotMessage("\n+--------------------------------------+\n");
            appendBotMessage("|          Chatbot Shutting Down       |\n");
            appendBotMessage("|             Goodbye!                 |\n");
            appendBotMessage("+--------------------------------------+\n");
            inputField.setEditable(false);
            sendButton.setEnabled(false);
            return;
        }

        String response = processor.process(userInput);
        appendBotMessage("Bot: " + response + "\n\n");
        inputField.setText("");
    }

    private void appendUserMessage(String msg) {
        appendToPane(msg, new Color(0, 102, 204), true);
    }

    private void appendBotMessage(String msg) {
        appendToPane(msg, new Color(0, 153, 0), true);
    }

    private void appendToPane(String msg, Color c, boolean bold) {
        try {
            Style style = chatPane.addStyle("Style", null);
            StyleConstants.setForeground(style, c);
            StyleConstants.setBold(style, bold);
            doc.insertString(doc.getLength(), msg, style);
            chatPane.setCaretPosition(doc.getLength());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AIChatbot());
    }
}
