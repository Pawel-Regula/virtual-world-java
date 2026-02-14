import java.util.Random;

public class WilczeJagody extends Roslina{
    public WilczeJagody (Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "WilczeJagody", 99, 0, 0, Kierunki.BRAK_RUCHU, true);
    }

    public WilczeJagody (Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                    int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y,"WilczeJagody", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'J';
    }
    @Override
    protected void kolizja(Organizm obcy_organizm) {

        obcy_organizm.setCzyZyje(false);
        getUniwersum().log(obcy_organizm.getRasa() + " ginie w walce z " + getRasa());

    }
}
