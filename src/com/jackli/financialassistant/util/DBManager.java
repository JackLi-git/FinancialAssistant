package com.jackli.financialassistant.util;


import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
    public DatabaseHelper helper;
    public static SQLiteDatabase db;

    /* ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,mFactory);
     * ��Ҫһ��context���� ,����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
     */
    public DBManager(Context context) {
        helper = new DatabaseHelper(context);
        Log.d("main", "new  DBManager ");
        db = helper.getWritableDatabase();
    }


    // -------------------------------֧��
    // begin----------------------------------------------
    // ���֧��
    public static boolean addPay(String[] info){
        Cursor cursor =null;
        int payId = -1;
        ContentValues values = new ContentValues();
        try {
            String sql = "select max(id) from accountpay;";
            cursor = db.rawQuery(sql,null);
            if (cursor.moveToFirst()){
                payId = cursor.getInt(0) + 1;
            }else{
                payId = 1;
            }
            Log.d("DBManager", "addPay payId = "+payId);
        }catch (Exception e){
        	Log.d("DBManager","addPay Exception111");
            e.printStackTrace();
        }
        try {
        	for (int i = 0; i < info.length; i++) {
        		Log.d("DBManager", "addPay info[]= "+info[i]);
			}
        	Log.d("DBManager", "1");
            values.put("id",payId);
            Log.d("DBManager", "2");
            values.put("moneypay",info[0]);
            values.put("categorypay",info[1]);
            values.put("accountnumberpay",info[2]);
            values.put("timepay",info[3]);
            values.put("projectpay",info[4]);
            values.put("memberpay",info[5]);
            values.put("notepay",info[6]);
            db.insert("accountpay", null, values);
            Log.d("DBManager","addPay return true");
            return true;
        }catch (Exception e){
        	Log.d("DBManager","addPay Exception222");
            e.printStackTrace();
        }finally {
        	Log.d("DBManager","addPay finally");
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.d("DBManager","addPay return false");
        return false;  //ΪʲôҪreturn false---------���ڻ�ж��Ƿ���ӳɹ�
    }

    // ���֧�����
    public static String[] getMoneyPay() {
        Cursor cursor = null;
        String[] money = null;
        int num = 0;
        try {
            cursor = db.query("accountpay",null,null,null,null,null,null);
            num = cursor.getCount();
            money = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
                money[count++] = cursor.getString(cursor.getColumnIndex("moneypay"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return money;
    }

    // ���֧������
    public static String[] getCategoryPay() {
        Cursor cursor = null;
        String[] category = null;
        int num = 0;
        try {
            cursor = db.query("accountpay",null,null,null,null,null,null);
            num = cursor.getCount();
            category = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
                category[count++] = cursor.getString(cursor.getColumnIndex("categorypay"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return category;
    }

    // ���֧��ʱ��
    public static String[] getTimePay() {
    	Log.d("DBManager","getTimePay");
        Cursor cursor = null;
        String[] time = null;
        int num = 0;
        try {
        	Log.d("DBManager","getTimePay111");
            cursor = db.query("accountpay",null,null,null,null,null,null);
            num = cursor.getCount();
            time = new String[num];
            Log.d("DBManager","getTimePay num = "+num);
            Log.d("DBManager","getTimePay Strig[]time = "+time);
            int count = 0;
            while (cursor.moveToNext()){
                time[count++] = cursor.getString(cursor.getColumnIndex("timepay"));
            }
        } catch (Exception e) {
        	Log.d("DBManager", "getTimePay Exception");
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        Log.d("DBManager","getTimePay num = "+num);
        Log.d("DBManager","getTimePay ewturn times = "+time);
        return time;
    }

    //���֧��id
    public static int[] getIdPay(){
    	 Cursor cursor = null;
         int[] ids = null;
         int num = 0;
         try {
             cursor = db.query("accountpay",null,null,null,null,null,null);
             num = cursor.getCount();
             ids = new int[num];
             int count = 0;
             while (cursor.moveToNext()){
            	 ids[count++] = cursor.getInt(cursor.getColumnIndex("id"));
             }
         } catch (Exception e) {
         	Log.d("DBManager", "getTimePay Exception");
             e.printStackTrace();
         } finally {
             if (cursor != null) {
                 cursor.close();
             }

         }
         Log.d("DBManager","getIdPay ewturn id= "+ids);
         return ids;
    }

    // ɾ��֧��   ����id��ɾ��ĳ��֧��
    public static boolean deletePayList(int id) {
    	try {
 			db.delete("accountpay","id = ?",new String[]{id+""});
 			return true;
 		} catch (Exception e) {
 			e.printStackTrace();
 		} 
 		return false;
    }

    
    // ͨ��ƥ��ʱ�䡢���ࡢ�������ö�Ӧ��֧����Ϣ��ͨ�����鷵��
 	public static String[] getPayListStr(String tStr, String cStr, String mStr) {
 		Cursor cursor = null;
 		String payStr[] = null;
 		try {
 			Log.d("DBManager", "getPayListStr 1111");
 			cursor = db.query("accountpay", null, "timepay = ? and categorypay = ? and moneypay = ?",
 						new String[]{tStr,cStr,mStr},null, null, null);
 			Log.d("DBManager", "getPayListStr 2222");
 			if (cursor.moveToFirst()) {
 				Log.d("DBManager", "getPayListStr 333");
 				payStr = new String[cursor.getColumnCount()];
 				for (int i = 0; i < cursor.getColumnCount(); i++) {
 					payStr[i] = cursor.getString(i);
 				}
 				if (payStr[6] == null) {
 					payStr[6] = "��";
 				}
 			}
 		} catch (Exception e) {
 			Log.d("DBManager", "getPayListStr Exception");
 			e.printStackTrace();
 		} finally {
 			if (cursor != null) {
 				cursor.close();
 			}
 		}
 		return payStr;
 	}

 
    
    
    // -------------------------------֧��
    // end----------------------------------------------

 	
    // -------------------------------����
    // begin----------------------------------------------
    // �������
    public static boolean addIncome(String[] info){
        Cursor cursor =null;
        int incomeId = -1;
        ContentValues values = new ContentValues();
        try {
            String sql = "select max(id) from accountincome;";
            cursor = db.rawQuery(sql,null);
            if (cursor.moveToFirst()){
                incomeId = cursor.getInt(0) + 1;
            }else{
                incomeId = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            values.put("id",incomeId);
            values.put("moneyincome",info[0]);
            values.put("categoryincome",info[1]);
            values.put("accountnumberincome",info[2]);
            values.put("timeincome",info[3]);
            values.put("projectincome",info[4]);
            values.put("memberincome",info[5]);
            values.put("noteincome",info[6]);
            db.insert("accountincome", null, values);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;  
    }

    // ���������
    public static String[] getMoneyIncome() {
        Cursor cursor = null;
        String[] money = null;
        int num = 0;
        try {
            cursor = db.query("accountincome",null,null,null,null,null,null);
            num = cursor.getCount();
            money = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
                money[count++] = cursor.getString(cursor.getColumnIndex("moneyincome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return money;
    }

    // �����������
    public static String[] getCategoryIncome() {
        Cursor cursor = null;
        String[] category = null;
        int num = 0;
        try {
            cursor = db.query("accountincome",null,null,null,null,null,null);
            num = cursor.getCount();
            category = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
                category[count++] = cursor.getString(cursor.getColumnIndex("categoryincome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return category;
    }

    // �������ʱ��
    public static String[] getTimeIncome() {
        Cursor cursor = null;
        String[] time = null;
        int num = 0;
        try {
            cursor = db.query("accountincome",null,null,null,null,null,null);
            num = cursor.getCount();
            time = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
                time[count++] = cursor.getString(cursor.getColumnIndex("timeincome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return time;
    }

	// ���֧��id
	public static int[] getIdIncome() {
		Cursor cursor = null;
		int[] ids = null;
		int num = 0;
		try {
			cursor = db.query("accountincome", null, null, null, null, null, null);
			num = cursor.getCount();
			ids = new int[num];
			int count = 0;
			while (cursor.moveToNext()) {
				ids[count++] = cursor.getInt(cursor.getColumnIndex("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}

		}
		Log.d("DBManager", "getIdIncome return id= " + ids);
		return ids;
	}

	// ɾ��֧��
	public static boolean deleteIncomeList(int id) {
		try {
			db.delete("accountincome", "id = ?", new String[] { id + "" });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// ͨ��ƥ��ʱ�䡢���ࡢ�������ö�Ӧ��֧����Ϣ��ͨ�����鷵��
	public static String[] getIncomeList(String tStr, String cStr, String mStr) {
		Cursor cursor = null;
		String incomeStr[] = null;
		try {
			cursor = db.query("accountincome", null,"timeincome = ? and categoryincome = ? and moneyincome = ?",
					new String[] { tStr, cStr, mStr }, null, null, null);
			if (cursor.moveToFirst()) {
				incomeStr = new String[cursor.getColumnCount()];
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					incomeStr[i] = cursor.getString(i);
				}
				if (incomeStr[6] == null) {
					incomeStr[6] = "��";
				}
			}
		} catch (Exception e) {
			Log.d("DBManager", "getIncoemList Exception");
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return incomeStr;
	}

    // ===========================================����
    // end======================================================================


    // ============================================����¼
    // begin===========================================================================
    // ��ӱ���¼
    public static boolean addNotepad(String[] info) {
        Cursor cursor =null;
        int noteId = -1;
        ContentValues values = new ContentValues();
        try {
            String sql = "select max(id) from notepad;";
            cursor = db.rawQuery(sql,null);
            if (cursor.moveToFirst()){
                noteId = cursor.getInt(0) + 1;
            }else{
                noteId = 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            values.put("id",noteId);
            values.put("timenote",info[0]);
            values.put("contentnote",info[1]);
            db.insert("notepad", null, values);
            return true; 
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false; 
    }


    // ��ü���ʱ��
    public static String[] getDateNote() {
        Cursor cursor = null;
        String[] date = null;
        int num = 0;
        try {
            cursor = db.query("notepad",null,null,null,null,null,null);
            num = cursor.getCount();
            date = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
            	date[count++] = cursor.getString(cursor.getColumnIndex("timenote"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return date;
    }


    // ��ü�������
    public static String[] getContentNote() {
        Cursor cursor = null;
        String[] content = null;
        int num = 0;
        try {
            cursor = db.query("notepad",null,null,null,null,null,null);
            num = cursor.getCount();
            content = new String[num];
            int count = 0;
            while (cursor.moveToNext()){
                content[count++] = cursor.getString(cursor.getColumnIndex("contentnote"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return content;
    }

    // ����ض��ļ�¼
//    public static String[] getNotepadList(String time, String content){
//    	Cursor cursor = null;
//		String noteStr[] = null;
//		try {
//			cursor = db.query("notepad", null,"time = ? and content = ? and moneyincome = ?",
//					new String[] { tStr, cStr, mStr }, null, null, null);
//			if (cursor.moveToFirst()) {
//				incomeStr = new String[cursor.getColumnCount()];
//				for (int i = 0; i < cursor.getColumnCount(); i++) {
//					incomeStr[i] = cursor.getString(i);
//				}
//				if (incomeStr[6] == null) {
//					incomeStr[6] = "��";
//				}
//			}
//		} catch (Exception e) {
//			Log.d("DBManager", "getIncoemList Exception");
//			e.printStackTrace();
//		} finally {
//			if (cursor != null) {
//				cursor.close();
//			}
//		}
//		return incomeStr;
//    	
//    }

   
    
  //���֧��id
    public static int[] getIdNote(){
    	 Cursor cursor = null;
         int[] ids = null;
         int num = 0;
         try {
             cursor = db.query("notepad",null,null,null,null,null,null);
             num = cursor.getCount();
             ids = new int[num];
             int count = 0;
             while (cursor.moveToNext()){
            	 ids[count++] = cursor.getInt(cursor.getColumnIndex("id"));
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (cursor != null) {
                 cursor.close();
             }
         }
         return ids;
    }

    // ɾ��֧��   ����time category money ��ɾ��ĳ��֧��
    public static boolean deleteNoteList(int id) {
    	try {
 			db.delete("notepad","id = ?",new String[]{id+""});
 			return true;
 		} catch (Exception e) {
 			e.printStackTrace();
 		} 
 		return false;
    }

    // -------------------------------����¼
    // end------------------------------------------------




}


