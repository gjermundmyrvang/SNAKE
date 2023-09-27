import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


class Modell {

    int rader, kolonner, lengde, skattX, skattY;

    ArrayList<Point> slangekropp = new ArrayList<>();
    boolean spilligang = true;
    Random random;
    Character retning = 'H';
    JLabel[][] grid;
    Point matPos;


    Modell() {
        rader = 25;
        kolonner = 25;
        grid = new JLabel[rader][kolonner];
        fyllGrid();
        lengde = 1;
        random = new Random();
        slangekropp.add(new Point(random.nextInt(rader-1), random.nextInt(kolonner-1)));

        nySkatt();
        oppdaterGrid();
    }

    int rader() {
        return rader;
    }

    int kolonner() {
        return kolonner;
    }

    void startSnake() {
            sjekkGrenser();
            flyttSlange();
            oppdaterGrid();
    }

    void restart() {
        lengde = 1;
        slangekropp.removeAll(slangekropp);
        slangekropp.add(new Point(random.nextInt(rader-1), random.nextInt(kolonner-1)));
        spilligang = true;
        nySkatt();
        oppdaterGrid();
    }


    boolean spillIgang() {
        return spilligang;
    }

    void nySkatt() {
        skattX = random.nextInt(rader);
        skattY = random.nextInt(kolonner);
        matPos = new Point(skattX, skattY);
    }

    void flyttSlange() {


        Point hode = slangekropp.get(0);
        Point nytthode;
        int r = hode.x;
        int k = hode.y;
        switch (retning) {
            case 'O':
                slangekropp.remove(slangekropp.size()-1);
                nytthode = new Point(r-1, k);
                if (slangekropp.contains(nytthode)) { spilligang = false; }
                slangekropp.add(0, nytthode);
                break;
            case 'N':
                slangekropp.remove(slangekropp.size()-1);
                nytthode = new Point(r+1, k);
                if (slangekropp.contains(nytthode)) { spilligang = false; }
                slangekropp.add(0, nytthode);
                break;
            case 'H':
                slangekropp.remove(slangekropp.size()-1);
                nytthode = new Point(r, k+1);
                if (slangekropp.contains(nytthode)) { spilligang = false; }
                slangekropp.add(0, nytthode);
                break;
            case 'V':
                slangekropp.remove(slangekropp.size()-1);
                nytthode = new Point(r, k-1);
                if (slangekropp.contains(nytthode)) { spilligang = false; }
                slangekropp.add(0, nytthode);
                break;
        }

        if (slangekropp.get(0).equals(matPos)) {
            leggTilKroppsDel();
            nySkatt();
        }

        /*// Flytt hver kroppsdel i slangen
        for (int i = slangekropp.size() - 1; i >= 1; i--) {
            slangekropp.set(i, slangekropp.get(i - 1));
        }*/


    }

    void sjekkGrenser() {

        if (slangekropp.get(0).x > rader || slangekropp.get(0).x < 0
            || slangekropp.get(0).y > kolonner || slangekropp.get(0).y < 0) {
            spilligang = false;
        }

    }


    void leggTilKroppsDel() {
        lengde++;
        Point sistePos = slangekropp.get(slangekropp.size()-1);
        slangekropp.add(new Point(sistePos.x, sistePos.y));
    }

    void endreRetning(Character r) {
        switch (r) {
            case 'H':
                if (retning!='V') {retning = 'H';}
                break;
            case 'V':
                if (retning!='H') {retning = 'V';}
                break;
            case 'O':
                if (retning!='N') {retning = 'O';}
                break;
            case 'N':
                if (retning!='O') {retning = 'N';}
                break;
        }
    }

    void fyllGrid() {
        for (int i = 0; i < rader; i++) {
            for ( int j = 0; j < kolonner; j++) {
                JLabel rute = new JLabel();
                rute.setBackground(Color.BLACK);
                grid[i][j] = rute;
            }
        }
    }

    void oppdaterGrid() {

        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                Point sjekk = new Point(i, j);
                if (slangekropp.contains(sjekk)) {
                    if (sjekk.equals(slangekropp.get(0))){
                        grid[i][j].setBackground(new Color(75, 230, 1));
                    } else {
                        grid[i][j].setBackground(new Color(50, 141, 0));
                    }
                } else {
                    grid[i][j].setBackground(Color.BLACK);
                }
            }
        }

        grid[skattX][skattY].setBackground(new Color(240, 255, 3));

    }

    JLabel[][] hentGrid() {
        return grid;
    }

    int lengde() {
        return lengde;
    }






}
