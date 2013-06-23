package ru.sgu.csit.admissiondepartment.gui.dialogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sgu.csit.admissiondepartment.common.Documents;
import ru.sgu.csit.admissiondepartment.common.EntranceCategory;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.common.Person;
import ru.sgu.csit.admissiondepartment.dao.MatriculantDAO;
import ru.sgu.csit.admissiondepartment.factory.DataAccessFactory;
import ru.sgu.csit.admissiondepartment.gui.MainFrame;
import ru.sgu.csit.admissiondepartment.gui.MatriculantTable;
import ru.sgu.csit.admissiondepartment.gui.actions.CloseAction;
import ru.sgu.csit.admissiondepartment.gui.dialogs.panels.MarkPanel;
import ru.sgu.csit.admissiondepartment.gui.dialogs.panels.SpecialityPanel;
import ru.sgu.csit.admissiondepartment.gui.utils.GBConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showWarningMessage;

/**
 * Date: May 4, 2010
 * Time: 10:34:02 PM
 *
 * @author xx & hd
 */
@Component
public class MatriculantDialog extends JDialog {

    private Action closeAction = new CloseAction(this);

    private ActionListener takeAwayActionListener = new TakeAwayActionListener();

    private Matriculant matriculant;

    // topPanel
    private JTextField receiptNumberField = new JTextField(10);
    private JSpinner todaySpinner = new JSpinner();
    private JSpinner timeSpinner = new JSpinner();

    // matriculantPanel
    private JTextField matriculantNameField = new JTextField();
    private JTextField matriculantPhoneNumberField = new JTextField();

    // fatherPanel
    private JTextField fatherNameField = new JTextField();
    private JTextField fatherPhoneNumberField = new JTextField();

    // motherPanel
    private JTextField motherNameField = new JTextField();
    private JTextField motherPhoneNumberField = new JTextField();

    // HowMatriculatePanel
    private ButtonGroup radioButtonGroup = new ButtonGroup();
    private JRadioButton examineRadioButton = new JRadioButton("По экзаменам", false);
    private JRadioButton noExamineRadioButton = new JRadioButton("Без экзаменов", false);
    private JRadioButton orphanOutExamineRadioButton = new JRadioButton("Вне конкурса ( сирота )", false);
    private JRadioButton invalidOutExamineRadioButton = new JRadioButton("Вне конкурса ( инвалид )", false);
    private JRadioButton otherOutExamineRadioButton = new JRadioButton("Вне конкурса ( другое )", false);

    // specialityPanel
    private SpecialityPanel specialityPanel = new SpecialityPanel();

    // markPanel
    private MarkPanel markPanel = new MarkPanel();

    // documentsPanel
    private JCheckBox takeAwayDocumentsCheckBox = new JCheckBox("Забрал документы");
    private JCheckBox originalAttestatCheckBox = new JCheckBox("Оригинал аттестата");
    private JCheckBox attestatInsertCheckBox = new JCheckBox("Наличие вкладыша аттестата");
    private JCheckBox originalEgeCheckBox = new JCheckBox("Оригинал ЕГЭ");
    private JCheckBox originalMedicalCertificateCheckBox = new JCheckBox("Оригинал медицинской справки");
    private JCheckBox copyMedicalPolicyCheckBox = new JCheckBox("Копия медицинского полиса");

    private JLabel photosLabel = new JLabel("Количество фотографий:");
    private JSpinner photosSpinner = new JSpinner();
    private JLabel passportLabel = new JLabel("Количество копий паспорта:");
    private JSpinner passportCopySpinner = new JSpinner();

    // schoolPanel
    private JTextField schoolNameField = new JTextField();

    // infoPanel
    private JTextArea infoTextArea = new JTextArea(3, 10);

    // buttonsPanel
    private JCheckBox needExitDuringAddingCheckBox = new JCheckBox("Закрыть при добавлении", true);

    private MainFrame mainFrame;

    @Autowired
    private MatriculantDAO matriculantDAO;

