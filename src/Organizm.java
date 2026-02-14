import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Organizm {
    protected int sila, inicjatywa, wiek, ost_ruch;
    protected Point polozenie;
    protected boolean czy_zyje, mozliwosc_rozmnazania;
    protected String rasa;
    protected Swiat uniwersum;

    public Organizm(Swiat uniwersum, int polozenie_x, int polozenie_y, String rasa,
                    int sila, int inicjatywa, int wiek, int ost_ruch, boolean czy_zyje, boolean mozliwosc_rozmnazania) {
        this.uniwersum = uniwersum;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.wiek = wiek;
        this.rasa = rasa;
        this.polozenie = new Point(polozenie_x, polozenie_y);
        this.czy_zyje = czy_zyje;
        this.ost_ruch = ost_ruch;
        this.mozliwosc_rozmnazania = mozliwosc_rozmnazania;
    }

    abstract protected void akcja();
    abstract protected void kolizja(Organizm obcy_organizm);
    abstract protected char rysuj();

    protected void rozmnoz(){
        boolean czy_wybrano_kierunek = false;
        int wybrany_kierunek, los;
        ost_ruch = Kierunki.BRAK_RUCHU;

        List<Integer> kierunek_rozmnazania = new ArrayList<>();
        dodajMozliweRozmnazania(kierunek_rozmnazania);

        while(!czy_wybrano_kierunek && !kierunek_rozmnazania.isEmpty()) {
            los = new Random().nextInt(kierunek_rozmnazania.size());
            wybrany_kierunek = kierunek_rozmnazania.get(los);

            switch (wybrany_kierunek){
                case Kierunki.POLNOC:
                    uniwersum.nowyOrganizm(getRasa(), new Point(getPolozenie().x, getPolozenie().y - 1));
                    czy_wybrano_kierunek = true;
                    break;
                case Kierunki.POLUDNIE:
                    uniwersum.nowyOrganizm(getRasa(), new Point(getPolozenie().x, getPolozenie().y + 1));
                    czy_wybrano_kierunek = true;
                    break;
                case Kierunki.WSCHOD:
                    uniwersum.nowyOrganizm(getRasa(), new Point(getPolozenie().x + 1, getPolozenie().y));
                    czy_wybrano_kierunek = true;
                    break;
                case Kierunki.ZACHOD:
                    uniwersum.nowyOrganizm(getRasa(), new Point(getPolozenie().x - 1, getPolozenie().y));
                    czy_wybrano_kierunek = true;
                    break;

            }
            kierunek_rozmnazania.remove(los);
        }
    }

    protected void dodajMozliweRozmnazania(List<Integer> kierunek_ruchu) {
        Point pozycja = getPolozenie();

        if(czyRozmnazanieJestMozliwe(new Point( pozycja.x, pozycja.y - 1 ))){
            kierunek_ruchu.add(Kierunki.POLNOC);
        }
        if(czyRozmnazanieJestMozliwe(new Point( pozycja.x, pozycja.y + 1) )){
            kierunek_ruchu.add(Kierunki.POLUDNIE);
        }
        if(czyRozmnazanieJestMozliwe(new Point( pozycja.x + 1, pozycja.y ))){
            kierunek_ruchu.add(Kierunki.WSCHOD);
        }
        if(czyRozmnazanieJestMozliwe(new Point( pozycja.x - 1, pozycja.y ))){
            kierunek_ruchu.add(Kierunki.ZACHOD);
        }
    }

    protected boolean czyRozmnazanieJestMozliwe(Point pozycja){
        Point akt_wymiar_planszy = uniwersum.getWymiaryPlanszy();

        if(pozycja.x >= 0 && pozycja.x < akt_wymiar_planszy.x
                && pozycja.y >= 0 && pozycja.y < akt_wymiar_planszy.y) {

            Organizm jakis_organizm
                    = getUniwersum().getWszystkie_organizmy().get(pozycja.x).get(pozycja.y);
            if(jakis_organizm == null)  return true;
        }

        return false;
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public int getOstRuch() {
        return ost_ruch;
    }

    public void setOstRuch(int ost_ruch) {
        this.ost_ruch = ost_ruch;
    }

    public Point getPolozenie() {
        return polozenie;
    }

    public void setPolozenie(Point polozenie) {
        this.polozenie = polozenie;
    }

    public boolean isCzyZyje() {
        return czy_zyje;
    }

    public void setCzyZyje(boolean czy_zyje) {
        this.czy_zyje = czy_zyje;
    }

    public boolean isMozliwoscRozmnazania() {
        return mozliwosc_rozmnazania;
    }

    public void setMozliwoscRozmnazania(boolean mozliwosc_rozmnazania) {
        this.mozliwosc_rozmnazania = mozliwosc_rozmnazania;
    }

    public String getRasa() {
        return rasa;
    }

    public void setRasa(String rasa) {
        this.rasa = rasa;
    }

    public Swiat getUniwersum() {
        return uniwersum;
    }

    public void setUniwersum(Swiat uniwersum) {
        this.uniwersum = uniwersum;
    }

    public void zapisz(BufferedWriter writer) throws IOException {
        writer.write(rasa + " " + sila + " " + inicjatywa + " "
                + wiek + " " + polozenie.x + " " + polozenie.y + " " + ost_ruch + "\n");
    }
}
