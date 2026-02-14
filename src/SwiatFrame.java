import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SwiatFrame extends JFrame {
    private JTextArea logArea;
    private Swiat swiat;

    public SwiatFrame(Swiat swiat) {
        this.swiat = swiat;
        setTitle("Symulacja Świata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        SwiatPanel swiatPanel = new SwiatPanel(swiat);
        swiat.setSwiatPanel(swiatPanel);
        add(swiatPanel, BorderLayout.CENTER);

        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.EAST);
        swiat.setLogArea(logArea);

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton nextTurnButton = new JButton("Następna tura");
        nextTurnButton.addActionListener(e -> {
            swiat.wykonajTure();
            swiatPanel.repaint();
        });
        buttonPanel.add(nextTurnButton);

        JButton loadGameButton = new JButton("Wczytaj grę");
        loadGameButton.addActionListener(e -> {
            String sciezkaDoPliku = "Zapis.txt";
            swiat.odczytajStanGry(sciezkaDoPliku);
            swiatPanel.repaint();  // Odświeżenie panelu po wczytaniu gry
            JOptionPane.showMessageDialog(this, "Stan gry wczytany z pliku " + sciezkaDoPliku);
        });
        buttonPanel.add(loadGameButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

/*    public static void main(String[] args) {
        Swiat swiat = new Swiat(new Point(20, 20));
        new SwiatFrame(swiat);
    }*/
}
