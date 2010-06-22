package ru.sgu.csit.selectioncommittee.gui;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.dialogs.*;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;

import static ru.sgu.csit.selectioncommittee.gui.utils.MessageUtil.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ru.sgu.csit.selectioncommittee.gui.utils.ResourcesForApplication.*;

/**
 * Date: 03.05.2010
 * Time: 13:22:34
 *
 * @author xx & hd
 */
public class MainFrame extends JFrame {
    private Action exportToExcelAction = new ExportToExcelAction();
    private Action exitAction = new ExitAction();
    private Action addAction = new AddAction();
    private Action editAction = new EditAction();
    private Action deleteAction = new DeleteAction();
    private Action aboutAction = new AboutAction();
    private Action infoAction = new InfoAction();
    private Action resizeTableAction = new ResizeTableAction();
//    private Action calcAllMatriculantsAction = new CalcAllMatriculantsAction();
    private Action highlightingAction = new HighlightingAction();
    private Action apportionMatriculantsAction = new ApportionMatriculantsAction();
    private Action sortAction = new SortAction();
    private Action refreshAction = new RefreshAction();

    private JComboBox specialityComboBox;

    private MatriculantTable mainTable = null;

    private MatriculantInfoDialog matriculantInfoDialog = new MatriculantInfoDialog(this);

    private JLabel matriculantSizeLabel = new JLabel();

    public MainFrame() {
        mainTable = new MatriculantTable();
        setTitle(tTITLE_OF_APPLICATION);
        setIconImage(iAPP16);
        setSize(800, 600);
        setJMenuBar(createJMenuBar());
        add(createJToolBar(), BorderLayout.NORTH);

        add(new JScrollPane(mainTable), BorderLayout.CENTER);

        add(createStatusBar(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //if (confirm(tCONFIRM_CLOSE_APP)) {
                System.exit(0);
                //}
            }
        });

        final JPopupMenu rowPopupMenu = createRowPopupMenu();

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    JTable table = (JTable)event.getSource();
                    Point point = event.getPoint();
                    int column = table.columnAtPoint(point);
                    int row = table.rowAtPoint(point);

                    if (column >= 0 && row >= 0) {
                        table.setColumnSelectionInterval(column, column);
                        table.setRowSelectionInterval(row, row);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    Point point = event.getPoint();
                    JTable table = (JTable)event.getSource();
                    rowPopupMenu.show(table, point.x, point.y);
                }
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    infoAction.actionPerformed(null);
                }
            }
        });

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
//        Dimension dialogSize = getSize();
//        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
//        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
//        setLocation(x, y);
        setSize(screenSize);

        refresh();
    }

    private JComponent createStatusBar() {
        StatusBar statusBar = new StatusBar();
        statusBar.addLabel(new JLabel("Количество абитуриентов:"));
        statusBar.addLabel(matriculantSizeLabel);
        return statusBar;
    }

    private JMenuBar createJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(tFILE_MENU);
        fileMenu.add(refreshAction);
        fileMenu.add(exportToExcelAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);

        JMenu editMenu = new JMenu(tEDIT_MENU);
        editMenu.add(addAction);
        editMenu.add(infoAction);
        editMenu.add(editAction);
        editMenu.addSeparator();
        editMenu.add(deleteAction);

        JMenu viewMenu = new JMenu(tVIEW_MENU);
        JCheckBoxMenuItem showNotEntranceMenuItem = new JCheckBoxMenuItem(new ShowMatriculantsAction(tSHOWNOTENT_ITEM));
        showNotEntranceMenuItem.setSelected(MatriculantTable.isShowNotEntrance());
        viewMenu.add(showNotEntranceMenuItem);
        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
            JCheckBoxMenuItem showEntranceMenuItem = new JCheckBoxMenuItem(new ShowMatriculantsAction(speciality.getName()));
            showEntranceMenuItem.setSelected(MatriculantTable.isShowEntrance(speciality.getName()));
            viewMenu.add(showEntranceMenuItem);
        }
        viewMenu.addSeparator();
        JCheckBoxMenuItem lightMenuItem = new JCheckBoxMenuItem(highlightingAction);
        lightMenuItem.setSelected(MatriculantTable.isHighlighting());
        viewMenu.add(lightMenuItem);
        JCheckBoxMenuItem resizeMenuItem = new JCheckBoxMenuItem(resizeTableAction);
        resizeMenuItem.setSelected(mainTable.getAutoResizeMode() == JTable.AUTO_RESIZE_ALL_COLUMNS);
        viewMenu.add(resizeMenuItem);

        JMenu apportionMenu = new JMenu(tAPPORTION_MENU);
        apportionMenu.add(sortAction);
