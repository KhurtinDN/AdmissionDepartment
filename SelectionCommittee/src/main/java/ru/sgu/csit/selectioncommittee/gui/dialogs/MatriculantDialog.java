package ru.sgu.csit.selectioncommittee.gui.dialogs;

import ru.sgu.csit.selectioncommittee.common.EntranceCategory;
import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.Person;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;
import ru.sgu.csit.selectioncommittee.gui.MainFrame;
import ru.sgu.csit.selectioncommittee.gui.dialogs.panels.SpecialityPanel;
import ru.sgu.csit.selectioncommittee.gui.utils.GBConstraints;
import ru.sgu.csit.selectioncommittee.gui.dialogs.panels.MarkPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Date: May 4, 2010
 * Time: 10:34:02 PM
 *
 * @author xx & hd
 */
public class MatriculantDialog extends JDialog {
    private Matriculant matriculant;
    private MainFrame mainFrame;

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
    private ButtonGroup radionButtonGroup = new ButtonGroup();
    private JRadioButton examineRadioButton = new JRadioButton("По экзаменам", false);
    private JRadioButton noExamineRadioButton = new JRadioButton("Без экзаменов", false);
    private JRadioButton outExamineRadioButton = new JRadioButton("Вне конкурса", false);

    // specialityPanel
    private SpecialityPanel specialityPanel = new SpecialityPanel();

    // markPanel
    MarkPanel markPanel = new MarkPanel();

    // documentsPanel
    private JCheckBox originalAttestatCheckBox = new JCheckBox("Оригинал аттестата");
    private JCheckBox attestatInsertCheckBox = new JCheckBox("Наличие вкладыша аттестата");
    private JCheckBox originalEgeCheckBox = new JCheckBox("Оригинал ЕГЭ");
    private JCheckBox allPhotosCheckBox = new JCheckBox("Все фотографии");
    private JCheckBox allPassportCopyCheckBox = new JCheckBox("Все копии паспорта");
    private JCheckBox originalMedicalCertificateCheckBox = new JCheckBox("Оригинал медицинской справки");
    private JCheckBox copyMedicalPolicyCheckBox = new JCheckBox("Копия медицинского полиса");

    // schoolPanel
    private JTextField schoolNameField = new JTextField();

    // infoPanel
    private JTextArea infoTextArea = new JTextArea(3, 10);

    // buttonsPanel
    private JCheckBox needExitDuringAddingCheckBox = new JCheckBox("Закрыть при добавлении", true);


    public MatriculantDialog(MainFrame owner, boolean add, Matriculant matriculant) {
        super(owner, add ? "Добавление нового абитуриента" : "Редактирование данных абитуриента", add);
        this.matriculant = matriculant;
        this.mainFrame = owner;

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

        if (!add) {
            setFieldsFromMatriculant(matriculant);
        }
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
        documentsPanel.add(originalAttestatCheckBox, getCheckBoxConstraints(0, 0));
        documentsPanel.add(attestatInsertCheckBox, getCheckBoxConstraints(0, 1));
        documentsPanel.add(originalEgeCheckBox, getCheckBoxConstraints(0, 2));
        documentsPanel.add(allPhotosCheckBox, getCheckBoxConstraints(0, 3));
        documentsPanel.add(allPassportCopyCheckBox, getCheckBoxConstraints(0, 4));
        documentsPanel.add(originalMedicalCertificateCheckBox, getCheckBoxConstraints(0, 5));
        documentsPanel.add(copyMedicalPolicyCheckBox, getCheckBoxConstraints(0, 6));
        documentsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Документы"));
        return documentsPanel;
    }

    private JPanel getHowMatriculatePanel() {
        JPanel howMatriculatePanel = new JPanel(new GridBagLayout());
        howMatriculatePanel.add(examineRadioButton, new GBConstraints(0, 0, true));
        howMatriculatePanel.add(noExamineRadioButton, new GBConstraints(0, 1, true));
        howMatriculatePanel.add(outExamineRadioButton, new GBConstraints(0, 2, true));
        radionButtonGroup.add(examineRadioButton);
        radionButtonGroup.add(noExamineRadioButton);
        radionButtonGroup.add(outExamineRadioButton);
        howMatriculatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Как поступает"));
        return howMatriculatePanel;
    }

    private JPanel getSchoolPanel() {
        JPanel schoolPanel = new JPanel(new GridBagLayout());
        schoolPanel.add(new JLabel("Номер школы:"), new GBConstraints(0, 0));
        schoolPanel.add(schoolNameField, new GBConstraints(1, 0, true));
        schoolPanel.add(new JLabel(" "), new GBConstraints(0, 1, 2, 1, true));
        schoolPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Школа"));
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
            actionButton = new JButton(new AddMatriculantAction("Добавить"));
        } else {
            actionButton = new JButton(new EditMatriculantAction("Изменить"));
        }
        JButton closeButton = new JButton(new CloseAction("Закрыть"));

