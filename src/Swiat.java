import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.JTextArea;

public class Swiat {
    private Point wymiary_planszy = new Point(20, 20);
    private List<List<Organizm>> wszystkie_organizmy = new ArrayList<>();
    private List<Organizm> listaPriorytetow = new ArrayList<>();
    private SwiatPanel swiatPanel;
    private JTextArea logArea;

    public Swiat() {
        this.wymiary_planszy = new Point(20, 20);
        utworzPlanszePierwszyRaz(wymiary_planszy);
        bazowaGra();
    }

    public Swiat(Point wymiary_planszy) {
        utworzPlanszePierwszyRaz(wymiary_planszy);
        bazowaGra();
    }


    public JTextArea getLog() {
        return logArea;
    }

    private void utworzPlanszePierwszyRaz(Point wymiary_planszy){
        this.wymiary_planszy = wymiary_planszy;

        for (int i = 0; i < wymiary_planszy.x; i++) {
            List<Organizm> wiersz = new ArrayList<>();
            for (int j = 0; j < wymiary_planszy.y; j++) {
                wiersz.add(null);
            }
            wszystkie_organizmy.add(wiersz);
        }
    }

    public void setSwiatPanel(SwiatPanel swiatPanel) {
        this.swiatPanel = swiatPanel;
    }
    public void setLogArea(JTextArea logArea) {
        this.logArea = logArea;
    }


    public void wykonajTure(){
        logArea.setText("");  // Czyszczenie logów na początku tury
        wyzerujPlansze();
        skasujMartwe();
        zaktualizujPlansze();
        sortujListePriorytetow();
        mozliwoscRozmanzaniaTak();

        for(int i = 0; i < listaPriorytetow.size(); i++){
            if(listaPriorytetow.get(i).isCzyZyje() && listaPriorytetow.get(i).isMozliwoscRozmnazania()){
                listaPriorytetow.get(i).akcja();
            }
            wyzerujPlansze();
            zaktualizujPlansze();
        }

        zaktualizujWiek();
        zapiszStanGry("Zapis.txt");
        if (swiatPanel != null) {
            swiatPanel.repaint();
        }
    }

    public void log(String message) {
        if (logArea != null) {
            logArea.append(message + "\n");
        }
    }


