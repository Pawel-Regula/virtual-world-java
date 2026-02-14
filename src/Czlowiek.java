import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;

public class Czlowiek extends Zwierze implements KeyListener, Serializable {
    private boolean ruchWykonany = false;
    private int umiejetnosc = 0;
    public Czlowiek(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Czlowiek", 5, 4, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Czlowiek(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                    int ost_ruch, int umiejetnosc) {
        super(uniwersum, polozenie_x, polozenie_y, "Czlowiek", sila, inicjatywa, wiek, ost_ruch, true);
        this. umiejetnosc = umiejetnosc;
    }

    @Override
    protected char rysuj() {
        return 'C';
    }

    @Override
    protected void wybierzKierunekRuchu() { }

    @Override
    public void akcja() {
        System.out.println("!Wybierz ruch dla gracza!");
        ruchWykonany = false; // Resetowanie zmiennej na początku nowej tury
        if(umiejetnosc > 1) umiejetnosc--;
        else if(umiejetnosc == 1) umiejetnosc = -5;
        else if(umiejetnosc < 0 ) umiejetnosc++;


        // Dodaj siebie jako KeyListener do panelu gry (SwiatPanel)
        SwiatPanel swiatPanel = uniwersum.getSwiatPanel();
        if (swiatPanel != null) {
            swiatPanel.addKeyListener(this);
            swiatPanel.requestFocus(); // Upewnij się, że panel ma focus, aby KeyListener działał
        }

        getUniwersum().log(getRasa() + " aktualny stan umiejetnosci: " + umiejetnosc);
    }

    // Implementacja metod interfejsu KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        if (ruchWykonany) {
            return; // Ignoruj dalsze wejścia klawiatury, jeśli ruch już został wykonany w tej turze
        }

        int key = e.getKeyCode();
        boolean ruchZrobiony = false;

        switch (key) {
            case KeyEvent.VK_UP: // POLNOC
                if (czyRuchJestMozliwy(new Point(getPolozenie().x, getPolozenie().y - 1))) {
                    ruch(new Point(getPolozenie().x, getPolozenie().y - 1));
                    ruchZrobiony = true;
                }
                break;
            case KeyEvent.VK_DOWN: // POLUDNIE
                if (czyRuchJestMozliwy(new Point(getPolozenie().x, getPolozenie().y + 1))) {
                    ruch(new Point(getPolozenie().x, getPolozenie().y + 1));
                    ruchZrobiony = true;
                }
                break;
            case KeyEvent.VK_LEFT: // ZACHOD
                if (czyRuchJestMozliwy(new Point(getPolozenie().x - 1, getPolozenie().y))) {
                    ruch(new Point(getPolozenie().x - 1, getPolozenie().y));
                    ruchZrobiony = true;
                }
                break;
            case KeyEvent.VK_RIGHT: // WSCHOD
                if (czyRuchJestMozliwy(new Point(getPolozenie().x + 1, getPolozenie().y))) {
                    ruch(new Point(getPolozenie().x + 1, getPolozenie().y));
                    ruchZrobiony = true;
                }
                break;
            case KeyEvent.VK_U:
                if (umiejetnosc == 0) {
                    umiejetnosc = 5;
                }
                break;
        }

        if (ruchZrobiony) {
            ruchWykonany = true; // Ustaw zmienną na true, aby zablokować kolejne ruchy w tej turze

            // Po wybraniu ruchu, usuń siebie jako KeyListener, aby uniknąć wielokrotnego obsłużenia ruchu
            SwiatPanel swiatPanel = uniwersum.getSwiatPanel();
            if (swiatPanel != null) {
                swiatPanel.removeKeyListener(this);
            }
        }
    }

    @Override
    protected void kolizja(Organizm obcy_organizm) {

        if(umiejetnosc > 0){
            getUniwersum().log(getRasa() + " uzywa tarczy na " + obcy_organizm.getRasa());
        }
        else if (obcy_organizm.getSila() >= getSila())
        {
            setCzyZyje(false);
            getUniwersum().log(getRasa() + " ginie w walce z " + obcy_organizm.getRasa());
        }
        else {
            obcy_organizm.setCzyZyje(false);
            getUniwersum().log(obcy_organizm.getRasa() + " ginie w walce z " + getRasa());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Metoda wywoływana, gdy klawisz zostanie zwolniony
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Metoda wywoływana, gdy pojedynczy znak zostanie wpisany
    }
@Override
    public void zapisz(BufferedWriter writer) throws IOException {
        writer.write(rasa + " " + sila + " " + inicjatywa + " "
                + wiek + " " + polozenie.x + " " + polozenie.y + " " + ost_ruch +
                " " + umiejetnosc + "\n");
    }
}
