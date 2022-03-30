import java.io.*;

import java.awt.*;

import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;

public class three_species_model {

    public static void main(String s[]) {

        JFrame frame = new JFrame("3-gatunkowy model ekosystemu na sieci kwadratowej");

        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout());

        Wykres_three_species_model wykres = new Wykres_three_species_model();

        JLabel sliderLabel1 = new JLabel("pr_1");
        JLabel sliderLabel2 = new JLabel("pr_2");

        JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider1.setMajorTickSpacing(20);
        slider1.setMinorTickSpacing(10);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);

        JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider2.setMajorTickSpacing(20);
        slider2.setMinorTickSpacing(10);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);

        JButton button = new JButton();

        button.setText("START");

        button.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent arg0) {

                // TODO Auto-generated method stub

                wykres.rysowac = true;

                wykres.repaint();

            }

        });

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                wykres.pr_1 = (double) (slider1.getValue()) / 100;
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print("Wartość pr_1: " + wykres.pr_1 + "\n" + "Wartość pr_2: " + wykres.pr_2);
            }
        });

        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                wykres.pr_2 = (double) (slider2.getValue()) / 100;
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.print("Wartość pr_1: " + wykres.pr_1 + "\n" + "Wartość pr_2: " + wykres.pr_2);
            }
        });

        panel.add(button);

        panel.add(sliderLabel1);

        panel.add(slider1);

        panel.add(sliderLabel2);

        panel.add(slider2);

        panel.add(wykres.counter_a);

        panel.add(wykres.counter_b);

        panel.setBackground(Color.orange);

        panel.add(wykres);

        frame.add(panel);

        frame.setSize(700, 700);

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

}

class Wykres_three_species_model extends JPanel {

    int wielkosc = 500, a = 0, b = 0;

    int[][] lattice = new int[wielkosc][wielkosc];

    boolean rysowac = false;

    double pr_1 = 0.5, pp_1 = 0, pr_2 = 0.5, pp_2 = 0, p_3 = 1; // prawdopodobieństwa rozmnożenia się

    JLabel counter_a = new JLabel("Liczba drapieżników 1: " + a);
    JLabel counter_b = new JLabel("Liczba drapieżników 2: " + b);

    public Wykres_three_species_model() {
        setBorder(BorderFactory.createLineBorder(Color.green, 2));

        for (int k = 0; k < wielkosc; k++) {
            for (int l = 0; l < wielkosc; l++) {
                lattice[k][l] = (int) (Math.random() * 3 + 1);

                if (lattice[k][l] == 1) {
                    a++;
                }
                if (lattice[k][l] == 2) {
                    b++;
                }
            }
        }

    }

    public Dimension getPreferredSize()

    {
        return new Dimension(wielkosc, wielkosc);
    }

