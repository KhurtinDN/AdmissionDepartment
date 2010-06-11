package ru.sgu.csit.selectioncommittee.gui;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.dialogs.AboutDialog;
import ru.sgu.csit.selectioncommittee.gui.dialogs.ExportToExcelDialog;
import ru.sgu.csit.selectioncommittee.gui.dialogs.MatriculantDialog;
import ru.sgu.csit.selectioncommittee.gui.dialogs.MatriculantInfoDialog;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: 03.05.2010
 * Time: 13:22:34
 *
 * @author xx & hd
 */
public class MainFrame extends JFrame {
    Action exportToExcelAction = new ExportToExcelAction();
    Action printAction = new PrintAction();
    Action exitAction = new ExitAction();
    Action addAction = new AddAction();
    Action editAction = new EditAction();
    Action deleteAction = new DeleteAction();
    Action aboutAction = new AboutAction();
    Action infoAction = new InfoAction();
    Action resizeTableAction = new ResizeTableAction();
    Action highlightingAction = new HighlightingAction();

    MatriculantTable mainTable = null;

    MatriculantInfoDialog matriculantInfoDialog = new MatriculantInfoDialog(this);

    public MainFrame() {
        mainTable = new MatriculantTable();
        setTitle(tTITLE_OF_APPLICATION);
        setIconImage(iAPP16);
        setSize(800, 600);
        setJMenuBar(createJMenuBar());
        add(createJToolBar(), BorderLayout.NORTH);

        mainTable.setComponentPopupMenu(createRowPopupMenu());
        add(new JScrollPane(mainTable), BorderLayout.CENTER);

        add(createButtonPanel(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //if (confirm(tCONFIRM_CLOSE_APP)) {
                System.exit(0);
                //}
            }
        });

        mainTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    infoAction.actionPerformed(null);
                }
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());
        buttonPanel.add(new JButton(addAction));
        buttonPanel.add(new JButton(infoAction));
        buttonPanel.add(new JButton(editAction));
        buttonPanel.add(new JButton(deleteAction));
        return buttonPanel;
    }

    private JMenuBar createJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(tFILE_MENU);
        fileMenu.add(exportToExcelAction);
        fileMenu.add(printAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);

        JMenu editMenu = new JMenu(tEDIT_MENU);
        editMenu.add(addAction);
        editMenu.add(infoAction);
        editMenu.add(editAction);
        editMenu.addSeparator();
        editMenu.add(deleteAction);

        JMenu viewMenu = new JMenu(tVIEW_MENU);
        for (MatriculantTable.ColumnInfo column : mainTable.getColumns()) {
            JCheckBoxMenuItem columnMenuItem = new JCheckBoxMenuItem(new ShowColumnAction(column.getColumnName()));

            columnMenuItem.setSelected(column.isVisible());
            viewMenu.add(columnMenuItem);
        }
        viewMenu.addSeparator();
        JCheckBoxMenuItem lightMenuItem = new JCheckBoxMenuItem(highlightingAction);
        lightMenuItem.setSelected(MatriculantTable.isHighlighting());
        viewMenu.add(lightMenuItem);
        JCheckBoxMenuItem resizeMenuItem = new JCheckBoxMenuItem(resizeTableAction);
        resizeMenuItem.setSelected(mainTable.getAutoResizeMode() == JTable.AUTO_RESIZE_ALL_COLUMNS);
        viewMenu.add(resizeMenuItem);

        JMenu helpMenu = new JMenu(tHELP_MENU);
        helpMenu.add(aboutAction);

        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);
        return jMenuBar;
    }

    private JPopupMenu createRowPopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        jPopupMenu.add(infoAction);
        jPopupMenu.add(editAction);