        buttonsPanel.add(actionButton, new GBConstraints(2, 0).setAnchor(GBConstraints.EAST));
        buttonsPanel.add(closeButton, new GBConstraints(3, 0).setAnchor(GBConstraints.EAST));
        return buttonsPanel;
    }

    private GBConstraints getCheckBoxConstraints(int gridX, int gridY) {
        return new GBConstraints(gridX, gridY, true).setInsets(0, 5, 0, 5);
    }

    private GBConstraints getPanelConstraints(int gridX, int gridY, int gridWidth, int gridHeight) {
        return new GBConstraints(gridX, gridY, gridWidth, gridHeight, true)
                .setInsets(3, 5, 3, 5)
                .setAnchor(GBConstraints.NORTHWEST);
    }

    private class AddMatriculantAction extends AbstractAction {
        private AddMatriculantAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Matriculant matriculant = getMatriculantFromFields();

            if (validateForm()) {
                DataAccessFactory.getMatriculantDAO().save(matriculant);
                DataAccessFactory.reloadMatriculants();
                mainFrame.refresh();
            } else {
                JOptionPane.showMessageDialog(MatriculantDialog.this, "Исправьте все", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }

            if (needExitDuringAddingCheckBox.isSelected()) {
                MatriculantDialog.this.setVisible(false);
            } else {
                matriculant = new Matriculant();
                setFieldsFromMatriculant(matriculant);
            }
        }
    }

    private boolean validateForm() {
        return true; // todo: validate
    }

    private class EditMatriculantAction extends AbstractAction {
        private EditMatriculantAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Matriculant matriculant = getMatriculantFromFields();

            if (validateForm()) {
                DataAccessFactory.getMatriculantDAO().update(matriculant);
                DataAccessFactory.reloadMatriculants();
                mainFrame.refresh();
            } else {
                JOptionPane.showMessageDialog(MatriculantDialog.this, "Исправьте все", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }

            MatriculantDialog.this.setVisible(false);
        }
    }

    private class CloseAction extends AbstractAction {
        private CloseAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MatriculantDialog.this.setVisible(false);
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

        examineRadioButton.setSelected(matriculant.getEntranceCategory() == EntranceCategory.EXAMINE);
        noExamineRadioButton.setSelected(matriculant.getEntranceCategory() == EntranceCategory.NO_EXAMINE);
        outExamineRadioButton.setSelected(matriculant.getEntranceCategory() == EntranceCategory.OUT_EXAMINE_OTHER);

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
            originalAttestatCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isOriginalAttestat()));
            attestatInsertCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isAttestatInsert()));
            originalEgeCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isOriginalEge()));
            allPhotosCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isAllPhotos()));
            allPassportCopyCheckBox.setSelected(Boolean.TRUE.equals(matriculant.getDocuments().isAllPassportCopy()));
            originalMedicalCertificateCheckBox.setSelected(
                    Boolean.TRUE.equals(matriculant.getDocuments().isOriginalMedicalCertificate()));
            copyMedicalPolicyCheckBox.setSelected(
                    Boolean.TRUE.equals(matriculant.getDocuments().isCopyMedicalPolicy()));
        } else {
            originalAttestatCheckBox.setSelected(false);
            attestatInsertCheckBox.setSelected(false);
            originalEgeCheckBox.setSelected(false);
            allPhotosCheckBox.setSelected(false);
            allPassportCopyCheckBox.setSelected(false);
            originalMedicalCertificateCheckBox.setSelected(false);
            copyMedicalPolicyCheckBox.setSelected(false);
        }

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
        if (outExamineRadioButton.isSelected()) {
            matriculant.setEntranceCategory(EntranceCategory.OUT_EXAMINE_OTHER);
        }

        matriculant.setSpeciality(specialityPanel.getSpecialityMap());

        matriculant.setBalls(markPanel.getMarks());

        Matriculant.Documents documents = new Matriculant.Documents();
        documents.setOriginalAttestat(originalAttestatCheckBox.isSelected());
        documents.setAttestatInsert(attestatInsertCheckBox.isSelected());
        documents.setOriginalEge(originalEgeCheckBox.isSelected());
        documents.setAllPhotos(allPhotosCheckBox.isSelected());
        documents.setAllPassportCopy(allPassportCopyCheckBox.isSelected());
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
        Calendar calendar = Calendar.getInstance();
        int hour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = timeCalendar.get(Calendar.MINUTE);
        int second = timeCalendar.get(Calendar.SECOND);

        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}

