package Main.ui;

import Main.business.Conteudo;
import Main.business.MC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EscolhaCategoria extends JFrame {
    private final MC mc;
    private String nomeCatAnterior;
    private JButton PLAY2;
    private JButton PLAY1;
    private JButton PLAY3;
    private JButton PLAY4;
    private JButton PLAY5;
    private JButton SAIRButton;
    private JPanel cat;
    private JButton PLAY6;
    private JButton PLAY7;
    private JButton PLAY8;
    private JComboBox paginas;
    private JLabel label1;
    private JLabel label2;
    private JLabel label4;
    private JLabel label3;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private int comeca;


    public EscolhaCategoria(MC mc) {
        this.mc = mc;
        this.comeca = 0;


        PLAY1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label1.getText()).getNomeFicheiro());
            }
        });

        PLAY2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label2.getText()).getNomeFicheiro());
            }
        });
        paginas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int j = paginas.getSelectedIndex() * 8;
                printFinal(nomeCatAnterior, j);
            }
        });
        PLAY3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label3.getText()).getNomeFicheiro());
            }
        });
        PLAY4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label4.getText()).getNomeFicheiro());
            }
        });
        PLAY5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label5.getText()).getNomeFicheiro());
            }
        });
        PLAY6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label6.getText()).getNomeFicheiro());
            }
        });
        PLAY7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label7.getText()).getNomeFicheiro());
            }
        });

        PLAY8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mc.reproduzirCont(mc.getConteudo().get(label8.getText()).getNomeFicheiro());
            }
        });
        SAIRButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }

    public void limpa() {
        PLAY1.setVisible(false);
        PLAY2.setVisible(false);
        PLAY3.setVisible(false);
        PLAY4.setVisible(false);
        PLAY5.setVisible(false);
        PLAY6.setVisible(false);
        PLAY7.setVisible(false);
        PLAY8.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
        label8.setVisible(false);
    }

    public void tornaV(int qual, String nCat) {
        switch (qual) {
            case 0:
                PLAY1.setVisible(true);
                label1.setVisible(true);
                label1.setText(nCat);

                break;
            case 1:
                PLAY2.setVisible(true);
                label2.setVisible(true);
                label2.setText(nCat);
                break;

            case 2:
                PLAY3.setVisible(true);
                label3.setVisible(true);
                label3.setText(nCat);
                break;
            case 3:
                PLAY4.setVisible(true);
                label4.setVisible(true);
                label4.setText(nCat);
                break;
            case 4:
                PLAY5.setVisible(true);
                label5.setVisible(true);
                label5.setText(nCat);
                break;
            case 5:
                PLAY6.setVisible(true);
                label6.setVisible(true);
                label6.setText(nCat);
                break;
            case 6:
                PLAY7.setVisible(true);
                label7.setVisible(true);
                label7.setText(nCat);
                break;
            case 7:
                PLAY8.setVisible(true);
                label8.setVisible(true);
                label8.setText(nCat);
                break;

            default:
        }

    }

    public void pPainel(ArrayList<Conteudo> al, Integer j) {
        limpa();
        int i = 0;
        for (int mini = j; mini < al.size(); mini++) {
            tornaV(i++, al.get(mini).getNomeCont());
        }
    }


    public void printFinal(String nCat, int j) {
        String email;
        Conteudo[] mp;
        if (mc.isLogged()) {
            email = mc.getAut().getEmail();
            mp = mc.getConteudoUser(email, nCat);
        } else {
            mp = mc.getConteudoConv(nCat);
        }

        ArrayList<Conteudo> al = new ArrayList<>();
        for (int i = 0; i < mp.length; i++) {
            Conteudo c = new Conteudo(mp[i].getNomeCont(), mp[i].getNomeFicheiro(), mp[i].getFlagCat(), mp[i].getTipo());
            al.add(i, c);
        }
        int resultado = (al.size() / 8);
        for (; this.comeca <= resultado; this.comeca++) paginas.addItem(comeca + 1);
        pPainel(al, j);
    }

    public JPanel getCat() {
        return cat;
    }

    public void setNomeCatAnterior(String nomeCatAnterior) {
        this.nomeCatAnterior = nomeCatAnterior;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        cat = new JPanel();
        cat.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 19, new Insets(0, 0, 0, 0), -1, -1));
        PLAY2 = new JButton();
        PLAY2.setText("PLAY");
        cat.add(PLAY2, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PLAY1 = new JButton();
        PLAY1.setText("PLAY");
        cat.add(PLAY1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PLAY3 = new JButton();
        PLAY3.setText("PLAY");
        cat.add(PLAY3, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(57, 11), null, 0, false));
        PLAY4 = new JButton();
        PLAY4.setText("PLAY");
        cat.add(PLAY4, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label1 = new JLabel();
        label1.setText("Label");
        cat.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 9, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label2 = new JLabel();
        label2.setText("Label");
        cat.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 9, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label3 = new JLabel();
        label3.setText("Label");
        cat.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 9, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label4 = new JLabel();
        label4.setText("Label");
        cat.add(label4, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 8, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SAIRButton = new JButton();
        SAIRButton.setText("SAIR");
        cat.add(SAIRButton, new com.intellij.uiDesigner.core.GridConstraints(5, 17, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(6, 17, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(5, 18, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(6, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(6, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        PLAY5 = new JButton();
        PLAY5.setText("PLAY");
        cat.add(PLAY5, new com.intellij.uiDesigner.core.GridConstraints(1, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label5 = new JLabel();
        label5.setText("Label");
        cat.add(label5, new com.intellij.uiDesigner.core.GridConstraints(1, 14, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(2, 18, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        PLAY6 = new JButton();
        PLAY6.setText("PLAY");
        cat.add(PLAY6, new com.intellij.uiDesigner.core.GridConstraints(2, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PLAY7 = new JButton();
        PLAY7.setText("PLAY");
        cat.add(PLAY7, new com.intellij.uiDesigner.core.GridConstraints(3, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PLAY8 = new JButton();
        PLAY8.setText("PLAY");
        cat.add(PLAY8, new com.intellij.uiDesigner.core.GridConstraints(4, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label8 = new JLabel();
        label8.setText("Label");
        cat.add(label8, new com.intellij.uiDesigner.core.GridConstraints(4, 14, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label7 = new JLabel();
        label7.setText("Label");
        cat.add(label7, new com.intellij.uiDesigner.core.GridConstraints(3, 14, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label6 = new JLabel();
        label6.setText("Label");
        cat.add(label6, new com.intellij.uiDesigner.core.GridConstraints(2, 14, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paginas = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        paginas.setModel(defaultComboBoxModel1);
        cat.add(paginas, new com.intellij.uiDesigner.core.GridConstraints(0, 18, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Selecione o conteudo pretendido :");
        cat.add(label9, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        cat.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return cat;
    }

}
