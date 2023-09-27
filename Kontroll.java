import javax.swing.*;

class Kontroll {

    Modell modell;
    GUI gui;

    Kontroll() {
        modell = new Modell();
        gui = new GUI(this);
    }

    int rader() {
        return modell.rader();
    }

    int kolonner() {
        return modell.kolonner();
    }

    void startSnake() {
        modell.startSnake();
    }

    void restart() {
        modell.restart();
    }


    JLabel[][] hentGrid() {
        return modell.hentGrid();
    }

    void endreRetning(Character r) {
        modell.endreRetning(r);
    }

    boolean spillIgang() {
        return modell.spillIgang();
    }

    int lengde() {
        return modell.lengde();
    }

    void avslutt() {
        System.exit(0);
    }
}
