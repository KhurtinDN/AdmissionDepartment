package ru.sgu.csit.admissiondepartment.factory;

import com.google.common.collect.Lists;
import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.common.ReceiptExamine;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.dao.MatriculantDAO;
import ru.sgu.csit.admissiondepartment.dao.ReceiptExamineDAO;
import ru.sgu.csit.admissiondepartment.dao.SpecialityDAO;
import ru.sgu.csit.admissiondepartment.system.ApplicationContextHolder;

import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 5:28:26 PM
 *
 * @author xx & hd
 */
public class DataAccessFactory {

    private static final MatriculantDAO matriculantDAO = ApplicationContextHolder.getBean(MatriculantDAO.class);
    private static final ReceiptExamineDAO receiptExamineDAO = ApplicationContextHolder.getBean(ReceiptExamineDAO.class);
    private static final SpecialityDAO specialityDAO = ApplicationContextHolder.getBean(SpecialityDAO.class);

    private static List<Matriculant> matriculants = Lists.newArrayList();
    private static List<ReceiptExamine> examines = Lists.newArrayList();
    private static List<Speciality> specialities = Lists.newArrayList();

    private DataAccessFactory() {
    }

    public static MatriculantDAO getMatriculantDAO() {
        return matriculantDAO;
    }

    public static ReceiptExamineDAO getReceiptExamineDAO() {
        return receiptExamineDAO;
    }

    public static SpecialityDAO getSpecialityDAO() {
        return specialityDAO;
    }

    public static List<Matriculant> getMatriculants() {
        return matriculants;
    }

    public static List<ReceiptExamine> getExamines() {
        return examines;
    }

    public static List<Speciality> getSpecialities() {
        return specialities;
    }

    public static void reloadAll() {
        reloadMatriculants();
        reloadExamines();
        reloadSpecialities();
    }

    public static void reloadMatriculants() {
        matriculants = matriculantDAO.findAll();
    }

    public static void reloadExamines() {
        examines = receiptExamineDAO.findAll();
    }

    public static void reloadSpecialities() {
        specialities = specialityDAO.findAll();
    }
}