//        jPopupMenu.add(deleteAction);

        return jPopupMenu;
    }

    private JToolBar createJToolBar() {
        JToolBar jToolBar = new JToolBar();
        jToolBar.add(addAction);
        jToolBar.add(infoAction);
        jToolBar.add(editAction);
        jToolBar.addSeparator();
        jToolBar.add(printAction);
//        jToolBar.add(exitAction);
        jToolBar.setFloatable(false);
        return jToolBar;
    }

    public void refresh() {
        mainTable.refresh();
    }

    private class ExportToExcelAction extends AbstractAction {
        private JDialog exportDialog;

        private ExportToExcelAction() {
            putValue(Action.NAME, tEXPORT_TO_EXCEL);
            putValue(Action.SHORT_DESCRIPTION, tEXPORT_TO_EXCEL_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
        }

        public void actionPerformed(ActionEvent e) {
            if (exportDialog == null) {
                exportDialog = new ExportToExcelDialog(MainFrame.this, mainTable);
            }
            exportDialog.setVisible(true);
        }
    }

    private class PrintAction extends AbstractAction {
        private PrintAction() {
            putValue(Action.NAME, tPRINT);
            putValue(Action.SMALL_ICON, iPRINT16);
            putValue(Action.SHORT_DESCRIPTION, tPRINT_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
        }

        public void actionPerformed(ActionEvent e) {
            try {
                mainTable.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class AddAction extends AbstractAction {
        private AddAction() {
            putValue(Action.NAME, tADD);
            putValue(Action.SMALL_ICON, iADD16);
            putValue(Action.SHORT_DESCRIPTION, tADD_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F7"));
        }

        public void actionPerformed(ActionEvent e) {
            MatriculantDialog matriculantDialog = new MatriculantDialog(MainFrame.this, true, new Matriculant());
            matriculantDialog.setVisible(true);
        }
    }

    private class EditAction extends AbstractAction {
        private EditAction() {
            putValue(Action.NAME, tEDIT);
            putValue(Action.SMALL_ICON, iEDIT16);
            putValue(Action.SHORT_DESCRIPTION, tEDIT_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F4"));
        }

        public void actionPerformed(ActionEvent e) {
            int selectedIndex = mainTable.getSelectedRow();
            if (selectedIndex >= 0) {
                Matriculant matriculant = DataAccessFactory.getMatriculants()
                        .get(mainTable.convertRowIndexToModel(selectedIndex));
                MatriculantDialog matriculantDialog = new MatriculantDialog(MainFrame.this, false, matriculant);
                matriculantDialog.setVisible(true);
            } else {
                showWarningMessage("Выберите сначала абитуриента");
            }
        }
    }

    private class DeleteAction extends AbstractAction {
        private DeleteAction() {
            putValue(Action.NAME, tDELETE);
            putValue(Action.SMALL_ICON, iDELETE16);
            putValue(Action.SHORT_DESCRIPTION, tDELETE_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F8"));
        }

        public void actionPerformed(ActionEvent e) {
            int selectedIndex = mainTable.getSelectedRow();
            if (selectedIndex >= 0) {
                if (showConfirmDialog("Удалить абитуриента?")) {
                    Matriculant matriculant = DataAccessFactory.getMatriculants()
                            .get(mainTable.convertRowIndexToModel(selectedIndex));
                    DataAccessFactory.getMatriculantDAO().delete(matriculant);
                    DataAccessFactory.reloadMatriculants();
                    mainTable.refresh();
                }
            } else {
                showWarningMessage("Выберите сначала абитуриента");
            }
        }
    }

    private class ExitAction extends AbstractAction {
        private ExitAction() {
            putValue(Action.NAME, tEXIT);
            putValue(Action.SMALL_ICON, iEXIT16);
            putValue(Action.SHORT_DESCRIPTION, tEXIT_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F12"));
        }

        public void actionPerformed(ActionEvent e) {
            //if (confirm(tCONFIRM_CLOSE_APP)) {
            System.exit(0);
            //}
        }
    }

    private class AboutAction extends AbstractAction {
        private AboutDialog dialog = null;

        private AboutAction() {
            putValue(Action.NAME, tABOUT);
            putValue(Action.SMALL_ICON, iABOUT);
            putValue(Action.SHORT_DESCRIPTION, tABOUT_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
        }

        public void actionPerformed(ActionEvent e) {
            if (dialog == null) {
                dialog = new AboutDialog(MainFrame.this);
            }
            dialog.setVisible(true);
        }
    }

    private class InfoAction extends AbstractAction {
        private InfoAction() {
            putValue(Action.NAME, tINFO);
            putValue(Action.SMALL_ICON, iABOUT);
            putValue(Action.SHORT_DESCRIPTION, tINFO_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
        }

        public void actionPerformed(ActionEvent e) {
            int selectedIndex = mainTable.getSelectedRow();
            if (selectedIndex >= 0) {
                Matriculant matriculant = DataAccessFactory.getMatriculants()
                        .get(mainTable.convertRowIndexToModel(selectedIndex));
                matriculantInfoDialog.showInfo(matriculant.printToString());
            } else {
                showWarningMessage("Выберите сначала абитуриента");
            }
        }
    }

    private class ResizeTableAction extends AbstractAction {
        private ResizeTableAction() {
            putValue(Action.NAME, tAUTORESIZE);
            putValue(Action.SHORT_DESCRIPTION, tAUTORESIZE_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl R"));
        }

        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem resizeMenuItem = (JCheckBoxMenuItem) e.getSource();

            if (resizeMenuItem.isSelected()) {
                mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            } else {
                mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }
        }
    }

    private class HighlightingAction extends AbstractAction {
        private HighlightingAction() {
            putValue(Action.NAME, tHIGHLIGHTING);
            putValue(Action.SHORT_DESCRIPTION, tHIGHLIGHTING_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
        }

        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem lightMenuItem = (JCheckBoxMenuItem) e.getSource();

            MatriculantTable.setHighlighting(lightMenuItem.isSelected());
            mainTable.repaint();
        }
    }

    private class ShowColumnAction extends AbstractAction {
        private ShowColumnAction(String name) {
            putValue(Action.NAME, name);
            putValue(Action.SHORT_DESCRIPTION, tSHOWCOLUMN_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem columnMenuItem = (JCheckBoxMenuItem) e.getSource();

            for (int i = 0; i < mainTable.getColumns().size(); ++i) {
                MatriculantTable.ColumnInfo column = mainTable.getColumns().get(i);

                if (column.getColumnName().equals(columnMenuItem.getText())) {
                    if (columnMenuItem.isSelected()) {
                        mainTable.addColumn(i);
                    } else {
                        mainTable.removeColumn(i);
                    }
                    mainTable.repaint();

                    return;
                }
            }
        }
    }
}
