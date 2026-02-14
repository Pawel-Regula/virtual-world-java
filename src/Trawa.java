import java.util.Random;

public class Trawa extends Roslina{
    public Trawa(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Trawa", 0, 0, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Trawa(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                 int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Trawa", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'T';
    }

}
