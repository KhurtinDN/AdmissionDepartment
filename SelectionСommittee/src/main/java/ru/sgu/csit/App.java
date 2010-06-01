package ru.sgu.csit;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;
import ru.sgu.csit.selectioncommittee.common.Speciality;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //Session session = LocalSessionFactory.getSessionFactory().getCurrentSession();
        Matriculant matriculant = new Matriculant();
        ReceiptExamine receiptExamine = new ReceiptExamine();
        Speciality speciality1 = new Speciality("ПМ 101");
        Speciality speciality2 = new Speciality("ВМ 102");
        Speciality speciality3 = new Speciality("КБ 103");
        Speciality speciality4 = new Speciality("ИВТ 104");
        //MatriculantDAOImpl matriculantDAO = new MatriculantDAOImpl();
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
        DataAccessFactory.getMatriculantDAO().create(matriculant);

        receiptExamine.setName("ЕГЭ РЯ");
        DataAccessFactory.getReceiptExamineDAO().create(receiptExamine);
        receiptExamine.setName("ЕГЭ МАТ");
        DataAccessFactory.getReceiptExamineDAO().create(receiptExamine);
        receiptExamine.setName("ЕГЭ ИНФ");
        DataAccessFactory.getReceiptExamineDAO().create(receiptExamine);
        receiptExamine.setName("ЕГЭ ФИЗ");
        DataAccessFactory.getReceiptExamineDAO().create(receiptExamine);


        speciality1.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        speciality1.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality1.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ФИЗ").get(0));
        DataAccessFactory.getSpecialityDAO().create(speciality1);

        speciality2.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        speciality2.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality2.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ИНФ").get(0));
        DataAccessFactory.getSpecialityDAO().create(speciality2);

        speciality3.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        speciality3.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality3.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ИНФ").get(0));
        DataAccessFactory.getSpecialityDAO().create(speciality3);

        speciality4.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ РЯ").get(0));
        speciality4.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ МАТ").get(0));
        speciality4.addExam(DataAccessFactory.getReceiptExamineDAO().findByName("ЕГЭ ИНФ").get(0));
        DataAccessFactory.getSpecialityDAO().create(speciality4);
        */

    }
}
