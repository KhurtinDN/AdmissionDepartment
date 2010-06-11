package ru.sgu.csit.selectioncommittee.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 03.05.2010
 * Time: 13:27:35
 *
 * @author xx & hd
 */
public class ResourcesForApplication {
    public final static String tTITLE_OF_APPLICATION = "Приемная комиссия";
    public final static String tTITLE_OF_ABOUT = "О программе \"Приемная комиссия\"";

    public final static String tFILE_MENU = "Файл";
    public final static String tEXPORT_TO_EXCEL = "Экспорт в Excel";
    public final static String tEXPORT_TO_EXCEL_DESCRIPTION = "Сохранить данные о поступающих в Excel";
    public final static String tPRINT = "Печать";
    public final static String tPRINT_DESCRIPTION = "Вывести на печать таблицу";
    public final static String tEXIT = "Выход";
    public final static String tEXIT_DESCRIPTION = "Выход из программы";

    public final static String tCLOSE = "Закрыть";
    public final static String tCLOSE_DESCRIPTION = "Закрыть диалоговое окно";

    public final static String tEDIT_MENU = "Правка";
    public final static String tADD = "Добавить";
    public final static String tADD_DESCRIPTION = "Добавить нового абитуриента";
    public final static String tEDIT = "Редактировать";
    public final static String tEDIT_DESCRIPTION = "Редактировать данные абитуриента";
    public final static String tDELETE = "Удалить";
    public final static String tDELETE_DESCRIPTION = "Удалить абитуриента";
    public final static String tINFO = "Просмотреть";
    public final static String tINFO_DESCRIPTION = "Посмотреть данные абитуриента";

    public final static String tVIEW_MENU = "Вид";
    public final static String tSHOWCOLUMN_DESCRIPTION = "Показать или скрыть столбец";
    public final static String tAUTORESIZE = "Автовыравнивание";
    public final static String tAUTORESIZE_DESCRIPTION = "Выровнять таблицу по ширине окна";
    public final static String tHIGHLIGHTING = "Цветовое выделение";
    public final static String tHIGHLIGHTING_DESCRIPTION = "Выделить строки цветами в соответствии с наличием документов";

    public final static String tHELP_MENU = "Справка";
    public final static String tABOUT = "О программе";
    public final static String tABOUT_DESCRIPTION = "О программе \"Приемная комиссия\"";


    public final static Image iAPP16 = Toolkit.getDefaultToolkit().getImage(
            ResourcesForApplication.class.getResource("/icons/app16.png"));
    public final static Icon iABOUT = new ImageIcon(ResourcesForApplication.class.getResource("/icons/app16.png"));
    public final static Icon iAPP48 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/app48.png"));
    public final static Icon iPRINT16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/printer16.png"));
    public final static Icon iEXIT16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/exit16.png"));
    public final static Icon iADD16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/add16.png"));
    public final static Icon iEDIT16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/edit16.png"));
    public final static Icon iDELETE16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/delete16.png"));
    public final static Icon iOK = new ImageIcon(ResourcesForApplication.class.getResource("/icons/ok.png"));
    public final static Icon iCANCEL = new ImageIcon(ResourcesForApplication.class.getResource("/icons/cancel.png"));


    //
    public final static String tCONFIRM_CLOSE_APP = "Вы действительно хотите закрыть приложение?";
    public final static String tCONFIRM = "Подтверждение";
    public final static String tYES = "Да";
    public final static String tNO = "Нет";
}
