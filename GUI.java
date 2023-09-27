import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GUI {

    Kontroll kontroll;
    JFrame vindu;
    JButton start, exit, restart;
    JPanel panel, menypanel, gridPanel, gameOver;
    JLabel antDeler = new JLabel("Lengde: ");
    JLabel gameover = new JLabel();

    JLabel[][] grid;
    int antMs = 100;

    Color gameOverFarge = new Color(255, 255, 200, 20);



    Timer timer;

    class startbehandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (start.getText().equalsIgnoreCase("Start")) {
                start.setText("STOP");
                start.setBackground(Color.ORANGE);
                timer = new Timer(antMs, new oppdaterRutenett());
                timer.start();

            } else {
                start.setText("START");
                start.setBackground(Color.GREEN);
                timer.stop();
            }

        }
    }
    class oppdaterRutenett implements ActionListener {
        int teller = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            teller++;
            if (antMs > 30 && teller == 100) {
                teller = 0;
                antMs -= 5;
                timer.setDelay(antMs);
            }
            if (kontroll.spillIgang()) {
                kontroll.startSnake();
                grid = kontroll.hentGrid();
                antDeler.setText("Lengde: " + kontroll.lengde());
                oppdaterGUI();
            } else {
                start.doClick();
                gameover.setText("GAME OVER KOMPIS!");
                gameover.setFont(new Font("Impact", Font.BOLD, 20));
                gameover.setForeground(Color.RED);

            }

        }
    }

    class exit implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            kontroll.avslutt();
        }

    }

    class TasteLytter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        kontroll.endreRetning('H');
                        break;
                    case KeyEvent.VK_LEFT:
                        kontroll.endreRetning('V');
                        break;
                    case KeyEvent.VK_UP:
                        kontroll.endreRetning('O');
                        break;
                    case KeyEvent.VK_DOWN:
                        kontroll.endreRetning('N');
                        break;
                }
            }
    }

    class restartSpill implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            antMs = 100;
            gameover.setText(" ");
            kontroll.restart();
            antDeler.setText("Lengde: " + kontroll.lengde());
        }
    }



    GUI(Kontroll k) {
        // Standard looknfeel setup
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(9);
        }

        // Oppretter en intans av klassen kontroll slik at GUI og kontroll-klassen kan samarbeide
        kontroll = k;

        grid = kontroll.hentGrid();


        // Oppretter vinduet
        vindu = new JFrame("--------- SNAKE ----------");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Oppretter et overordnet panel og legger det til i vinduet
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        vindu.add(panel);

        // Menypanel
        menypanel = new JPanel();
        menypanel.setLayout(new FlowLayout());
        menypanel.setBackground(Color.LIGHT_GRAY);
        panel.add(menypanel, BorderLayout.NORTH);


        // Start-knapp
        start = new JButton("START");
        start.setForeground(Color.BLACK);
        start.setBackground(Color.GREEN);
        start.addActionListener(new startbehandler());
        start.addKeyListener(new TasteLytter());
        start.setFocusable(true);

        // Exit-knapp
        exit = new JButton("EXIT");
        exit.setForeground(Color.BLACK);
        exit.setBackground(Color.RED);
        exit.addActionListener(new exit());

        // Restart-knapp
        restart = new JButton("RESTART");
        restart.setForeground(Color.BLACK);
        restart.setBackground(Color.BLUE);
        restart.addActionListener(new restartSpill());

        antDeler.setText("Lengde: " + kontroll.lengde());

        menypanel.add(antDeler);
        menypanel.add(start);
        menypanel.add(exit);
        menypanel.add(restart);
        menypanel.add(gameover);



        // Oppretter selve griden bestaaende av jbuttons som representerer celler
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(kontroll.rader(), kontroll.kolonner()));
        for (int rx = 0; rx < kontroll.rader(); ++rx) {
            for (int kx = 0; kx < kontroll.kolonner(); kx++) {
                JLabel rute = grid[rx][kx];
                rute.setOpaque(true);
                gridPanel.add(rute);
            }
        }
        panel.add(gridPanel, BorderLayout.CENTER);

        vindu.pack();
        vindu.setSize(1000, 1000);
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);

    }



    void oppdaterGUI() {
        for (int rx = 0; rx < kontroll.rader(); ++rx) {
            for (int kx = 0; kx < kontroll.kolonner(); kx++) {
                JLabel rute = grid[rx][kx];
                rute.setOpaque(true);
                gridPanel.add(rute);
            }
        }

    }

}
