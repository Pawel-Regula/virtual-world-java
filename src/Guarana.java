import java.util.Random;

public class Guarana extends Roslina{
    public Guarana (Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Guarana", 0, 0, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Guarana (Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                 int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Guarana", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'G';
    }
    @Override
    protected void kolizja(Organizm obcy_organizm) {

        setCzyZyje(false);
        obcy_organizm.setSila(obcy_organizm.getSila() + 3);
        getUniwersum().log(getRasa() + " ginie przez " + obcy_organizm.getRasa() + " sila "
                + obcy_organizm.getRasa() + " zostala zwiekszona na: " + obcy_organizm.getSila());

    }
}
