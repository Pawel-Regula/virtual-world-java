import java.awt.*;
import java.util.Random;

public class BarszczSosnowskiego extends Roslina{
    public BarszczSosnowskiego (Swiat uniwersum, int polozenie_x, int polozenie_y) {
        super(uniwersum, polozenie_x, polozenie_y, "BarszczSosnowskiego", 10, 0, 0, Kierunki.BRAK_RUCHU, true);
    }

    public BarszczSosnowskiego (Swiat uniwersum, int polozenie_x, int polozenie_y, int sila, int inicjatywa, int wiek,
                         int ost_ruch) {
        super(uniwersum, polozenie_x, polozenie_y, "BarszczSosnowskiego", sila, inicjatywa, wiek, ost_ruch, true);
    }

    @Override
    protected char rysuj() {
        return 'B';
    }
    @Override
    protected void kolizja(Organizm obcy_organizm) {

        obcy_organizm.setCzyZyje(false);
        getUniwersum().log(obcy_organizm.getRasa() + " ginie w walce z " + getRasa());

    }
    @Override
    protected void akcja() {
        super.akcja();
        pozabijajZwierzeta();
    }
    private boolean czyJestTuZwierze(Point pozycja){
        Point akt_wymiar_planszy = uniwersum.getWymiaryPlanszy();

        if(pozycja.x >= 0 && pozycja.x < akt_wymiar_planszy.x
                && pozycja.y >= 0 && pozycja.y < akt_wymiar_planszy.y) {

            Organizm jakis_organizm
                    = getUniwersum().getWszystkie_organizmy().get(pozycja.x).get(pozycja.y);
            if(jakis_organizm != null && jakis_organizm.getInicjatywa() != 0)  return true;
        }

        return false;
    }
   private void pozabijajZwierzeta() {
        Point pozycja = getPolozenie();

        if(czyJestTuZwierze(new Point( pozycja.x, pozycja.y - 1 ))){
            getUniwersum().getWszystkie_organizmy().get(pozycja.x).get(pozycja.y - 1).setCzyZyje(false);
        }
        if(czyJestTuZwierze(new Point( pozycja.x, pozycja.y + 1) )){
            getUniwersum().getWszystkie_organizmy().get(pozycja.x).get(pozycja.y + 1).setCzyZyje(false);
        }
        if(czyJestTuZwierze(new Point( pozycja.x + 1, pozycja.y ))){
            getUniwersum().getWszystkie_organizmy().get(pozycja.x + 1).get(pozycja.y).setCzyZyje(false);
        }
        if(czyJestTuZwierze(new Point( pozycja.x - 1, pozycja.y ))){
            getUniwersum().getWszystkie_organizmy().get(pozycja.x - 1).get(pozycja.y).setCzyZyje(false);
        }
    }

}
