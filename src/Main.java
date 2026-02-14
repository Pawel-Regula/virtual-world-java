import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Swiat swiat = new Swiat(new Point(20, 20));
            
            new SwiatFrame(swiat);

        });
    }
}
