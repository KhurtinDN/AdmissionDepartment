package ru.sgu.csit.admissiondepartment.factory;

import ru.sgu.csit.admissiondepartment.common.Matriculant;
import ru.sgu.csit.admissiondepartment.common.ReceiptExamine;
import ru.sgu.csit.admissiondepartment.common.Speciality;
import ru.sgu.csit.admissiondepartment.dao.impl.MatriculantDAOImpl;
import ru.sgu.csit.admissiondepartment.dao.impl.ReceiptExamineDAOImpl;
import ru.sgu.csit.admissiondepartment.dao.impl.SpecialityDAOImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 5:28:26 PM
 *
 * @author xx & hd
 */
public class DataAccessFactory {
    private static final MatriculantDAOImpl matriculantDAO = new MatriculantDAOImpl();
    private static final ReceiptExamineDAOImpl receiptExamineDAO = new ReceiptExamineDAOImpl();
    private static final SpecialityDAOImpl specialityDAO = new SpecialityDAOImpl();

    private static List<Matriculant> matriculants = new ArrayList<Matriculant>();
    private static List<ReceiptExamine> examines = new ArrayList<ReceiptExamine>();
    private static List<Speciality> specialities = new ArrayList<Speciality>();

    private DataAccessFactory() {
    }

    public static MatriculantDAOImpl getMatriculantDAO() {
        return matriculantDAO;
    }

    public static ReceiptExamineDAOImpl getReceiptExamineDAO() {
        return receiptExamineDAO;
    }

    public static SpecialityDAOImpl getSpecialityDAO() {
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
