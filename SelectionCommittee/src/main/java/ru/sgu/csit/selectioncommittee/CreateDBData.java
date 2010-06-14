package ru.sgu.csit.selectioncommittee;

import ru.sgu.csit.selectioncommittee.common.*;
import ru.sgu.csit.selectioncommittee.dao.impl.MatriculantDAOImpl;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

import java.util.*;

/**
 * Hello world!
 */
public class CreateDBData {
    public static void main(String[] args) {
        System.out.println("Start process");

        //Session session = LocalSessionFactory.getSessionFactory().getCurrentSession();
        //Matriculant matriculant = new Matriculant();
        ReceiptExamine receiptExamine = new ReceiptExamine();
        Speciality speciality1 = new Speciality("ПМ 101");
        Speciality speciality2 = new Speciality("ВМ 102");
        Speciality speciality3 = new Speciality("КБ 103");
        Speciality speciality4 = new Speciality("ИВТ 104");
        //MatriculantDAOImpl matriculantDAO = new MatriculantDAOImpl();

        receiptExamine.setName("ЕГЭ РЯ");
        DataAccessFactory.getReceiptExamineDAO().save(receiptExamine);
        receiptExamine.setName("ЕГЭ МАТ");
        DataAccessFactory.getReceiptExamineDAO().save(receiptExamine);
        receiptExamine.setName("ЕГЭ ИНФ");
        DataAccessFactory.getReceiptExamineDAO().save(receiptExamine);
        receiptExamine.setName("ЕГЭ ФИЗ");
        DataAccessFactory.getReceiptExamineDAO().save(receiptExamine);

        speciality1.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality1.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ФИЗ").get(0));
        speciality1.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        DataAccessFactory.getSpecialityDAO().save(speciality1);

        speciality2.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality2.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ИНФ").get(0));
        speciality2.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        DataAccessFactory.getSpecialityDAO().save(speciality2);

        speciality3.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality3.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ИНФ").get(0));
        speciality3.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        DataAccessFactory.getSpecialityDAO().save(speciality3);

        speciality4.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality4.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ИНФ").get(0));
        speciality4.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        DataAccessFactory.getSpecialityDAO().save(speciality4);

        DataAccessFactory.reloadAll();

        for (int i = 0; i < 70; ++i) {
            Matriculant matriculant = MatriculantGenerator.getRandomMatriculant();
            DataAccessFactory.getMatriculantDAO().save(matriculant);
        }
        /*
        matriculant.setName("Петров Сидор Иванович");
        matriculant.setPhoneNumbers("02, +7 877 777 34 22");
        Calendar date = Calendar.getInstance();
        //date.set(Calendar.MONTH, 1);
        matriculant.setFilingDate(date.getTime());
        Map<Integer, String> mapSpecPriorityByPetrov = new TreeMap<Integer, String>();
        ArrayList<Speciality> listSpec =
                (ArrayList<Speciality>) DataAccessFactory.getSpecialityDAO().findAll();
        for (int i = 0; i < listSpec.size(); ++i) {
            mapSpecPriorityByPetrov.put(listSpec.size() - i, listSpec.get(i).getName());
        }
        matriculant.setSpeciality(mapSpecPriorityByPetrov);
        DataAccessFactory.getMatriculantDAO().save(matriculant);

        matriculant.setName("Иванов Пётр Александрович");
        matriculant.setPhoneNumbers("+7 827 568 34 23");
        date = Calendar.getInstance();
        matriculant.setFilingDate(date.getTime());
        Map<Integer, String> mapSpecPriority = new TreeMap<Integer, String>();
        listSpec =
                (ArrayList<Speciality>) DataAccessFactory.getSpecialityDAO().findAll();
        for (int i = 0; i < listSpec.size(); ++i) {
            mapSpecPriorityByPetrov.put(listSpec.size() - i, listSpec.get(i).getName());
        }
        matriculant.setSpeciality(mapSpecPriority);
        DataAccessFactory.getMatriculantDAO().save(matriculant);

        matriculant.setName("Захаров Алексей Петрович");
        matriculant.setPhoneNumbers("+7 908 222 46 78");
        date = Calendar.getInstance();
        matriculant.setFilingDate(date.getTime());
        mapSpecPriority = new TreeMap<Integer, String>();
        listSpec =
                (ArrayList<Speciality>) DataAccessFactory.getSpecialityDAO().findAll();
        for (int i = 0; i < listSpec.size(); ++i) {
            mapSpecPriorityByPetrov.put(listSpec.size() - i, listSpec.get(i).getName());
        }
        matriculant.setSpeciality(mapSpecPriority);
        DataAccessFactory.getMatriculantDAO().save(matriculant);

        /*
        Speciality spec = DataAccessFactory.getSpecialityDAO().findByName("ПМ 101").get(0);
        ReceiptExamine exam = spec.getExams().get(0);
        spec.getExams().clear();
        spec.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ФИЗ").get(0));
        spec.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        spec.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        DataAccessFactory.getSpecialityDAO().update(spec);
        */
    }

