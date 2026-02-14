import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antylopa extends Zwierze {

    public Antylopa(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Antylopa", 4, 4, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Antylopa(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                    int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Antylopa", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'A';
    }

    @Override
    protected void wybierzKierunekRuchu() {
        boolean czy_wybrano_ruch = false;
        int wybrany_ruch, los;
        ost_ruch = Kierunki.BRAK_RUCHU;

        List<Integer> kierunek_ruchu = new ArrayList<>();
        dodajMozliweRuchy(kierunek_ruchu);

        while(!czy_wybrano_ruch && !kierunek_ruchu.isEmpty()) {
            los = new Random().nextInt(kierunek_ruchu.size());
            wybrany_ruch = kierunek_ruchu.get(los);

            switch (wybrany_ruch){
                case Kierunki.POLNOC:
                    if(czyRuchJestMozliwy(new Point( getPolozenie().x, getPolozenie().y - 2))){
                        getUniwersum().log(getRasa() + " skacze");
                        ruch(new Point(getPolozenie().x, getPolozenie().y - 2));
                    }
                    else {
                        ruch(new Point(getPolozenie().x, getPolozenie().y - 1));
                    }
                    ost_ruch = Kierunki.POLNOC;
                    czy_wybrano_ruch = true;
                    break;
                case Kierunki.POLUDNIE:
                    if(czyRuchJestMozliwy(new Point( getPolozenie().x, getPolozenie().y + 2))){
                        getUniwersum().log(getRasa() + " skacze");
                        ruch(new Point(getPolozenie().x, getPolozenie().y + 2));
                    }
                    else {
                        ruch(new Point(getPolozenie().x, getPolozenie().y + 1));
                    }
                    ost_ruch = Kierunki.POLUDNIE;
                    czy_wybrano_ruch = true;
                    break;
                case Kierunki.WSCHOD:
                    if(czyRuchJestMozliwy(new Point( getPolozenie().x + 2, getPolozenie().y))){
                        getUniwersum().log(getRasa() + " skacze");
                        ruch(new Point(getPolozenie().x + 2, getPolozenie().y));
                    }
                    else {
                        ruch(new Point(getPolozenie().x + 1, getPolozenie().y + 1));
                    }
                    ost_ruch = Kierunki.WSCHOD;
                    czy_wybrano_ruch = true;
                    break;
                case Kierunki.ZACHOD:
                    if(czyRuchJestMozliwy(new Point( getPolozenie().x - 2, getPolozenie().y))){
                        getUniwersum().log(getRasa() + " skacze");
                        ruch(new Point(getPolozenie().x - 2, getPolozenie().y));
                    }
                    else {
                        ruch(new Point(getPolozenie().x - 1, getPolozenie().y + 1));
                    }
                    ost_ruch = Kierunki.ZACHOD;
                    czy_wybrano_ruch = true;
                    break;

            }
            kierunek_ruchu.remove(los);
        }

    }

    @Override
    protected void kolizja(Organizm obcy_organizm) {
        boolean czy_wybrano_ruch = false;
        int wybrany_ruch, los = new Random().nextInt(2);

        if(obcy_organizm.getRasa().equals(getRasa())){
            if(obcy_organizm.isMozliwoscRozmnazania() && isMozliwoscRozmnazania()) rozmnoz();
            getUniwersum().log(getRasa() + " rozmnaża się");
        }
        else if(los == 0){
            int kierunek_ucieczki;
            ost_ruch = Kierunki.BRAK_RUCHU;
            List<Integer> kierunek_ruchu = new ArrayList<>();
            dodajMozliweRozmnazania(kierunek_ruchu);

            while(!czy_wybrano_ruch && !kierunek_ruchu.isEmpty()) {
                kierunek_ucieczki = new Random().nextInt(kierunek_ruchu.size());
                wybrany_ruch = kierunek_ruchu.get(kierunek_ucieczki);

                switch (wybrany_ruch){
                    case Kierunki.POLNOC:
                        setPolozenie(new Point(getPolozenie().x, getPolozenie().y - 1));
                        ost_ruch = Kierunki.POLNOC;
                        czy_wybrano_ruch = true;
                        break;
                    case Kierunki.POLUDNIE:
                        setPolozenie(new Point(getPolozenie().x, getPolozenie().y + 1));
                        ost_ruch = Kierunki.POLUDNIE;
                        czy_wybrano_ruch = true;
                        break;
                    case Kierunki.WSCHOD:
                        setPolozenie(new Point(getPolozenie().x + 1, getPolozenie().y));
                        ost_ruch = Kierunki.WSCHOD;
                        czy_wybrano_ruch = true;
                        break;
                    case Kierunki.ZACHOD:
                        setPolozenie(new Point(getPolozenie().x - 1, getPolozenie().y));
                        ost_ruch = Kierunki.ZACHOD;
                        czy_wybrano_ruch = true;
                        break;

                }
                kierunek_ruchu.remove(los);
            }
            if(ost_ruch != Kierunki.BRAK_RUCHU) getUniwersum().log(getRasa() + " ucieka przed walka");
        }

        if(los != 0 || ost_ruch == Kierunki.BRAK_RUCHU)
        {
            if (obcy_organizm.getSila() >= getSila())
            {
                setCzyZyje(false);
                getUniwersum().log(getRasa() + " ginie w walce z " + obcy_organizm.getRasa());
            }
            else {
                obcy_organizm.setCzyZyje(false);
                getUniwersum().log(obcy_organizm.getRasa() + " ginie w walce z " + getRasa());
            }
        }

    }
}