    void step() {

        for (int counter = 0; counter < wielkosc * wielkosc; counter++) {

            int x_old = (int) (wielkosc * Math.random());

            int y_old = (int) (wielkosc * Math.random());

            // int lattice_old=lattice[x_old][y_old];

            if (lattice[x_old][y_old] != 0) {

                int x = x_old;

                int y = y_old;

                double losowa = Math.random();

                if (losowa < 0.25) {

                    x = x + 1;

                    if (x == wielkosc) {

                        x = 0;

                    }

                } else if (losowa < 0.5) {

                    x = x - 1;

                    if (x == -1) {

                        x = wielkosc - 1;

                    }

                } else if (losowa < 0.75) {

                    y = y + 1;

                    if (y == wielkosc) {

                        y = 0;

                    }

                } else {

                    y = y - 1;

                    if (y == -1) {

                        y = wielkosc - 1;

                    }

                }

                if (lattice[x][y] == 0) {

                    // przemieszczanie

                    lattice[x][y] = (lattice[x_old][y_old] + lattice[x][y]) % 4;

                    lattice[x_old][y_old] = 0;

                    // zachowanie ekosystemu 1-drapieżnik_1, 2-drapieżnik_2, 3-ofiara

                    if (lattice[x][y] == 1) {

                        if (x + 1 == wielkosc) {
                            x = 2;
                        }
                        if (x - 1 == -1) {
                            x = wielkosc - 2;
                        }
                        if (y + 1 == wielkosc) {
                            y = 2;
                        }
                        if (y - 1 == -1) {
                            y = wielkosc - 2;
                        }

                        if (lattice[x + 1][y] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x + 1][y] = 1;
                            } else {
                                lattice[x + 1][y] = 0;
                            }
                        } else if (lattice[x - 1][y] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x - 1][y] = 1;
                            } else {
                                lattice[x - 1][y] = 0;
                            }
                        } else if (lattice[x][y + 1] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x][y + 1] = 1;
                            } else {
                                lattice[x][y + 1] = 0;
                            }
                        } else if (lattice[x][y - 1] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x][y - 1] = 1;
                            } else {
                                lattice[x][y - 1] = 0;
                            }
                        } else if (lattice[x + 1][y] != 3 && lattice[x - 1][y] != 3 && lattice[x][y + 1] != 3
                                && lattice[x][y - 1] != 3) {
                            if (Math.random() >= pp_1) {
                                lattice[x][y] = 0;
                            }
                        }
                    }

                    else if (lattice[x][y] == 2) {

                        if (x + 1 == wielkosc) {
                            x = 2;
                        }
                        if (x - 1 == -1) {
                            x = wielkosc - 2;
                        }
                        if (y + 1 == wielkosc) {
                            y = 2;
                        }
                        if (y - 1 == -1) {
                            y = wielkosc - 2;
                        }

                        if (lattice[x + 1][y] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x + 1][y] = 2;
                            } else {
                                lattice[x + 1][y] = 0;
                            }
                        } else if (lattice[x - 1][y] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x - 1][y] = 2;
                            } else {
                                lattice[x - 1][y] = 0;
                            }
                        } else if (lattice[x][y + 1] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x][y + 1] = 2;
                            } else {
                                lattice[x][y + 1] = 0;
                            }
                        } else if (lattice[x][y - 1] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x][y - 1] = 2;
                            } else {
                                lattice[x][y - 1] = 0;
                            }
                        } else if (lattice[x + 1][y] != 3 && lattice[x - 1][y] != 3 && lattice[x][y + 1] != 3
                                && lattice[x][y - 1] != 3) {
                            if (Math.random() >= pp_1) {
                                lattice[x][y] = 0;
                            }
                        }

                    } else if (lattice[x][y] == 3) {

                        if (x + 1 == wielkosc) {
                            x = 2;
                        }
                        if (x - 1 == -1) {
                            x = wielkosc - 2;
                        }
                        if (y + 1 == wielkosc) {
                            y = 2;
                        }
                        if (y - 1 == -1) {
                            y = wielkosc - 2;
                        }

                        if (lattice[x + 1][y] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x + 1][y] = 3;
                            }
                        } else if (lattice[x - 1][y] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x - 1][y] = 3;
                            }
                        } else if (lattice[x][y + 1] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x][y + 1] = 3;
                            }
                        } else if (lattice[x][y - 1] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x][y - 1] = 3;
                            }
                        }
                    }
                }

                if (lattice[x][y] != 0) {
                    if (lattice[x][y] == 1) {
                        if (x + 1 == wielkosc) {
                            x = 2;
                        }
                        if (x - 1 == -1) {
                            x = wielkosc - 2;
                        }
                        if (y + 1 == wielkosc) {
                            y = 2;
                        }
                        if (y - 1 == -1) {
                            y = wielkosc - 2;
                        }

                        if (lattice[x + 1][y] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x + 1][y] = 1;
                            } else {
                                lattice[x + 1][y] = 0;
                            }
                        } else if (lattice[x - 1][y] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x - 1][y] = 1;
                            } else {
                                lattice[x - 1][y] = 0;
                            }
                        } else if (lattice[x][y + 1] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x][y + 1] = 1;
                            } else {
                                lattice[x][y + 1] = 0;
                            }
                        } else if (lattice[x][y - 1] == 3) {
                            if (Math.random() <= pr_1) {
                                lattice[x][y - 1] = 1;
                            } else {
                                lattice[x][y - 1] = 0;
                            }
                        } else if (lattice[x + 1][y] != 3 && lattice[x - 1][y] != 3 && lattice[x][y + 1] != 3
                                && lattice[x][y - 1] != 3) {
                            if (Math.random() >= pp_1) {
                                lattice[x][y] = 0;
                            }
                        }
                    }

                    else if (lattice[x][y] == 2) {
                        if (x + 1 == wielkosc) {
                            x = 2;
                        }
                        if (x - 1 == -1) {
                            x = wielkosc - 2;
                        }
                        if (y + 1 == wielkosc) {
                            y = 2;
                        }
                        if (y - 1 == -1) {
                            y = wielkosc - 2;
                        }

                        if (lattice[x + 1][y] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x + 1][y] = 2;
                            } else {
                                lattice[x + 1][y] = 0;
                            }
                        } else if (lattice[x - 1][y] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x - 1][y] = 2;
                            } else {
                                lattice[x - 1][y] = 0;
                            }
                        } else if (lattice[x][y + 1] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x][y + 1] = 2;
                            } else {
                                lattice[x][y + 1] = 0;
                            }
                        } else if (lattice[x][y - 1] == 3) {
                            if (Math.random() <= pr_2) {
                                lattice[x][y - 1] = 2;
                            } else {
                                lattice[x][y - 1] = 0;
                            }
                        } else if (lattice[x + 1][y] != 3 && lattice[x - 1][y] != 3 && lattice[x][y + 1] != 3
                                && lattice[x][y - 1] != 3) {
                            if (Math.random() >= pp_1) {
                                lattice[x][y] = 0;
                            }
                        }
                    }

                    else if (lattice[x][y] == 3) {

                        if (x + 1 == wielkosc) {
                            x = 2;
                        }
                        if (x - 1 == -1) {
                            x = wielkosc - 2;
                        }
                        if (y + 1 == wielkosc) {
                            y = 2;
                        }
                        if (y - 1 == -1) {
                            y = wielkosc - 2;
                        }

                        if (lattice[x + 1][y] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x + 1][y] = 3;
                            }
                        } else if (lattice[x - 1][y] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x - 1][y] = 3;
                            }
                        } else if (lattice[x][y + 1] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x][y + 1] = 3;
                            }
                        } else if (lattice[x][y - 1] == 0) {
                            if (Math.random() <= p_3) {
                                lattice[x][y - 1] = 3;
                            }
                        }
                    }
                }

            }
        } // end for
        a = 0;
        b = 0;
        for (int i = 0; i < wielkosc; i++) {
            for (int j = 0; j < wielkosc; j++) {
                if (lattice[i][j] == 1) {
                    a++;
                }
                if (lattice[i][j] == 2) {
                    b++;
                }
            }
        }

        counter_a.setText("Liczba drapieżników 1: " + a);
        counter_b.setText("Liczba drapieżników 2: " + b);

    } // end step()

    @Override
    public void paintComponent(Graphics graf) {

        super.paintComponent(graf);

        for (int i = 0; i < wielkosc; i++) {

            for (int j = 0; j < wielkosc; j++) {

                if (lattice[i][j] == 1) {

                    graf.setColor(Color.blue);

                    graf.drawRect(i, j, 1, 1);

                }

                if (lattice[i][j] == 2) {

                    graf.setColor(Color.red);

                    graf.drawRect(i, j, 1, 1);

                }

                if (lattice[i][j] == 3) {

                    graf.setColor(Color.green);

                    graf.drawRect(i, j, 1, 1);

                }

            }

        }

        if (rysowac) {

            step();

            try {

                Thread.sleep(20); // sleep for 10 msec

            } catch (InterruptedException t) {
            }

            repaint();

        }

    }

}