//        apportionMenu.add(calcAllMatriculantsAction);
//        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
//            apportionMenu.add(new CalcForSpecialityAction(speciality.getName()));
//        }
        apportionMenu.addSeparator();
        apportionMenu.add(apportionMatriculantsAction);

        JMenu helpMenu = new JMenu(tHELP_MENU);
        helpMenu.add(aboutAction);

        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(apportionMenu);
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
        jToolBar.add(refreshAction);
        jToolBar.addSeparator();
        jToolBar.add(exportToExcelAction);
        jToolBar.addSeparator();
        jToolBar.addSeparator();

        jToolBar.add(createSpecialityPanel());
        jToolBar.addSeparator();

        jToolBar.setFloatable(false);
        return jToolBar;
    }

    private JPanel createSpecialityPanel() {
        JPanel specialityPanel = new JPanel(new GridBagLayout());
        specialityPanel.add(new JLabel("Ранжировать:"), new GBConstraints(0, 0));

        Object[] items = new Object[1 + DataAccessFactory.getSpecialities().size()];
        items[0] = tCALCALL;
        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            items[i + 1] = tCALCFOR_PREF + DataAccessFactory.getSpecialities().get(i).getName();
        }
        specialityComboBox = new JComboBox(items);
        specialityComboBox.addActionListener(new CalcMatriculantsAction());

        specialityPanel.add(specialityComboBox, new GBConstraints(1, 0, true));

        specialityPanel.setMaximumSize(specialityPanel.getPreferredSize());
