package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Date: Jun 11, 2010
 * Time: 1:32:30 PM
 *
 * @author xx & hd
 */
public class MatriculantInfoDialog extends JDialog {
    private JTextArea textArea = new JTextArea();

    public MatriculantInfoDialog(JFrame owner) {
        super(owner, "Информация об абитуриенте", false);
        setLayout(new GridBagLayout());

        add(getContentPanel(), new GBConstraints(0, 0).setFill(GBConstraints.BOTH).setWeight(100, 100).setInsets(10));

        Action closeAction = new CloseAction("Закрыть");
        JPanel content = (JPanel) getContentPane();
        content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
        content.getActionMap().put("CLOSE_DIALOG", closeAction);
    }

    public void showInfo(String info) {
        textArea.setText(info);

        pack();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension dialogSize = getSize();
        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
        setLocation(x, y);

        setVisible(true);
    }

    private JComponent getContentPanel() {
        JPanel matriculantInfoPanel = new JPanel(new GridBagLayout());
        textArea.setFont(textArea.getFont().deriveFont(14f));
        textArea.setEditable(false);
        matriculantInfoPanel.add(textArea,
                new GBConstraints(0, 0).setFill(GBConstraints.BOTH).setWeight(100, 100).setInsets(5));
        matriculantInfoPanel.setAutoscrolls(true);
        matriculantInfoPanel.setBorder(BorderFactory.createEtchedBorder());
        return new JScrollPane(matriculantInfoPanel);
    }

    private class CloseAction extends AbstractAction {
        private CloseAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MatriculantInfoDialog.this.setVisible(false);
        }
    }
}
