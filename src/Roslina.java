import java.awt.*;
import java.util.Random;

public abstract class Roslina extends Organizm{

    public Roslina(Swiat uniwersum, int polozenie_x, int polozenie_y, String rasa,
                   int sila, int inicjatywa, int wiek, int ost_ruch, boolean czy_zyje)
    {
        super(uniwersum, polozenie_x, polozenie_y, rasa, sila, inicjatywa, wiek, ost_ruch, czy_zyje, false);
    }

    @Override
    protected void akcja() {
        if(new Random().nextInt(2) == 0) rozmnoz();
    }

    @Override
    protected void kolizja(Organizm obcy_organizm) {
        setCzyZyje(false);
        getUniwersum().log(getRasa() + " ginie przez " + obcy_organizm.getRasa());
    }

    @Override
    abstract protected char rysuj();
}
