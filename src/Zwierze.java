import java.awt.*;
import java.io.Serializable;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Zwierze extends Organizm implements Serializable {

    public Zwierze(Swiat uniwersum, int polozenie_x, int polozenie_y, String rasa,
                   int sila, int inicjatywa, int wiek, int ost_ruch, boolean czy_zyje)
    {
        super(uniwersum, polozenie_x, polozenie_y, rasa, sila, inicjatywa, wiek, ost_ruch, czy_zyje, false);
    }

    @Override
   protected void akcja() {
        wybierzKierunekRuchu();
    }

    protected void ruch(Point potencjalna_nowa_pozycja){
        Organizm jakis_organizm
                = getUniwersum().getWszystkie_organizmy().get(potencjalna_nowa_pozycja.x).get(potencjalna_nowa_pozycja.y);

        if (jakis_organizm == null){
            setPolozenie(potencjalna_nowa_pozycja);
            getUniwersum().log(getRasa() + " rusza się na pozycję " + potencjalna_nowa_pozycja);
        }
        else {
            getUniwersum().log(getRasa() + " wchodzi w interakcje z " + jakis_organizm.getRasa() + " na pozycji "
                    + potencjalna_nowa_pozycja);

            jakis_organizm.kolizja(this);

            if(!jakis_organizm.isCzyZyje()
                    || !jakis_organizm.getPolozenie().equals(potencjalna_nowa_pozycja)) setPolozenie(potencjalna_nowa_pozycja);
        }
    }

    @Override
    protected void kolizja(Organizm obcy_organizm) {

        if(obcy_organizm.getRasa().equals(getRasa())){
            if(obcy_organizm.isMozliwoscRozmnazania() && isMozliwoscRozmnazania()) rozmnoz();
            getUniwersum().log(getRasa() + " rozmnaża się");
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
    abstract protected char rysuj();

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
                    ruch(new Point(getPolozenie().x, getPolozenie().y - 1));
                    ost_ruch = Kierunki.POLNOC;
                    czy_wybrano_ruch = true;
                    break;
                case Kierunki.POLUDNIE:
                    ruch(new Point(getPolozenie().x, getPolozenie().y + 1));
                    ost_ruch = Kierunki.POLUDNIE;
                    czy_wybrano_ruch = true;
                    break;
                case Kierunki.WSCHOD:
                    ruch(new Point(getPolozenie().x + 1, getPolozenie().y));
                    ost_ruch = Kierunki.WSCHOD;
                    czy_wybrano_ruch = true;
                    break;
                case Kierunki.ZACHOD:
                    ruch(new Point(getPolozenie().x - 1, getPolozenie().y));
                    ost_ruch = Kierunki.ZACHOD;
                    czy_wybrano_ruch = true;
                    break;

            }
            kierunek_ruchu.remove(los);

        }

    }

    protected void dodajMozliweRuchy(List<Integer> kierunek_ruchu) {
        Point pozycja = getPolozenie();

        if(czyRuchJestMozliwy(new Point( pozycja.x, pozycja.y - 1 ))){
            kierunek_ruchu.add(Kierunki.POLNOC);
        }
        if(czyRuchJestMozliwy(new Point( pozycja.x, pozycja.y + 1) )){
            kierunek_ruchu.add(Kierunki.POLUDNIE);
        }
        if(czyRuchJestMozliwy(new Point( pozycja.x + 1, pozycja.y ))){
            kierunek_ruchu.add(Kierunki.WSCHOD);
        }
        if(czyRuchJestMozliwy(new Point( pozycja.x - 1, pozycja.y ))){
            kierunek_ruchu.add(Kierunki.ZACHOD);
        }
    }

    protected boolean czyRuchJestMozliwy(Point pozycja){
        Point akt_wymiar_planszy = uniwersum.getWymiaryPlanszy();

        if(pozycja.x >= 0 && pozycja.x < akt_wymiar_planszy.x
        && pozycja.y >= 0 && pozycja.y < akt_wymiar_planszy.y) return true;

        return false;
    }


}