//        specialityPanel.setMaximumSize(new Dimension(250, specialityComboBox.getPreferredSize().height));

        return specialityPanel;
    }

    public void refresh() {
        mainTable.refresh();
        matriculantSizeLabel.setText("" + DataAccessFactory.getMatriculants().size());
    }

    private class ExportToExcelAction extends AbstractAction {
        private JDialog exportDialog;

        private ExportToExcelAction() {
            putValue(Action.NAME, tEXPORT_TO_EXCEL);
            putValue(Action.SMALL_ICON, iEXCEL16);
            putValue(Action.SHORT_DESCRIPTION, tEXPORT_TO_EXCEL_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
        }

        public void actionPerformed(ActionEvent e) {
            if (exportDialog == null) {
                exportDialog = new ExportToExcelDialog(MainFrame.this);
            }
            exportDialog.setVisible(true);
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
                        .get(mainTable.convertViewRowIndexToMatriculants(selectedIndex));
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
                            .get(mainTable.convertViewRowIndexToMatriculants(selectedIndex));

                    DataAccessFactory.getMatriculants().remove(mainTable.convertViewRowIndexToMatriculants(selectedIndex));
                    MatriculantTable.deleteFromViewIndex(selectedIndex);
                    DataAccessFactory.getMatriculantDAO().delete(matriculant);
//                    DataAccessFactory.reloadMatriculants();
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
            putValue(Action.SMALL_ICON, iINFO16);
            putValue(Action.SHORT_DESCRIPTION, tINFO_DESCRIPTION);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
        }

        public void actionPerformed(ActionEvent e) {
            int selectedIndex = mainTable.getSelectedRow();
            if (selectedIndex >= 0) {
                Matriculant matriculant = DataAccessFactory.getMatriculants()
                        .get(mainTable.convertViewRowIndexToMatriculants(selectedIndex));
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

//    private class CalcForSpecialityAction extends AbstractAction {
//        private String specialityName;
//
//        private CalcForSpecialityAction(String name) {
//            specialityName = name;
//            putValue(Action.NAME, tCALCFOR_PREF + name);
//            putValue(Action.SHORT_DESCRIPTION, tCALCFORSPECIALITY_DESCRIPTION);
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            JMenuItem showMenuItem = (JMenuItem) e.getSource();
//
//            for (Speciality speciality : DataAccessFactory.getSpecialities()) {
//                if (speciality.getName().equals(specialityName)) {
//                    mainTable.sortBy(speciality);
//                    mainTable.repaint();
//
//                    return;
//                }
//            }
//        }
//    }

//    private class CalcAllMatriculantsAction extends AbstractAction {
//        private CalcAllMatriculantsAction() {
//            putValue(Action.NAME, tCALCALL);
//            putValue(Action.SHORT_DESCRIPTION, tCALCALL_DESCRIPTION);
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            JMenuItem columnMenuItem = (JMenuItem) e.getSource();
//
//            MatriculantTable.resetRowIndexes();
//            mainTable.repaint();
//        }
//    }

    private class CalcMatriculantsAction extends AbstractAction {
        private CalcMatriculantsAction() {
            putValue(Action.NAME, "Список отображения");
            putValue(Action.SHORT_DESCRIPTION, tCALCFORSPECIALITY_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            String itemName = (String) specialityComboBox.getSelectedItem();

            if (itemName.equals(tCALCALL)) {
                MatriculantTable.resetRowIndexes();
                mainTable.repaint();
            } else {
                for (Speciality speciality : DataAccessFactory.getSpecialities()) {
                    if (itemName.equals(tCALCFOR_PREF + speciality.getName())) {
                        mainTable.sortBy(speciality);
                        mainTable.repaint();

                        return;
                    }
                }
            }
        }
    }

    private class ApportionMatriculantsAction extends AbstractAction {
        private ApportionMatriculantsAction() {
            putValue(Action.NAME, tAPPORTION_SPEC);
            putValue(Action.SHORT_DESCRIPTION, tAPPORTION_SPEC_DESCRIPTION);
            putValue(Action.SMALL_ICON, iAPPORTION16);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F10"));
        }

        public void actionPerformed(ActionEvent e) {
            ApportionMatriculantsDialog apportionMatriculantsDialog =
                    new ApportionMatriculantsDialog(MainFrame.this, mainTable);
            apportionMatriculantsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            apportionMatriculantsDialog.setVisible(true);
        }
    }

    private class ShowMatriculantsAction extends AbstractAction {
        private String specialityName;

        private ShowMatriculantsAction(String name) {
            specialityName = name;
            if (tSHOWNOTENT_ITEM.equals(name)) {
                putValue(Action.NAME, name);
            } else {
                putValue(Action.NAME, tSHOWENT_PREF + name);
            }
            putValue(Action.SHORT_DESCRIPTION, tSHOWENT_DESCRIPTION);
        }

        public void actionPerformed(ActionEvent e) {
            JCheckBoxMenuItem showMenuItem = (JCheckBoxMenuItem) e.getSource();

            if (tSHOWNOTENT_ITEM.equals(specialityName)) {
                MatriculantTable.setShowNotEntrance(showMenuItem.isSelected());
            } else {
                MatriculantTable.setShowEntrance(specialityName, showMenuItem.isSelected());
            }
            MatriculantTable.recalculateViewRows();
            mainTable.refresh();
            mainTable.repaint();
        }
    }

    private class SortAction extends AbstractAction {
        private SortAction() {
            putValue(Action.NAME, "Сортировка");
            putValue(Action.SHORT_DESCRIPTION, "Выбрать столбцы для сортировки");
            putValue(Action.SMALL_ICON, iSORT16);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F9"));
        }

        public void actionPerformed(ActionEvent e) {
            SortDialog sortDialog = new SortDialog(MainFrame.this, mainTable);
            sortDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            sortDialog.setVisible(true);
        }
    }

    private class RefreshAction extends AbstractAction {
        private RefreshAction() {
            putValue(Action.NAME, "Обновить");
            putValue(Action.SHORT_DESCRIPTION, "Обновить");
            putValue(Action.SMALL_ICON, iREFRESH16);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
        }

        public void actionPerformed(ActionEvent e) {
            DataAccessFactory.reloadAll();
            refresh();
        }
    }
}
