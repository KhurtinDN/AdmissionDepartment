package ru.sgu.csit.selectioncommittee.factory;

import ru.sgu.csit.selectioncommittee.common.Matriculant;
import ru.sgu.csit.selectioncommittee.common.ReceiptExamine;
import ru.sgu.csit.selectioncommittee.common.Speciality;
import ru.sgu.csit.selectioncommittee.dao.impl.MatriculantDAOImpl;
import ru.sgu.csit.selectioncommittee.dao.impl.ReceiptExamineDAOImpl;
import ru.sgu.csit.selectioncommittee.dao.impl.SpecialityDAOImpl;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Date: May 5, 2010
 * Time: 5:28:26 PM
 *
 * @author xx & hd
 */
public class DataAccessFactory {
    private static final MatriculantDAOImpl matriculantDAO;
    private static final ReceiptExamineDAOImpl receiptExamineDAO;
    private static final SpecialityDAOImpl specialityDAO;

    private static List<Matriculant> matriculants;
    private static List<ReceiptExamine> examines;
    private static List<Speciality> specialities;

    static {
        matriculantDAO = new MatriculantDAOImpl();
        receiptExamineDAO = new ReceiptExamineDAOImpl();
        specialityDAO = new SpecialityDAOImpl();

        reloadAll();
    }

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
        matriculants = matriculantDAO.findAll();
        examines = receiptExamineDAO.findAll();
        specialities = specialityDAO.findAll();
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
