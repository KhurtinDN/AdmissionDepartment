package ru.sgu.csit;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;
import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.dao.impl.MatriculantDAOImpl;
import ru.sgu.csit.selectioncommittee.factory.DataAccessFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Start process");
        //Session session = LocalSessionFactory.getSessionFactory().getCurrentSession();
        Matriculant matriculant = new Matriculant();
        ReceiptExamine receiptExamine = new ReceiptExamine();
        Speciality speciality1 = new Speciality("ПМ 101");
        Speciality speciality2 = new Speciality("ВМ 102");
        Speciality speciality3 = new Speciality("КБ 103");
        Speciality speciality4 = new Speciality("ИВТ 104");
        MatriculantDAOImpl matriculantDAO = new MatriculantDAOImpl();
        
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
}
