import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwiatPanel extends JPanel {
    private Swiat swiat;

    public SwiatPanel(Swiat swiat) {
        this.swiat = swiat;
        setPreferredSize(new Dimension(swiat.getWymiaryPlanszy().x * 20, swiat.getWymiaryPlanszy().y * 20));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 20;
                int y = e.getY() / 20;

                if (x >= 0 && x < swiat.getWymiaryPlanszy().x && y >= 0 && y < swiat.getWymiaryPlanszy().y) {
                    if (swiat.getWszystkie_organizmy().get(x).get(y) == null) {
                        showPopupMenu(e.getComponent(), e.getX(), e.getY(), x, y);
                    }
                }
            }
        });
    }
    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
    }

    private void showPopupMenu(Component component, int x, int y, int gridX, int gridY) {
        JPopupMenu popupMenu = new JPopupMenu();
        String[] organizmy = {"Lis", "Owca", "Wilk", "Zolw", "Antylopa",
                "Trawa", "Mlecz", "Guarana", "WilczeJagody", "BarszczSosnowskiego"};

        for (String organizm : organizmy) {
            JMenuItem menuItem = new JMenuItem(organizm);
            menuItem.addActionListener(e -> {
                swiat.nowyOrganizm(organizm, new Point(gridX, gridY));
                repaint();
            });
            popupMenu.add(menuItem);
        }

        popupMenu.show(component, x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < swiat.getWymiaryPlanszy().x; i++) {
            for (int j = 0; j < swiat.getWymiaryPlanszy().y; j++) {
                Organizm organizm = swiat.getWszystkie_organizmy().get(i).get(j);
                if (organizm != null) {
                    g.drawString(String.valueOf(organizm.rysuj()), i * 20, (j + 1) * 20);
                } else {
                    g.drawRect(i * 20, j * 20, 20, 20);
                }
            }
        }
    }
}
