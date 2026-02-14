public class Owca extends Zwierze {

    public Owca(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Owca", 4, 4, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Owca(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Owca", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
            return 'O';
    }
}
