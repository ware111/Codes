package com.baidu.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.baidu.sqlite.SqliteUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * 此类主要的作用是向服务器发送查询请求
 * 及接收来自服务器的查询结构
 * 
 * */
public class MainActivity extends Activity {
	Socket socket;
	EditText edit;
	Button send;
	String string;
	String news;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                  new MyThread().start();
                  db = SqliteUtils.db;
                  ContentValues values = new ContentValues();
                  values.put("id", 1);
                  values.put("values", news);
                  db.insert("data_table", null, values);
                  edit.setText(news);
			}
		});
	}
	
	class MyThread extends Thread{
			@Override
			public void run() {
				// TODO Auto-generated method stub
			//	string = edit.getText().toString();
				try {
					InetAddress dstAddress = InetAddress.getByName("10.1.7.34");
					socket = new Socket(dstAddress, 10000);
					string = edit.getText().toString();
					send(string);
					news = receive();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	/**
	 * 发送信息
	 * @param str
	 *           发送的信息
	 */
	public void send(String str){
		try {
			Log.v("4444", socket + "");
			OutputStream out = socket.getOutputStream();
			Log.v("2222", "socket");
			OutputStreamWriter output = new OutputStreamWriter(out);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *接收数据 
	 *@return 接收到的信息
	 *
	 */
	public String receive(){
		InputStream in;
		String data = "";
		try {
			in = socket.getInputStream();
			InputStreamReader input = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(input);
			data = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 初始化组件
	 * 
	 */
	public void init(){
		edit = (EditText)findViewById(R.id.edit);
		string = new String("");
		send = (Button)findViewById(R.id.btn);
	}
}