    public void nowyOrganizm(String rasa, Point pozycja) {
        Organizm nowyOrganizm = null;
        if (rasa.equals("Lis")) {
            nowyOrganizm = new Lis(this, pozycja.x, pozycja.y);
        } else if (rasa.equals("Owca")) {
            nowyOrganizm = new Owca(this, pozycja.x, pozycja.y);
        } else if (rasa.equals("Wilk")) {
            nowyOrganizm = new Wilk(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("Zolw")) {
            nowyOrganizm = new Zolw(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("Antylopa")) {
            nowyOrganizm = new Antylopa(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("Trawa")) {
            nowyOrganizm = new Trawa(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("Mlecz")) {
            nowyOrganizm = new Mlecz(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("Guarana")) {
            nowyOrganizm = new Guarana(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("WilczeJagody")) {
            nowyOrganizm = new WilczeJagody(this, pozycja.x, pozycja.y);
        }
        else if (rasa.equals("BarszczSosnowskiego")) {
            nowyOrganizm = new BarszczSosnowskiego(this, pozycja.x, pozycja.y);
        }

        if (nowyOrganizm != null) {
            listaPriorytetow.add(nowyOrganizm);
            zaktualizujPlansze();
            log("Dodano nowy organizm: " + rasa + " na pozycji (" + pozycja.x + ", " + pozycja.y + ")");
            if (swiatPanel != null) {
                swiatPanel.repaint();
            }
        }
    }

    public void dodajCzlowieka(int x, int y) {
        Czlowiek czlowiek = new Czlowiek(this, x, y);
        listaPriorytetow.add(czlowiek);
        zaktualizujPlansze();
        if (swiatPanel != null) {
            swiatPanel.repaint();
        }
    }

    private void bazowaGra(){
        dodajCzlowieka(2, 2);

        listaPriorytetow.add(new Owca(this, 5, 5));
        listaPriorytetow.add(new Owca(this, 10, 5));
        listaPriorytetow.add(new Lis(this, 12, 3));
        listaPriorytetow.add(new Lis(this, 7, 8));
        listaPriorytetow.add(new Antylopa(this, 2, 17));
        listaPriorytetow.add(new Antylopa(this, 14, 9));
        listaPriorytetow.add(new Zolw(this, 18, 14));
        listaPriorytetow.add(new Zolw(this, 10, 10));
        listaPriorytetow.add(new Wilk(this, 6, 16));
        listaPriorytetow.add(new Wilk(this, 17, 1));

        listaPriorytetow.add(new Trawa(this, 4, 9));
        listaPriorytetow.add(new Guarana(this, 15, 7));
        listaPriorytetow.add(new BarszczSosnowskiego(this, 11, 12));
        listaPriorytetow.add(new Mlecz(this, 3, 10));
        listaPriorytetow.add(new WilczeJagody(this, 19, 19));
        zaktualizujPlansze();
    }


    public List<Organizm> getListaPriorytetow() {
        return listaPriorytetow;
    }

    public void zaktualizujPlansze() {
        for (Organizm stworzenie : listaPriorytetow) {
            if(stworzenie.isCzyZyje()){
                wszystkie_organizmy.get(stworzenie.getPolozenie().x).set(stworzenie.getPolozenie().y, stworzenie);
            }
        }
    }

    private void zaktualizujWiek() {
        for (Organizm stworzenie : listaPriorytetow) {
            if(stworzenie.isCzyZyje()){
                stworzenie.setWiek(stworzenie.getWiek() + 1);
            }
        }
    }

    private void mozliwoscRozmanzaniaTak() {
        for (Organizm stworzenie : listaPriorytetow) {
            if(stworzenie.isCzyZyje()){
                stworzenie.setMozliwoscRozmnazania(true);
            }
        }
    }

    private void skasujMartwe (){
        for(int i = 0; i < listaPriorytetow.size(); i++){
            if(!listaPriorytetow.get(i).isCzyZyje()){
                listaPriorytetow.remove(i);
                i--;
            }
        }
    }

    private void wyzerujPlansze() {
        for (int i = 0; i < wymiary_planszy.x; i++) {
            for (int j = 0; j < wymiary_planszy.y; j++) {
                wszystkie_organizmy.get(i).set(j, null);
            }
        }
    }

    public Point getWymiaryPlanszy() {
        return wymiary_planszy;
    }

    public void setWymiaryPlanszy(Point wymiary_planszy) {
        this.wymiary_planszy = wymiary_planszy;
    }

    public List<List<Organizm>> getWszystkie_organizmy() {
        return wszystkie_organizmy;
    }

    public void setWszystkie_organizmy(List<List<Organizm>> wszystkie_organizmy) {
        this.wszystkie_organizmy = wszystkie_organizmy;
    }

    public void sortujListePriorytetow() {
        Collections.sort(listaPriorytetow, new Comparator<Organizm>() {
            @Override
            public int compare(Organizm o1, Organizm o2) {
                if (o1.getInicjatywa() == o2.getInicjatywa()) {
                    return o2.getWiek() - o1.getWiek();
                } else {
                    return o2.getInicjatywa() - o1.getInicjatywa();
                }
            }
        });
    }
    public SwiatPanel getSwiatPanel() {
        return swiatPanel;
    }
    public void zapiszStanGry(String sciezka) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sciezka))) {
            writer.write(wymiary_planszy.x + " " + wymiary_planszy.y + "\n");
            for (Organizm organizm : listaPriorytetow) {
                organizm.zapisz(writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void odczytajStanGry(String sciezka) {
        listaPriorytetow = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(sciezka))) {
            String[] wymiary = reader.readLine().split(" ");
            wymiary_planszy.x = Integer.parseInt(wymiary[0]);
            wymiary_planszy.y = Integer.parseInt(wymiary[1]);

            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] dane = linia.split(" ");
                String rasa = dane[0];
                int sila = Integer.parseInt(dane[1]);
                int inicjatywa = Integer.parseInt(dane[2]);
                int wiek = Integer.parseInt(dane[3]);
                int polozenieX = Integer.parseInt(dane[4]);
                int polozenieY = Integer.parseInt(dane[5]);
                int ostRuch = Integer.parseInt(dane[6]);
                int umiejetnosc = 0;
                if (rasa.equals("Czlowiek")) {
                    umiejetnosc = Integer.parseInt(dane[7]);
                }
                nowyOrganizmZPliku(rasa, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch, umiejetnosc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nowyOrganizmZPliku(String rasa, int polozenieX, int polozenieY, int sila, int inicjatywa, int wiek, int ostRuch,
                                    int umijetnosc) {
        switch (rasa) {
            case "Mlecz":
                listaPriorytetow.add(new Mlecz(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Guarana":
                listaPriorytetow.add(new Guarana(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "WilczeJagody":
                listaPriorytetow.add(new WilczeJagody(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "BarszczSosnowskiego":
                listaPriorytetow.add(new BarszczSosnowskiego(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Trawa":
                listaPriorytetow.add(new Trawa(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Antylopa":
                listaPriorytetow.add(new Antylopa(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Zolw":
                listaPriorytetow.add(new Zolw(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Lis":
                listaPriorytetow.add(new Lis(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Owca":
                listaPriorytetow.add(new Owca(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Wilk":
                listaPriorytetow.add(new Wilk(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch));
                break;
            case "Czlowiek":
                listaPriorytetow.add(new Czlowiek(this, polozenieX, polozenieY, sila, inicjatywa, wiek, ostRuch,
                        umijetnosc));
                break;
            default:
                throw new IllegalArgumentException("Nieznana rasa: " + rasa);
        }
    }
}
