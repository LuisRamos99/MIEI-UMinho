package Main.ui;

import Main.business.MC;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.desktop.OpenFilesEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.StringTokenizer;

import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class UploadWindow extends JFrame {
    private JButton button1;
    private JPanel up;
    private JComboBox comboBox1;
    private JTextField NOMECONTEUDOTextField;
    private JButton escolherButton;
    private JButton MUSICAButton;
    private JButton VIDEOButton;
    private JTextField outraCat;
    private JButton adicionarButton;
    private JButton button3;
    private JComboBox comboBox2;
    private JCheckBox checkBox1;
    private JCheckBox adicionarNovaCheckBox;
    private JCheckBox checkBox2;
    private String filename;
    private final MC mc;

    public UploadWindow(MC mc) {
        this.mc = mc;
        outraCat.setEditable(false);
        comboBox2.addItem("-");
        comboBox2.addItem("Música");
        comboBox2.addItem("Vídeo");
        comboBox1.addItem("-");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        escolherButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                filename = urlParaPlayer();
            }
        });

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tryUp(filename);
            }
        });
        comboBox2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });


        comboBox1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        checkBox1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if ((comboBox2.getSelectedItem()).equals("Música")) {
                    comboBox1.removeAllItems();
                    setCBNames(0);
                } else if ((comboBox2.getSelectedItem()).equals("Vídeo")) {
                    comboBox1.removeAllItems();
                    checkBox1.setSelected(false);
                    setCBNames(1);
                }
            }
        });
        adicionarNovaCheckBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                outraCat.setEditable(true);
            }
        });
        checkBox2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }

    //Método necessário para inserir no SQL as barras da diretoria
    public String criaLink(String oldLink) {
        int i = 0;
        StringBuilder novo = new StringBuilder();
        String[] parts = oldLink.split("\\\\");
        novo.append(parts[i++]);
        while (i < parts.length) {
            novo.append("\\\\");
            novo.append(parts[i++]);
        }
        return novo.toString();
    }

    private String urlParaPlayer() {
        File f = new File("C:\\Users\\josec\\Desktop");
        JFileChooser j = new JFileChooser(f, FileSystemView.getFileSystemView());
        j.showSaveDialog(null);
        String name = j.getSelectedFile().toString();
        return criaLink(name);
    }


    private void setCBNames(int tipo) {
        for (String s : (mc.getNomesTeste(tipo))) {
            comboBox1.addItem(s);
        }
        comboBox1.addItem("-");
    }

    private boolean tryUp(String filename) {
        String nomeConteudo = NOMECONTEUDOTextField.getText();
        String categEsc;
        int tipo = 0;
        if (checkBox2.isSelected()) {
            categEsc = comboBox1.getSelectedItem().toString();
        } else {
            categEsc = outraCat.getText();
        }

        if ((comboBox2.getSelectedItem()).equals("Música")) {
            tipo = 0;
        } else if ((comboBox2.getSelectedItem()).equals("Vídeo")) {
            tipo = 1;
        }

        if (nomeConteudo.equals("") || categEsc.equals("") || filename.equals("") || (comboBox1.getSelectedIndex() == -1)
                || (comboBox2.getSelectedIndex() == -1)) {
            JOptionPane.showMessageDialog(null, "Dados introduzidos estão errados/incompletos!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (comboBox2.getSelectedIndex() >= 1 && checkBox2.isSelected() && adicionarNovaCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Não pode selecionar duas categorias!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!(mc.adicionarConteudo(nomeConteudo, filename, categEsc, tipo))) {
            JOptionPane.showMessageDialog(null, "O conteúdo já se encontra no sistema com um ficheiro diferente!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Upload efetuado com sucesso!!",
                    "Suceesso", JOptionPane.PLAIN_MESSAGE);
            dispose();
        }
        return true;
    }


    public JPanel getUp() {
        return up;
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
        up = new JPanel();
        up.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 9, new Insets(0, 0, 0, 0), -1, -1));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        up.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 7, 7, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Nome do conteudo");
        up.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Introduza os dados pedidos*:");
        up.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("SAIR");
        up.add(button1, new com.intellij.uiDesigner.core.GridConstraints(9, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        NOMECONTEUDOTextField = new JTextField();
        NOMECONTEUDOTextField.setText("");
        up.add(NOMECONTEUDOTextField, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Escolha o ficheiro");
        up.add(label3, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        escolherButton = new JButton();
        escolherButton.setText("Escolher");
        up.add(escolherButton, new com.intellij.uiDesigner.core.GridConstraints(9, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Já existente");
        up.add(label4, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        up.add(comboBox1, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        up.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(6, 5, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        up.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(9, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Selecione a categoria");
        up.add(label5, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        up.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        outraCat = new JTextField();
        outraCat.setText("");
        up.add(outraCat, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        up.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(9, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        button3 = new JButton();
        button3.setText("Adicionar");
        up.add(button3, new com.intellij.uiDesigner.core.GridConstraints(8, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Selecione o tipo");
        up.add(label6, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBox2.setModel(defaultComboBoxModel1);
        up.add(comboBox2, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox2 = new JCheckBox();
        checkBox2.setText("");
        up.add(checkBox2, new com.intellij.uiDesigner.core.GridConstraints(3, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBox1 = new JCheckBox();
        checkBox1.setText("");
        up.add(checkBox1, new com.intellij.uiDesigner.core.GridConstraints(2, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Adicionar nova?");
        up.add(label7, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        adicionarNovaCheckBox = new JCheckBox();
        adicionarNovaCheckBox.setText("");
        up.add(adicionarNovaCheckBox, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("*Não se esqueça de selecionar as CheckBox's!");
        up.add(label8, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return up;
    }

}
