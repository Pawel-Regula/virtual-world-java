
import java.util.Random;
public class Zolw extends Zwierze {
    public Zolw(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Zolw", 2, 1, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Zolw(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Zolw", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected void akcja() {
        int los = new Random().nextInt(4);

        if(los == 0)wybierzKierunekRuchu();
        else {
            getUniwersum().log(getRasa() + " nie wykonuje ruchu");
        }
    }

    @Override
    protected void kolizja(Organizm obcy_organizm) {

        if(obcy_organizm.getRasa().equals(getRasa())){
            if(obcy_organizm.isMozliwoscRozmnazania() && isMozliwoscRozmnazania()) rozmnoz();
            getUniwersum().log(getRasa() + " rozmnaża się");
        } else if(obcy_organizm.getSila() < 5){
            getUniwersum().log(getRasa() + " odpiera wrogi atak");
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
    protected char rysuj() {
        return 'Z';
    }

}
