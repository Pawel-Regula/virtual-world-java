import java.awt.*;

public class Lis extends Zwierze {

    public Lis(Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "Lis", 3, 7, 0, Kierunki.BRAK_RUCHU, true);
    }

    public Lis(Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
               int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "Lis",  sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'L';
    }

    @Override
    protected boolean czyRuchJestMozliwy(Point pozycja){
        Point akt_wymiar_planszy = uniwersum.getWymiaryPlanszy();

        if(pozycja.x >= 0 && pozycja.x < akt_wymiar_planszy.x
                && pozycja.y >= 0 && pozycja.y < akt_wymiar_planszy.y) {

            Organizm jakis_organizm
                    = getUniwersum().getWszystkie_organizmy().get(pozycja.x).get(pozycja.y);
            if(jakis_organizm == null || jakis_organizm.getSila() <= getSila())  return true;
        }

        return false;
    }
}
