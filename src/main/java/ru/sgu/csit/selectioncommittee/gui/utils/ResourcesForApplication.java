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
    public final static String tSHOWNOTENT_ITEM = "Не зачисленные";
    public final static String tSHOWENT_PREF = "Зачисленные на ";
    public final static String tSHOWENT_DESCRIPTION = "Показать абитуриентов данной категории";
    public final static String tSHOWCOLUMN_DESCRIPTION = "Показать или скрыть столбец";
    public final static String tAUTORESIZE = "Автовыравнивание";
    public final static String tAUTORESIZE_DESCRIPTION = "Выровнять таблицу по ширине окна";
    public final static String tHIGHLIGHTING = "Цветовое выделение";
    public final static String tHIGHLIGHTING_DESCRIPTION = "Выделить строки цветами в соответствии с наличием документов";

    public final static String tAPPORTION_MENU = "Действие";
    public final static String tCALCFORSPECIALITY_DESCRIPTION = "Сортировать абитуриентов для данной специальности";
    public final static String tCALCALL = "В порядке добавления";
    public final static String tCALCFOR_PREF = "Для ";
    public final static String tAPPORTION_SPEC_DESCRIPTION = "Распределить абитуриентов по специальностям";
    public final static String tAPPORTION_SPEC = "Распределить по специальностям";

    public final static String tHELP_MENU = "Справка";
    public final static String tABOUT = "О программе";
    public final static String tABOUT_DESCRIPTION = "О программе \"Приемная комиссия\"";


    public final static Image iAPP16 = Toolkit.getDefaultToolkit().getImage(
            ResourcesForApplication.class.getResource("/icons/app16.png"));
    public final static Icon iABOUT = new ImageIcon(ResourcesForApplication.class.getResource("/icons/app16.png"));
    public final static Icon iAPP48 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/app48.png"));
    public final static Icon iEXCEL16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/excel16.gif"));
    public final static Icon iEXIT16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/exit16.png"));
    public final static Icon iADD16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/add16.png"));
    public final static Icon iEDIT16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/edit16.png"));
    public final static Icon iINFO16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/info16.png"));
    public final static Icon iDELETE16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/delete16.png"));
    public final static Icon iREFRESH16 =
            new ImageIcon(ResourcesForApplication.class.getResource("/icons/refresh16.png"));
    public final static Icon iSORT16 = new ImageIcon(ResourcesForApplication.class.getResource("/icons/sort16.png"));
    public final static Icon iAPPORTION16 =
            new ImageIcon(ResourcesForApplication.class.getResource("/icons/apportion16.png"));

//    public final static String tCONFIRM_CLOSE_APP = "Вы действительно хотите закрыть приложение?";
    public final static String tCONFIRM = "Подтверждение";
    public final static String tYES = "Да";
    public final static String tNO = "Нет";

    public final static String mWARN_ACCESS_DENIED = "У Вас недостаточно прав для этой операции!";
}
