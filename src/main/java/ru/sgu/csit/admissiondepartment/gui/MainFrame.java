package ru.sgu.csit.admissiondepartment.gui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.actions.*;
import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.*;

/**
 * Date: 03.05.2010
 * Time: 13:22:34
 *
 * @author xx & hd
 */
public class MainFrame extends JFrame {

    @Autowired
    private MatriculantTable mainTable;

    private Action exportToExcelAction;
    private Action exitAction;
    private Action addMatriculantAction;
    private Action editMatriculantAction;
    private Action matriculantInfoAction;
    private Action deleteMatriculantAction;
    private Action aboutAction;
    private Action resizeTableAction;
    private Action switchHighlightingTableAction;
    private Action calculateMatriculantsAction;
    private Action apportionMatriculantsAction;
    private Action sortAction;
    private Action reloadAction;
    private Action logInAction;

    private JComboBox<String> specialityComboBox;

    private JLabel matriculantSizeLabel = new JLabel();

    public MainFrame() {
        setTitle(tTITLE_OF_APPLICATION);
        setIconImage(iAPP16);
        setSize(800, 600);

        add(createStatusBar(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });

        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @PostConstruct
    public void initialize() {
        setJMenuBar(createJMenuBar());
        add(createJToolBar(), BorderLayout.NORTH);

        add(new JScrollPane(mainTable), BorderLayout.CENTER);

        final JPopupMenu rowPopupMenu = createRowPopupMenu();

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    JTable table = (JTable) event.getSource();
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
                    JTable table = (JTable) event.getSource();
                    rowPopupMenu.show(table, point.x, point.y);
                }
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    matriculantInfoAction.actionPerformed(null);
                }
            }
        });
    }

    public void setExportToExcelAction(Action exportToExcelAction) {
        this.exportToExcelAction = exportToExcelAction;
    }

    public void setExitAction(Action exitAction) {
        this.exitAction = exitAction;
    }

    public void setAddMatriculantAction(Action addMatriculantAction) {
        this.addMatriculantAction = addMatriculantAction;
    }

    public void setEditMatriculantAction(Action editMatriculantAction) {
        this.editMatriculantAction = editMatriculantAction;
    }

    public void setMatriculantInfoAction(Action matriculantInfoAction) {
        this.matriculantInfoAction = matriculantInfoAction;
    }

    public void setDeleteMatriculantAction(Action deleteMatriculantAction) {
        this.deleteMatriculantAction = deleteMatriculantAction;
    }

    public void setAboutAction(Action aboutAction) {
        this.aboutAction = aboutAction;
    }

    public void setResizeTableAction(Action resizeTableAction) {
        this.resizeTableAction = resizeTableAction;
    }

    public void setSwitchHighlightingTableAction(Action switchHighlightingTableAction) {
        this.switchHighlightingTableAction = switchHighlightingTableAction;
    }

    public void setCalculateMatriculantsAction(Action calculateMatriculantsAction) {
        this.calculateMatriculantsAction = calculateMatriculantsAction;
    }

    public void setApportionMatriculantsAction(Action apportionMatriculantsAction) {
        this.apportionMatriculantsAction = apportionMatriculantsAction;
    }

    public void setSortAction(Action sortAction) {
        this.sortAction = sortAction;
    }

    public void setReloadAction(Action reloadAction) {
        this.reloadAction = reloadAction;
    }

    public void setLogInAction(Action logInAction) {
        this.logInAction = logInAction;
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
        fileMenu.add(reloadAction);
        fileMenu.add(exportToExcelAction);
        fileMenu.addSeparator();
        fileMenu.add(logInAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);

        JMenu editMenu = new JMenu(tEDIT_MENU);
        editMenu.add(addMatriculantAction);
        editMenu.add(matriculantInfoAction);
        editMenu.add(editMatriculantAction);
        editMenu.addSeparator();
        editMenu.add(deleteMatriculantAction);

        JMenu viewMenu = new JMenu(tVIEW_MENU);
        JCheckBoxMenuItem showNotEntranceMenuItem = new JCheckBoxMenuItem(
                new ShowMatriculantsAction(mainTable, tSHOWNOTENT_ITEM));
        showNotEntranceMenuItem.setSelected(MatriculantTable.isShowNotEntrance());
        viewMenu.add(showNotEntranceMenuItem);
        for (Speciality speciality : DataAccessFactory.getSpecialities()) {
            JCheckBoxMenuItem showEntranceMenuItem = new JCheckBoxMenuItem(
                    new ShowMatriculantsAction(mainTable, speciality.getName()));
            showEntranceMenuItem.setSelected(MatriculantTable.isShowEntrance(speciality.getName()));
            viewMenu.add(showEntranceMenuItem);
        }
        viewMenu.addSeparator();
        JCheckBoxMenuItem lightMenuItem = new JCheckBoxMenuItem(switchHighlightingTableAction);
        lightMenuItem.setSelected(MatriculantTable.isHighlighting());
        viewMenu.add(lightMenuItem);
        JCheckBoxMenuItem resizeMenuItem = new JCheckBoxMenuItem(resizeTableAction);
        resizeMenuItem.setSelected(mainTable.getAutoResizeMode() == JTable.AUTO_RESIZE_ALL_COLUMNS);
        viewMenu.add(resizeMenuItem);

        JMenu apportionMenu = new JMenu(tAPPORTION_MENU);
        apportionMenu.add(sortAction);
        
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

        jPopupMenu.add(matriculantInfoAction);
        jPopupMenu.add(editMatriculantAction);

        return jPopupMenu;
    }

    private JToolBar createJToolBar() {
        JToolBar jToolBar = new JToolBar();
        jToolBar.add(addMatriculantAction);
        jToolBar.add(matriculantInfoAction);
        jToolBar.add(editMatriculantAction);
        jToolBar.addSeparator();
        jToolBar.add(reloadAction);
        jToolBar.addSeparator();
        jToolBar.add(exportToExcelAction);
        jToolBar.addSeparator();
        jToolBar.addSeparator();

        jToolBar.add(createSpecialityPanel());
        jToolBar.addSeparator();

        jToolBar.addSeparator();
        jToolBar.add(createSearchPanel());
        jToolBar.addSeparator();

        jToolBar.setFloatable(false);
        return jToolBar;
    }

    private JPanel createSpecialityPanel() {
        JPanel specialityPanel = new JPanel(new GridBagLayout());
        specialityPanel.add(new JLabel("Ранжировать:"), new GBConstraints(0, 0));

        specialityComboBox = new JComboBox<String>();
        reloadDataSpecialityComboBox();
        specialityComboBox.addActionListener(calculateMatriculantsAction);

        specialityPanel.add(specialityComboBox, new GBConstraints(1, 0, true));

        specialityPanel.setMaximumSize(specialityPanel.getPreferredSize());

        return specialityPanel;
    }

    private void reloadDataSpecialityComboBox() {
        String[] items = new String[1 + DataAccessFactory.getSpecialities().size()];
        items[0] = tCALCALL;
        for (int i = 0; i < DataAccessFactory.getSpecialities().size(); ++i) {
            items[i + 1] = tCALCFOR_PREF + DataAccessFactory.getSpecialities().get(i).getName();
        }
        specialityComboBox.setModel(new DefaultComboBoxModel<String>(items));
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.add(new JLabel("Поиск:"), new GBConstraints(0, 0));

        final JTextField searchPhraseTextField = new JTextField(30);
        searchPhraseTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchPhrase = searchPhraseTextField.getText().toLowerCase();
                    int beginRow = Math.max(mainTable.getSelectedRow(), 0);
                    int beginColumn = Math.max(mainTable.getSelectedColumn() + 1, 0);
                    int rowSize = mainTable.getRowCount();
                    int columnSize = mainTable.getColumnCount();

                    boolean search;
                    do {
                        for (int row = beginRow; row < rowSize; ++row) {
                            for (int column = beginColumn; column < columnSize; ++column) {
                                if (mainTable.getValueAt(row, column) != null) {
                                    String cellValue = mainTable.getValueAt(row, column).toString();
                                    if (cellValue.toLowerCase().contains(searchPhrase)) {
                                        mainTable.changeSelection(row, column, false, false);
                                        searchPhraseTextField.selectAll();
                                        return;
                                    }
                                }
                            }
                            beginColumn = 0;
                        }
                        search = showConfirmDialog("Ничего не найдено. Хотите продолжить поиск с начала таблицы?");
                        beginRow = 0;
                        beginColumn = 0;
                    } while (search);
                }
            }
        });

        searchPanel.add(searchPhraseTextField, new GBConstraints(1, 0, true));

        searchPanel.setMaximumSize(searchPanel.getPreferredSize());

        return searchPanel;
    }

    public void refresh() {
        mainTable.refresh();
        matriculantSizeLabel.setText("" + DataAccessFactory.getMatriculants().size());
    }

    public void reloadAllData() {
        DataAccessFactory.reloadAll();
        mainTable.reload();
        MatriculantTable.resetRowIndexes();
        specialityComboBox.setSelectedIndex(0);
        setJMenuBar(createJMenuBar());
        reloadDataSpecialityComboBox();
        if (isVisible()) {
            setVisible(true);
        }
        refresh();
    }

    public void resetPositionToLastRow() {
        int selectedRow = mainTable.getRowCount() - 1;

        specialityComboBox.setSelectedIndex(0);
        if (selectedRow >= 0 && mainTable.getColumnCount() > 0) {
            mainTable.changeSelection(selectedRow, 0, false, false);
        }
    }
}       
