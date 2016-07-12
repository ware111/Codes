package com.baidu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * 
 * 此类主要作用是创建数据库及表
 * 
 */
public class BuildDB {
    static String CREATE_TABLE = "create table IF NOT EXIST data_table (id numeric primary, value varchar[10])";
	public static void buildDB(Context context){
	InforSqlite sql = new InforSqlite(context, "data.db", null, 1);
	SQLiteDatabase db = sql.getWritableDatabase();
	SqliteUtils.db = db;
	db.execSQL(CREATE_TABLE);
	}
}
