public class Wilk extends Zwierze {

    public Wilk(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Wilk", 9, 5, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Wilk(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Wilk",  sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'W';
    }
}