package ru.sgu.csit.admissiondepartment.gui.utils;

import org.springframework.security.access.AccessDeniedException;

import java.io.*;

import static ru.sgu.csit.admissiondepartment.gui.utils.MessageUtil.showWarningMessage;
import static ru.sgu.csit.admissiondepartment.gui.utils.ResourcesForApplication.mWARN_ACCESS_DENIED;

/**
 * Date: Jun 29, 2010
 * Time: 2:12:24 PM
 *
 * @author : xx & hd
 */
public class SecurityExceptionHandler {

    public void handle(Throwable e) {
        if (e instanceof AccessDeniedException) {
            showWarningMessage(mWARN_ACCESS_DENIED);
        } else {
            saveStackTrace(e);
        }
    }

    private void saveStackTrace(Throwable aThrowable) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("SelectionCommittee.errors", true);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            try {
                printWriter.println(getStackTrace(aThrowable));
            } finally {
                printWriter.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}
