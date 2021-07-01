package Main.ui;

import Main.business.Conteudo;
import Main.business.MC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlterarCateg extends JFrame {
    private JButton musA;
    private JButton vidA;
    private JComboBox comboCategoriaInicial;
    private JComboBox comboConteudo;
    private JComboBox comboCategoriaFinal;
    private JButton SAIRButton;
    private JPanel alt;
    private JCheckBox cbCatInicial;
    private JCheckBox cbConteudo;
    private JButton confirmarAlteracaoButton;
    private JCheckBox cbCatNovaExistente;
    private JCheckBox cbCatNova;
    private JTextField textField1;
    private JCheckBox cbNovaCatCheckBox;
    private JButton VIDEOButton;
    private MC mc;
    private int tipo;


    public AlterarCateg(MC mc) {
        this.mc = mc;


        textField1.setEditable(false);
        musA.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limpaCheck();

                comboCategoriaInicial.removeAllItems();
                tipo = 0;
                setCBNames(tipo);


            }
        });
        vidA.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limpaCheck();
                comboCategoriaInicial.removeAllItems();
                tipo = 1;
                setCBNames(tipo);

            }
        });
        comboCategoriaInicial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        cbCatInicial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                comboConteudo.removeAllItems();
                String qualC = ((String) comboCategoriaInicial.getSelectedItem());
                setNCont(tipo, qualC);
                textField1.setEditable(false);
            }
        });
        cbConteudo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        comboCategoriaFinal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        cbConteudo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                comboCategoriaFinal.removeAllItems();
                setCBNamesFinal(tipo);
            }
        });
        cbCatNovaExistente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        cbNovaCatCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textField1.setEditable(true);
            }
        });
        confirmarAlteracaoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                altCat(mc.getAut().getEmail());
                dispose();
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

    //Preenche ComboBox Inicial com as Categorias
    private void setCBNames(int tipo) {
        for (String s : (mc.getNomesTeste(tipo))) {
            comboCategoriaInicial.addItem(s);

        }
    }

    //Preenche ComboBox Final com as Categorias
    private void setCBNamesFinal(int tipo) {
        for (String s : (mc.getNomesTeste(tipo))) {
            comboCategoriaFinal.addItem(s);

        }
    }

    //Preenche ComboBox com os Conteúdos da Categoria
    private void setNCont(int tipo, String nCat) {
        Conteudo[] mp = mc.getConteudoUser(mc.getAut().getEmail(), nCat);
        for (int i = 0; i < mp.length; i++) {
            if (tipo == mp[i].getTipo() && nCat.equals(mp[i].getFlagCat())) {
                comboConteudo.addItem(mp[i].getNomeCont());
            }
        }
    }

    private void limpaCheck() {
        cbCatInicial.setSelected(false);
        cbConteudo.setSelected(false);
        cbCatNovaExistente.setSelected(false);
        cbNovaCatCheckBox.setSelected(false);
        comboConteudo.removeAllItems();
        comboCategoriaFinal.removeAllItems();
        comboCategoriaInicial.removeAllItems();
        comboCategoriaInicial.addItem("-");
        comboCategoriaFinal.addItem("-");
        comboConteudo.addItem("-");
    }

    public JPanel getAlt() {
        return alt;
    }

    private boolean altCat(String email) {
        String nomeConteudo = comboConteudo.getSelectedItem().toString();
        String categEsc;
        String catInicial = comboCategoriaInicial.getSelectedItem().toString();

        if (cbCatNovaExistente.isSelected()) {
            categEsc = comboCategoriaFinal.getSelectedItem().toString();
        } else {
            categEsc = textField1.getText();
        }
        if ((comboCategoriaInicial.getSelectedIndex() == -1 || !cbCatInicial.isSelected()) || (comboConteudo.getSelectedIndex() == -1 || !cbConteudo.isSelected())) {
            JOptionPane.showMessageDialog(null, "Verifique os campos que pretende alterar!",
                    "Erro", JOptionPane.ERROR_MESSAGE);

        } else if (textField1.getText().equals("") && cbNovaCatCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Verifique o campo da nova categoria!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (comboCategoriaFinal.getSelectedIndex() >= 1 && !cbCatNovaExistente.isSelected() && !cbNovaCatCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Verifique as checkbox's!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (cbCatNovaExistente.isSelected() && cbNovaCatCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Não pode adicionar duas categorias!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!mc.alterarCategoria(nomeConteudo, email, catInicial, categEsc, tipo)) {
            JOptionPane.showMessageDialog(null, "A categoria escolhida é igual à inicial!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!!",
                    "Suceesso", JOptionPane.PLAIN_MESSAGE);
            dispose();
        }
        return true;
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
        alt = new JPanel();
        alt.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 9, new Insets(0, 0, 0, 0), -1, -1));
        musA = new JButton();
        musA.setText("MUSICA");
        alt.add(musA, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        vidA = new JButton();
        vidA.setText("VIDEO");
        alt.add(vidA, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Selecione o tipo de conteúdo que pretende alterar");
        alt.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Selecione a categoria que está atualmente atribuida ao conteúdo:");
        alt.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboCategoriaInicial = new JComboBox();
        alt.add(comboCategoriaInicial, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Selecione o conteúdo que pretende alterar:");
        alt.add(label3, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboConteudo = new JComboBox();
        alt.add(comboConteudo, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Selecione a nova categoria:");
        alt.add(label4, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboCategoriaFinal = new JComboBox();
        alt.add(comboCategoriaFinal, new com.intellij.uiDesigner.core.GridConstraints(7, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        alt.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cbCatInicial = new JCheckBox();
        cbCatInicial.setText("Guardar");
        alt.add(cbCatInicial, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbConteudo = new JCheckBox();
        cbConteudo.setText("Guardar");
        alt.add(cbConteudo, new com.intellij.uiDesigner.core.GridConstraints(5, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        alt.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(5, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        alt.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(6, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Já existente:");
        alt.add(label5, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Adicionar nova");
        alt.add(label6, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbCatNovaExistente = new JCheckBox();
        cbCatNovaExistente.setText("Guardar");
        alt.add(cbCatNovaExistente, new com.intellij.uiDesigner.core.GridConstraints(7, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        alt.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(8, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cbNovaCatCheckBox = new JCheckBox();
        cbNovaCatCheckBox.setText("");
        alt.add(cbNovaCatCheckBox, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmarAlteracaoButton = new JButton();
        confirmarAlteracaoButton.setText("Confirmar ");
        alt.add(confirmarAlteracaoButton, new com.intellij.uiDesigner.core.GridConstraints(7, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SAIRButton = new JButton();
        SAIRButton.setText("SAIR");
        alt.add(SAIRButton, new com.intellij.uiDesigner.core.GridConstraints(8, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        alt.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(8, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return alt;
    }

}