    private static class MatriculantGenerator {
        private static Random generator = new Random(new Date().getTime());
        private static int number = DataAccessFactory.getMatriculants().size();
        private static String[][] lastNames = {{"Иванов", "Петров", "Захаров", "Клочко", "Кузнецов", "Алексеев", "Свиридов",
                "Яковлев", "Нечаев", "Куприн", "Снегов", "Дубровский", "Шефер", "Привалов", "Горбовский", "Печкин"},
                {"Иванова", "Петрова", "Захарова", "Клочко", "Кузнецова", "Алексеева", "Свиридова",
                    "Яковлева", "Нечаева", "Куприна", "Снегова", "Дубровская", "Шефер", "Привалова", "Горбовская", "Печкина"}};
        private static String[][] firstNames = {{"Иван", "Пётр", "Александр", "Семён", "Николай", "Алексей", "Юрий",
                "Борис", "Сергей", "Андрей", "Олег", "Степан", "Максим", "Дмитрий", "Владимир", "Виктор", "Денис"},
                {"Светлана", "Екатерина", "Александра", "Надежда", "Анна", "Юлия", "Томара", "Наталия",
                    "Елена", "Мария", "Марина", "Ольга", "Зульфия", "Алёна", "Лидия", "Антонина", "Настасья", "Лера"}};
        private static String[][] middleNames = {{"Иванович", "Петрович", "Александрович", "Семёнович", "Николаевич", "Алексеевич",
                "Юрьевич", "Борисович", "Сергеевич", "Андреевич", "Олегович", "Степанович", "Максимович", "Дмитриевич",
                    "Владимирович", "Викторович", "Денисович"},
                {"Ивановна", "Петровна", "Александровна", "Семёновна", "Николаевна", "Алексеевна",
                    "Юрьевна", "Борисовна", "Сергеевна", "Андреевна", "Олеговна", "Степановна", "Максимовна", "Дмитриевна",
                        "Владимировна", "Викторовна", "Денисовна"}};

        public MatriculantGenerator() {
        }

        public static Matriculant getRandomMatriculant() {
            Matriculant matriculant = new Matriculant();
            int sex = generator.nextInt(2);

            matriculant.setName(getRandomName(sex));
            matriculant.setPhoneNumbers(getRandomPhone());
            matriculant.setFilingDate(Calendar.getInstance().getTime());
            matriculant.setReceiptNumber(String.valueOf(++number));
            matriculant.setFather(new Person(getRandomName(0), getRandomPhone()));
            matriculant.setMother(new Person(getRandomName(1), getRandomPhone()));
            matriculant.setSchoolName("Школа № " + (generator.nextInt(999) + 1));
            matriculant.setEntranceCategory(getRandomCategory());
            matriculant.setEntranceSpecialityName("");
            matriculant.setDocuments(getRandomDocuments());
            matriculant.setSpeciality(getRandomMapSpec());
            matriculant.setBalls(getRandomBalls(matriculant));

            return matriculant;
        }

        private static String getRandomName(int sex) {
            return lastNames[sex][generator.nextInt(lastNames[sex].length)] +
                   " " + firstNames[sex][generator.nextInt(firstNames[sex].length)] +
                   " " + middleNames[sex][generator.nextInt(middleNames[sex].length)];
        }

        private static String getRandomPhone() {
            return "+7 " + (generator.nextInt(200) + 800) +
                    " " + generator.nextInt(10) + generator.nextInt(10) + generator.nextInt(10) +
                    " " + generator.nextInt(10) + generator.nextInt(10) +
                    " " + generator.nextInt(10) + generator.nextInt(10);
        }

        private static EntranceCategory getRandomCategory() {
            switch (generator.nextInt(100)) {
                case 1:
                    return EntranceCategory.NO_EXAMINE;
                case 2:
                    return EntranceCategory.OUT_EXAMINE_INVALID;
                case 3:
                    return EntranceCategory.OUT_EXAMINE_ORPHAN;
                case 4:
                    return EntranceCategory.OUT_EXAMINE_OTHER;
            }
            return EntranceCategory.EXAMINE;
        }

        private static Matriculant.Documents getRandomDocuments() {
            Matriculant.Documents documents = new Matriculant.Documents();

            documents.setOriginalAttestat(generator.nextBoolean());
            documents.setAttestatInsert(generator.nextBoolean());
            documents.setCopyMedicalPolicy(generator.nextBoolean());
            documents.setOriginalEge(generator.nextBoolean());
            documents.setOriginalMedicalCertificate(generator.nextBoolean());
            documents.setCountPassportCopy(generator.nextInt(3));
            documents.setCountPhotos(generator.nextInt(7));

            return documents;
        }

        private static Map<Integer, String> getRandomMapSpec() {
            Map<Integer, String> mapSpecPriority = new TreeMap<Integer, String>();
            ArrayList<Speciality> listSpec =
                    (ArrayList<Speciality>) DataAccessFactory.getSpecialityDAO().findAll();
            int countSpecPriority = generator.nextInt(70) + 1;

            if (countSpecPriority > listSpec.size()) {
                countSpecPriority = listSpec.size();
            }
            for (int i = 0; i < countSpecPriority; ++i) {
                int index = -1;

                do {
                    index = generator.nextInt(listSpec.size());
                    for (int j = 0; index > -1 && j < mapSpecPriority.size(); ++j) {
                        if (mapSpecPriority.get(j + 1).equals(listSpec.get(index).getName())) {
                            index = -1;
                        }
                    }
                } while (index < 0);
                mapSpecPriority.put(i + 1, listSpec.get(index).getName());
            }

            return mapSpecPriority;
        }

        private static Map<String, Integer> getRandomBalls(Matriculant matriculant) {
            Map<String, Integer> balls = new HashMap<String, Integer>();

            for (int i = 0; i < DataAccessFactory.getExamines().size(); ++i) {
                balls.put(DataAccessFactory.getExamines().get(i).getName(), 51 + generator.nextInt(50));
            }

            return balls;
        }
    }
}
