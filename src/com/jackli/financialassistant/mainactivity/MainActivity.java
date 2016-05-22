package com.jackli.financialassistant.mainactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.account.AccountActivity;
import com.jackli.financialassistant.calculator.CalculatorActivity;
import com.jackli.financialassistant.instructions.InstructionsActivity;
import com.jackli.financialassistant.notepad.NotepadActivity;
import com.jackli.financialassistant.util.DBManager;
import com.jackli.financialassistant.util.FontManager;



public class MainActivity extends Activity {

	private DBManager dbManager;
	private Button btn_account;
	private Button btn_law;
	private Button btn_stock;
	private Button btn_notepad;
	private Button btn_instructions;
	private Button btn_calculator;
	private Button btn_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		//�������ݿ�ͱ�
		dbManager = new DBManager(this);
		
		//���뵽���˻
		btn_account = (Button) findViewById(R.id.main_bn_account);
		btn_account.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,AccountActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_activity, R.anim.main_exit);
				finish();
			}
		});
		
		//���뵽�������
		btn_calculator = (Button) this.findViewById(R.id.main_bn_calculator);
		btn_calculator.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_activity, R.anim.main_exit);
				MainActivity.this.finish();
			}
		});

		//���뵽����¼�
		btn_notepad = (Button) this.findViewById(R.id.main_bn_notepad);
		btn_notepad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, NotepadActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_activity, R.anim.main_exit);
				MainActivity.this.finish();
			}
		});
		
		//���뵽˵����
		btn_instructions = (Button) this.findViewById(R.id.main_bn_instructions);
		btn_instructions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_activity, R.anim.main_exit);
				MainActivity.this.finish();
			}
		});
		
		
		btn_law = (Button) this.findViewById(R.id.main_bn_law);
		btn_law.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this, KnowledgeActivity.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.push_activity, R.anim.main_go);
//				MainActivity.this.finish();
			}
		});

		btn_stock = (Button) this.findViewById(R.id.main_bn_stock);
		btn_stock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(MainActivity.this, StockMainActivity.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.push_activity, R.anim.main_go);
//				MainActivity.this.finish();
			}
		});




		btn_exit = (Button)this.findViewById(R.id.main_bn_exit);
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog();
			}
		}
		);
		//��������Ϊ������ͨ����
		FontManager.initTypeface(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFont(vg, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;	//true ��ʾ����˵���ʾ����
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {	//�ظ�����
			dialog();
			return false;
		}
		return false;
	}

	protected void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage("ȷ��Ҫ�˳���");
		builder.setCancelable(true);
		builder.setNegativeButton("ȡ��",  new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
			}
		});
		
		builder.create().show();
		

		
//		AlertDialog.Builder builder = new Builder(MainActivity.this);
//		builder.setMessage("ȷ��Ҫ�˳���?");
//		builder.setTitle("��ʾ");
//		builder.setPositiveButton("ȷ��",
//		new android.content.DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				MainActivity.this.finish();
//			}
//		});
//		builder.setNegativeButton("ȡ��",
//		new android.content.DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		builder.create().show();
	}
	
}
