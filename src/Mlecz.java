import java.util.Random;

public class Mlecz extends Roslina{
    public Mlecz(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Mlecz", 0, 0, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Mlecz(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                 int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Mlecz", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'M';
    }
    @Override
    protected void akcja() {
        if(new Random().nextInt(2) == 0) rozmnoz();
        if(new Random().nextInt(2) == 0) rozmnoz();
        if(new Random().nextInt(2) == 0) rozmnoz();
    }
}
