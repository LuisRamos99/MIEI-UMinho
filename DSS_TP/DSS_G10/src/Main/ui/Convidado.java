package Main.ui;

import Main.business.MC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Convidado extends JFrame {
    public JPanel conv;
    private JFormattedTextField pesquisaFormattedTextField;
    private JButton bt1;
    private JButton bt5;
    private JButton bt2;
    private JButton bt6;
    private JButton SAIRButton;
    private JButton bt3;
    private JButton bt7;
    private JButton estilo3;
    private JButton bt4;
    private JButton bt8;
    private JComboBox comboBox1;
    private int flag;
    private final MC mc;
    private int comeca;
    private int tipo;

    public Convidado(MC mc) {
        this.mc = mc;
        this.comeca = 0;

        bt1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                flag = 1;
                super.mouseClicked(e);

            }
        });
        comboBox1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int j = comboBox1.getSelectedIndex() * 8;
                printFinal(j);

            }
        });
        bt1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt1);
            }
        });
        bt2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt2);

            }
        });
        bt3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt3);

            }
        });
        bt4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt4);

            }
        });
        bt5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt5);

            }
        });

        bt6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt6);

            }
        });
        bt7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt7);

            }
        });
        bt8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                defineWindow(bt8);

            }
        });
        SAIRButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (mc.isLogged()) {
                    dispose();
                } else {
                    System.exit(0);
                }
            }
        });
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void tornaV(int qual, String nCat) {
        switch (qual) {
            case 0:
                bt1.setVisible(true);
                bt1.setText(nCat);
                break;
            case 1:
                bt2.setVisible(true);
                bt2.setText(nCat);
                break;

            case 2:
                bt3.setVisible(true);
                bt3.setText(nCat);
                break;
            case 3:
                bt4.setVisible(true);
                bt4.setText(nCat);
                break;
            case 4:
                bt5.setVisible(true);
                bt5.setText(nCat);
                break;
            case 5:
                bt6.setVisible(true);
                bt6.setText(nCat);
                break;
            case 6:
                bt7.setVisible(true);
                bt7.setText(nCat);
                break;
            case 7:
                bt8.setVisible(true);
                bt8.setText(nCat);
                break;

            default:
        }

    }
    private void defineWindow(JButton bEsc) {
        EscolhaCategoria ec = new EscolhaCategoria(mc);
        ec.setContentPane(ec.getCat());
        ec.setVisible(true);
        ec.pack();
        ec.setSize(600, 600);
        ec.setLocationRelativeTo(null);
        ec.setNomeCatAnterior(bEsc.getText());
        ec.printFinal(bEsc.getText(), 0);
    }

    public int getFlag() {
        return flag;
    }

    public void limpa() {
        bt1.setVisible(false);
        bt2.setVisible(false);
        bt3.setVisible(false);
        bt4.setVisible(false);
        bt5.setVisible(false);
        bt6.setVisible(false);
        bt7.setVisible(false);
        bt8.setVisible(false);
    }

    public void pPainel(ArrayList<String> al, Integer j) {
        limpa();
        int i = 0;
        for (int mini = j; mini < al.size(); mini++) {
            tornaV(i++, al.get(mini));
        }
    }
    //Através do array de categorias preenche os botões e as páginas, consoante o seu tamanho.
    public void printFinal(int j) {
        ArrayList<String> al = mc.getNomesTeste(tipo);
        int resultado = (al.size() / 8);
        for (; this.comeca <= resultado; this.comeca++) comboBox1.addItem(comeca + 1);
        pPainel(al, j);

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
        conv = new JPanel();
        conv.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 15, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("  Selecione a categoria pretendida :");
        conv.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bt5 = new JButton();
        bt5.setText("");
        conv.add(bt5, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bt6 = new JButton();
        bt6.setText("");
        conv.add(bt6, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SAIRButton = new JButton();
        SAIRButton.setText("SAIR");
        conv.add(SAIRButton, new com.intellij.uiDesigner.core.GridConstraints(3, 12, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(3, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bt1 = new JButton();
        bt1.setText("");
        conv.add(bt1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bt2 = new JButton();
        bt2.setText("");
        conv.add(bt2, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bt3 = new JButton();
        bt3.setText("");
        conv.add(bt3, new com.intellij.uiDesigner.core.GridConstraints(1, 6, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bt4 = new JButton();
        bt4.setText("");
        conv.add(bt4, new com.intellij.uiDesigner.core.GridConstraints(1, 9, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bt8 = new JButton();
        bt8.setText("");
        conv.add(bt8, new com.intellij.uiDesigner.core.GridConstraints(2, 9, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 13, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        conv.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(0, 14, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bt7 = new JButton();
        bt7.setText("");
        conv.add(bt7, new com.intellij.uiDesigner.core.GridConstraints(2, 6, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(3, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(3, 11, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        conv.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(1, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return conv;
    }


    public JButton getBt3() {
        return bt3;
    }

    public JButton getBt7() {
        return bt7;
    }

    public JButton getEstilo3() {
        return estilo3;
    }

    public JPanel getConv() {
        return conv;
    }
}