    @Autowired
    public MatriculantDialog(MainFrame owner) {
        super(owner);
        this.mainFrame = owner;

        boolean add = true; // todo: REMOVE

        setLayout(new GridBagLayout());

        add(getTopPanel(), getPanelConstraints(0, 0, 2, 1));
        add(getMatriculantPanel(), getPanelConstraints(0, 1, 1, 1));
        add(getSchoolPanel(), getPanelConstraints(1, 1, 1, 1));

        add(getFatherPanel(), getPanelConstraints(0, 2, 1, 1));
        add(getMotherPanel(), getPanelConstraints(1, 2, 1, 1));

        add(getHowMatriculatePanel(), getPanelConstraints(0, 3, 1, 1));
        add(markPanel, getPanelConstraints(1, 3, 1, 1));

        add(getDocumentsPanel(), getPanelConstraints(0, 4, 1, 1));
        add(specialityPanel, getPanelConstraints(1, 4, 1, 1));

        add(getInfoPanel(), getPanelConstraints(0, 7, 2, 1).setFill(GBConstraints.BOTH).setWeight(100, 100));
        add(getButtonsPanel(add), getPanelConstraints(0, 8, 2, 1));

        // align parts
        JLabel emptyLabel1 = new JLabel();
        emptyLabel1.setPreferredSize(new Dimension(400, 0));
        add(emptyLabel1, new GBConstraints(0, 20, true));
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setPreferredSize(new Dimension(400, 0));
        add(emptyLabel2, new GBConstraints(1, 20, true));

        pack();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension dialogSize = getSize();
        int x = (int) (screenSize.getWidth() / 2 - dialogSize.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - dialogSize.getHeight() / 2);
        setLocation(x, y);

        JPanel content = (JPanel) getContentPane();
        content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE_DIALOG");
        content.getActionMap().put("CLOSE_DIALOG", closeAction);
    }

    public void setMatriculant(Matriculant matriculant) {
        this.matriculant = matriculant;
        setFieldsFromMatriculant(matriculant);
    }

    private JPanel getTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.add(new JLabel("№ расписки:"), new GBConstraints(0, 0));
        topPanel.add(receiptNumberField, new GBConstraints(1, 0, true));

        todaySpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
        todaySpinner.setEditor(new JSpinner.DateEditor(todaySpinner, "dd.MM.yyyy"));
        topPanel.add(new JLabel("Дата подачи документов:"), new GBConstraints(2, 0));
        topPanel.add(todaySpinner, new GBConstraints(3, 0));

        timeSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm:ss"));
        topPanel.add(new JLabel("Время подачи документов:"), new GBConstraints(4, 0));
        topPanel.add(timeSpinner, new GBConstraints(5, 0));

