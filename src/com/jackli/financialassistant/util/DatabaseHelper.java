package com.jackli.financialassistant.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 //���ݿ���
    private static String DATABASE_NAME = "FinancialAssistant.db";
    //���ݿ�汾��
    private static final int VERSION = 1;


    // ����֧����
    String tablepay = "create table if not exists accountpay("
        + "id integer PRIMARY KEY,"
        + "moneypay varchar(10),"
        + "categorypay varchar(20),"
        + "accountnumberpay varchar(20),"
        + "timepay varchar(50),"
        + "projectpay varchar(20),"
        + "memberpay varchar(20),"
        + "notepay varchar(200));";

    // ���������
    String tableincome = "create table if not exists accountincome("
        + "id integer PRIMARY KEY,"
        + "moneyincome varchar(10),"
        + "categoryincome varchar(20),"
        + "accountnumberincome varchar(20),"
        + "timeincome varchar(50),"
        + "projectincome varchar(20),"
        + "memberincome varchar(20),"
        + "noteincome varchar(200));";

    // ��������¼��
    String tablenote = "create table if not exists notepad(" +
            "id integer PRIMARY KEY," +
            "timenote varchar(20)," +
            "contentnote varchar(300));";


    public DatabaseHelper(Context context) {

        // ���ݿ�ʵ�ʱ���������getWritableDatabase()��getReadableDatabase()��������ʱ
        super(context, DATABASE_NAME, null, VERSION);
        // CursorFactory����Ϊnull,ʹ��ϵͳĬ�ϵĹ�����


    }

    /* ����ʱ�䣺���ݿ��һ�δ���ʱonCreate()�����ᱻ����
     * onCreate������һ�� SQLiteDatabase������Ϊ������������Ҫ�������������ͳ�ʼ������
     * �����������Ҫ��ɴ������ݿ������ݿ�Ĳ���
     * ��������޸��������У�ֻҪ���ݿ��Ѿ����������Ͳ����ٽ������onCreate����
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tablepay);

        db.execSQL(tableincome);

        db.execSQL(tablenote);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
	
}