        topPanel.setBorder(BorderFactory.createEtchedBorder());
        return topPanel;
    }

    private JPanel getMatriculantPanel() {
        JPanel matriculantPanel = new JPanel(new GridBagLayout());
        matriculantPanel.add(new JLabel("ФИО:"), new GBConstraints(0, 0));
        matriculantPanel.add(matriculantNameField, new GBConstraints(1, 0, true));
        matriculantPanel.add(new JLabel("Телефоны:"), new GBConstraints(0, 1));
        matriculantPanel.add(matriculantPhoneNumberField, new GBConstraints(1, 1, true));
        matriculantPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Абитуриент"));
        return matriculantPanel;
    }

    private JPanel getFatherPanel() {
        JPanel fatherPanel = new JPanel(new GridBagLayout());
        fatherPanel.add(new JLabel("ФИО:"), new GBConstraints(0, 0));
        fatherPanel.add(fatherNameField, new GBConstraints(1, 0, true));
        fatherPanel.add(new JLabel("Телефоны:"), new GBConstraints(0, 1));
        fatherPanel.add(fatherPhoneNumberField, new GBConstraints(1, 1, true));
        fatherPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Отец"));
        return fatherPanel;
    }

    private JPanel getMotherPanel() {
        JPanel motherPanel = new JPanel(new GridBagLayout());
        motherPanel.add(new JLabel("ФИО:"), new GBConstraints(0, 0));
        motherPanel.add(motherNameField, new GBConstraints(1, 0, true));
        motherPanel.add(new JLabel("Телефоны:"), new GBConstraints(0, 1));
        motherPanel.add(motherPhoneNumberField, new GBConstraints(1, 1, true));
        motherPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Мать"));
        return motherPanel;
    }

    private JPanel getDocumentsPanel() {
        JPanel documentsPanel = new JPanel(new GridBagLayout());
        takeAwayDocumentsCheckBox.addActionListener(takeAwayActionListener);
        documentsPanel.add(takeAwayDocumentsCheckBox, getCheckBoxConstraints(0, 0));
        documentsPanel.add(originalAttestatCheckBox, getCheckBoxConstraints(0, 1));
        documentsPanel.add(attestatInsertCheckBox, getCheckBoxConstraints(0, 2));
        documentsPanel.add(originalEgeCheckBox, getCheckBoxConstraints(0, 3));
        documentsPanel.add(originalMedicalCertificateCheckBox, getCheckBoxConstraints(0, 4));
        documentsPanel.add(copyMedicalPolicyCheckBox, getCheckBoxConstraints(0, 5));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(photosLabel, new GBConstraints(0, 0, true));
        panel.add(photosSpinner, new GBConstraints(1, 0, true));
        panel.add(passportLabel, new GBConstraints(0, 1, true));
        panel.add(passportCopySpinner, new GBConstraints(1, 1, true));
        documentsPanel.add(panel, new GBConstraints(0, 6, true));

        documentsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Документы"));
        return documentsPanel;
    }

    private JPanel getHowMatriculatePanel() {
        JPanel howMatriculatePanel = new JPanel(new GridBagLayout());

        JPanel content = new JPanel(new GridBagLayout());
        howMatriculatePanel.add(examineRadioButton, new GBConstraints(0, 0));
        howMatriculatePanel.add(noExamineRadioButton, new GBConstraints(0, 1));

        howMatriculatePanel.add(orphanOutExamineRadioButton, new GBConstraints(1, 0));
        howMatriculatePanel.add(invalidOutExamineRadioButton, new GBConstraints(1, 1));
        howMatriculatePanel.add(otherOutExamineRadioButton, new GBConstraints(1, 2));
        howMatriculatePanel.add(content, new GBConstraints(0, 0, true));

        radioButtonGroup.add(examineRadioButton);
        radioButtonGroup.add(noExamineRadioButton);
        radioButtonGroup.add(orphanOutExamineRadioButton);
        radioButtonGroup.add(invalidOutExamineRadioButton);
        radioButtonGroup.add(otherOutExamineRadioButton);

        howMatriculatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Как поступает"));
        return howMatriculatePanel;
    }

    private JPanel getSchoolPanel() {
        JPanel schoolPanel = new JPanel(new GridBagLayout());
        schoolPanel.add(new JLabel("Учебное заведение:"), new GBConstraints(0, 0));
        schoolPanel.add(schoolNameField, new GBConstraints(1, 0, true));
        schoolPanel.add(new JLabel(" "), new GBConstraints(0, 1, 2, 1, true));
        schoolPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Предыдущее место учебы"));
        return schoolPanel;
    }

    private JPanel getInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GBConstraints infoGBConstraints = new GBConstraints(0, 0).setFill(GBConstraints.BOTH).setWeight(100, 100);
        infoPanel.add(new JScrollPane(infoTextArea), infoGBConstraints);
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Дополнительная информация"));
        return infoPanel;
    }

    private JPanel getButtonsPanel(boolean add) {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        if (add) {
            buttonsPanel.add(needExitDuringAddingCheckBox, new GBConstraints(0, 0));
        }
        buttonsPanel.add(new JLabel(" "), new GBConstraints(1, 0, true));

        JButton actionButton;
        if (add) {
            actionButton = new JButton(new AddMatriculantAction());
        } else {
            actionButton = new JButton(new EditMatriculantAction());
        }
        JButton closeButton = new JButton(closeAction);

        buttonsPanel.add(actionButton, new GBConstraints(2, 0).setAnchor(GBConstraints.EAST));
        buttonsPanel.add(closeButton, new GBConstraints(3, 0).setAnchor(GBConstraints.EAST));
        return buttonsPanel;
    }

    private GBConstraints getCheckBoxConstraints(int gridX, int gridY) {
        return new GBConstraints(gridX, gridY).setInsets(0, 5, 0, 5);
    }

    private GBConstraints getPanelConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        return new GBConstraints(gridX, gridY, gridWidth, gridHeight, true)
                .setInsets(3, 5, 3, 5)
                .setAnchor(GBConstraints.NORTHWEST);
    }

    private boolean validateForm() {
        if (!receiptNumberField.getText().matches("\\w+")) {
            showWarningMessage("Введите корректный номер расписки");
            return false;
        }
        if (!matriculantNameField.getText().matches("[\\w\\sА-Яа-яЁё\\-]+")) {
            showWarningMessage("Введите корректное имя абитуриента");
            return false;
        }
        if (!motherNameField.getText().matches("[\\w\\sА-Яа-яЁё\\-]*")) {
            showWarningMessage("Введите корректное имя мамы абитуриента");
            return false;
        }
        if (!fatherNameField.getText().matches("[\\w\\sА-Яа-яЁё\\-]*")) {
            showWarningMessage("Введите корректное имя папы абитуриента");
            return false;
        }
        if (radioButtonGroup.getSelection() == null) {
            showWarningMessage("Выбирите тип поступления");
            return false;
        }
        if (specialityPanel.isEmpty()) {
            showWarningMessage("Выберите хотябы одну специальность");
            return false;
        }
        return true;
    }

    private class TakeAwayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean enabled = !takeAwayDocumentsCheckBox.isSelected();
            originalAttestatCheckBox.setEnabled(enabled);
            attestatInsertCheckBox.setEnabled(enabled);
            originalEgeCheckBox.setEnabled(enabled);
            originalMedicalCertificateCheckBox.setEnabled(enabled);
            copyMedicalPolicyCheckBox.setEnabled(enabled);

            photosLabel.setEnabled(enabled);
            photosSpinner.setEnabled(enabled);
            passportLabel.setEnabled(enabled);
            passportCopySpinner.setEnabled(enabled);
        }
    }

    private class AddMatriculantAction extends AbstractAction {
        private AddMatriculantAction() {
            super("Добавить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Matriculant matriculant = getMatriculantFromFields();

            if (validateForm()) {
                matriculantDAO.save(matriculant);
                DataAccessFactory.getMatriculants().add(matriculant);
                MatriculantTable.resetRowIndexes();

                if (needExitDuringAddingCheckBox.isSelected()) {
                    MatriculantDialog.this.setVisible(false);
                } else {
                    matriculant = new Matriculant();
                    setFieldsFromMatriculant(matriculant);
                }

                mainFrame.refresh();
                mainFrame.resetPositionToLastRow();
            }
        }
    }

    private class EditMatriculantAction extends AbstractAction {
        private EditMatriculantAction() {
            super("Изменить");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Matriculant matriculant = getMatriculantFromFields();

            if (validateForm()) {
                matriculantDAO.update(matriculant);
                MatriculantDialog.this.setVisible(false);
                
                mainFrame.refresh();
            }
        }
    }

    private void setFieldsFromMatriculant(Matriculant matriculant) {
        if (matriculant.getReceiptNumber() != null) {
            receiptNumberField.setText(matriculant.getReceiptNumber());
        } else {
            receiptNumberField.setText("");
        }
        if (matriculant.getFilingDate() != null) {
            todaySpinner.getModel().setValue(matriculant.getFilingDate());
        } else {
            todaySpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
            todaySpinner.setEditor(new JSpinner.DateEditor(todaySpinner, "dd.MM.yyyy"));
        }
        if (matriculant.getFilingDate() != null) {
            timeSpinner.getModel().setValue(matriculant.getFilingDate());
        } else {
            timeSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
            timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "hh:mm:ss"));
        }

        if (matriculant.getName() != null) {
            matriculantNameField.setText(matriculant.getName());
        } else {
            matriculantNameField.setText("");
        }
        if (matriculant.getPhoneNumbers() != null) {
            matriculantPhoneNumberField.setText(matriculant.getPhoneNumbers());
        } else {
            matriculantPhoneNumberField.setText("");
        }

        if (matriculant.getFather() != null) {
            if (matriculant.getFather().getName() != null) {
                fatherNameField.setText(matriculant.getFather().getName());
            } else {
                fatherNameField.setText("");
            }
            if (matriculant.getFather().getPhoneNumbers() != null) {
                fatherPhoneNumberField.setText(matriculant.getFather().getPhoneNumbers());
            } else {
                fatherPhoneNumberField.setText("");
            }
        } else {
            fatherNameField.setText("");
            fatherPhoneNumberField.setText("");
        }

        if (matriculant.getMother() != null) {
            if (matriculant.getMother().getName() != null) {
                motherNameField.setText(matriculant.getMother().getName());
            } else {
                motherNameField.setText("");
            }
            if (matriculant.getMother().getPhoneNumbers() != null) {
                motherPhoneNumberField.setText(matriculant.getMother().getPhoneNumbers());
            } else {
                motherPhoneNumberField.setText("");
            }
        } else {
            motherNameField.setText("");
            motherPhoneNumberField.setText("");
        }

        radioButtonGroup.clearSelection();
        examineRadioButton.setSelected(EntranceCategory.EXAMINE.equals(matriculant.getEntranceCategory()));
        noExamineRadioButton.setSelected(EntranceCategory.NO_EXAMINE.equals(matriculant.getEntranceCategory()));
        orphanOutExamineRadioButton.setSelected(
                EntranceCategory.OUT_EXAMINE_ORPHAN.equals(matriculant.getEntranceCategory()));
        invalidOutExamineRadioButton.setSelected(
                EntranceCategory.OUT_EXAMINE_INVALID.equals(matriculant.getEntranceCategory()));
        otherOutExamineRadioButton.setSelected(
                EntranceCategory.OUT_EXAMINE_OTHER.equals(matriculant.getEntranceCategory()));

        if (matriculant.getSpeciality() != null) {
            specialityPanel.setSpecialityMap(matriculant.getSpeciality());
        } else {
            specialityPanel.setSpecialityMap(new TreeMap<Integer, String>());
        }

        if (matriculant.getBalls() != null) {
            markPanel.setMarks(matriculant.getBalls());
        } else {
            markPanel.setMarks(new HashMap<String, Integer>());
        }

        if (matriculant.getDocuments() != null) {
            takeAwayDocumentsCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isTookDocuments()));
            originalAttestatCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isOriginalAttestat()));
            attestatInsertCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isAttestatInsert()));
            originalEgeCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isOriginalEge()));
            if (matriculant.getDocuments() != null) {
                photosSpinner.setValue(matriculant.getDocuments().getCountPhotos());
                passportCopySpinner.setValue(matriculant.getDocuments().getCountPassportCopy());
            }
            originalMedicalCertificateCheckBox.setSelected(
                    Boolean.TRUE.equals(matriculant.getDocuments().isOriginalMedicalCertificate()));
            copyMedicalPolicyCheckBox.setSelected(
                    Boolean.TRUE.equals(matriculant.getDocuments().isCopyMedicalPolicy()));
        } else {
            takeAwayDocumentsCheckBox.setSelected(false);
            originalAttestatCheckBox.setSelected(false);
            attestatInsertCheckBox.setSelected(false);
            originalEgeCheckBox.setSelected(false);
            originalMedicalCertificateCheckBox.setSelected(false);
            copyMedicalPolicyCheckBox.setSelected(false);
            photosSpinner.setValue(0);
            passportCopySpinner.setValue(0);
        }
        takeAwayActionListener.actionPerformed(null);

        if (matriculant.getSchoolName() != null) {
            schoolNameField.setText(matriculant.getSchoolName());
        } else {
            schoolNameField.setText("");
        }

        if (matriculant.getInfo() != null) {
            infoTextArea.setText(matriculant.getInfo());
        } else {
            infoTextArea.setText("");
        }
    }

    private Matriculant getMatriculantFromFields() {
        matriculant.setReceiptNumber(receiptNumberField.getText());
        matriculant.setFilingDate(getDateFromFields());

        matriculant.setName(matriculantNameField.getText());
        matriculant.setPhoneNumbers(matriculantPhoneNumberField.getText());

        Person father = new Person(fatherNameField.getText(), fatherPhoneNumberField.getText());
        matriculant.setFather(father);

        Person mother = new Person(motherNameField.getText(), motherPhoneNumberField.getText());
        matriculant.setMother(mother);

        if (examineRadioButton.isSelected()) {
            matriculant.setEntranceCategory(EntranceCategory.EXAMINE);
        }
        if (noExamineRadioButton.isSelected()) {
            matriculant.setEntranceCategory(EntranceCategory.NO_EXAMINE);
        }
        if (orphanOutExamineRadioButton.isSelected()) {
            matriculant.setEntranceCategory(EntranceCategory.OUT_EXAMINE_ORPHAN);
        }
        if (invalidOutExamineRadioButton.isSelected()) {
            matriculant.setEntranceCategory(EntranceCategory.OUT_EXAMINE_INVALID);
        }
        if (otherOutExamineRadioButton.isSelected()) {
            matriculant.setEntranceCategory(EntranceCategory.OUT_EXAMINE_OTHER);
        }

        matriculant.setSpeciality(specialityPanel.getSpecialityMap());

        matriculant.setBalls(markPanel.getMarks());

        Documents documents = new Documents();
        documents.setTookDocuments(takeAwayDocumentsCheckBox.isSelected());
        documents.setOriginalAttestat(originalAttestatCheckBox.isSelected());
        documents.setAttestatInsert(attestatInsertCheckBox.isSelected());
        documents.setOriginalEge(originalEgeCheckBox.isSelected());
        documents.setCountPhotos((Integer) photosSpinner.getValue());
        documents.setCountPassportCopy((Integer) passportCopySpinner.getValue());
        documents.setOriginalMedicalCertificate(originalMedicalCertificateCheckBox.isSelected());
        documents.setCopyMedicalPolicy(copyMedicalPolicyCheckBox.isSelected());
        matriculant.setDocuments(documents);

        matriculant.setSchoolName(schoolNameField.getText());

        matriculant.setInfo(infoTextArea.getText());

        return matriculant;
    }

    private Date getDateFromFields() {
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime((Date) todaySpinner.getModel().getValue());
        int year = todayCalendar.get(Calendar.YEAR);
        int month = todayCalendar.get(Calendar.MONTH);
        int day = todayCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime((Date) timeSpinner.getModel().getValue());

        int hour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = timeCalendar.get(Calendar.MINUTE);
        int second = timeCalendar.get(Calendar.SECOND);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}

