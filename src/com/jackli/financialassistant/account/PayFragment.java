package com.jackli.financialassistant.account;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.jackli.financialassistant.R;
import com.jackli.financialassistant.util.DBManager;
import com.jackli.financialassistant.util.FontManager;


public class PayFragment extends Fragment {
	View view;
	private TextView tv_payMoney;
	private TextView tv_payCategory;
	private TextView tv_payAccount;
	private TextView tv_payDate;
	private TextView tv_payProject;
	private TextView tv_payMember;
	private TextView tv_payTime;
	
	private EditText et_payMoney;
	private EditText et_payNote;
	
	private Button btn_paySave;
	private Button btn_payCheck;
	
	private Spinner sp_payCategory;
	private Spinner sp_payAccount;
	private Spinner sp_payProject;
	private Spinner sp_payMember;
	
	private String payCategoryStr;
	private String payAccountStr;
	private String payProjectStr;
	private String payMemberStr;
	private ArrayAdapter<CharSequence> adapterPayCate= null;
	private ArrayAdapter<CharSequence> adapterPayAccount= null;
	private ArrayAdapter<CharSequence> adapterPayProject= null;
	private ArrayAdapter<CharSequence> adapterPayMember= null;
	
	// ���ϵͳ��ǰ������
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public String date = format.format(new java.util.Date());
	
	//???????????
	//DatePickerDialog.OnDateSetListener OnDateSetListener;
	
	private boolean flag = false;	//�ж�������ݿ��Ƿ�ɹ�������
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view= inflater.inflate(R.layout.account_pay, container, false);
		
	    
		//��View�пؼ��Ĳ�������
		tv_payMoney =(TextView) view.findViewById(R.id.pay_Text_money);
		tv_payCategory =(TextView) view.findViewById(R.id.pay_Text_category);
		tv_payAccount =(TextView) view.findViewById(R.id.pay_Text_zhanghu);
		tv_payDate =(TextView) view.findViewById(R.id.pay_Text_time);
		tv_payProject =(TextView) view.findViewById(R.id.pay_Text_project);
		tv_payMember =(TextView) view.findViewById(R.id.pay_Text_member);
		et_payMoney = (EditText) view.findViewById(R.id.money_et1);
		et_payNote = (EditText) view.findViewById(R.id.pay_et_note);
		btn_paySave = (Button) view.findViewById(R.id.btn_pay_save);
		btn_payCheck = (Button) view.findViewById(R.id.btn_pay_check);
		
		//��������
		tv_payMoney.setTypeface(FontManager.typeface);
		tv_payCategory.setTypeface(FontManager.typeface);
		tv_payAccount.setTypeface(FontManager.typeface);
		tv_payDate.setTypeface(FontManager.typeface);
		tv_payProject.setTypeface(FontManager.typeface);
		tv_payMember.setTypeface(FontManager.typeface);
		et_payMoney.setTypeface(FontManager.typeface);
		et_payNote.setTypeface(FontManager.typeface);
		btn_paySave.setTypeface(FontManager.typeface);
		btn_paySave.setTypeface(FontManager.typeface);
		
		InitPayCategorySp();
		InitPayAccountSp();
		InitPayTimetv();
		InitPayProjectSp();
		InitPayMemberSp();

		//֧�����������
		btn_paySave.setTypeface(FontManager.typeface);
		btn_paySave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] pay_content = new String[] {
						et_payMoney.getText().toString(), payCategoryStr,
						payAccountStr, tv_payTime.getText().toString(),
						payProjectStr, payMemberStr,
						et_payNote.getText().toString() };
				if (et_payMoney.getText().toString().length() == 0) {
					Toast toast = Toast.makeText(getActivity(),"���������ѽ��", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else {
					flag = DBManager.addPay(pay_content);
					Toast toast;
					if (flag) {
						toast = Toast.makeText(getActivity(), "��ӳɹ�",Toast.LENGTH_SHORT);
						//toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						et_payMoney.setText("");
						sp_payCategory.setSelection(0, true);
						sp_payAccount.setSelection(0, true);
						sp_payProject.setSelection(0, true);
						sp_payMember.setSelection(0, true);
						et_payNote.setText("");
					} else {
						toast = Toast.makeText(getActivity(), "���ʧ��",Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
			}
		});

		btn_payCheck.setTypeface(FontManager.typeface);
		btn_payCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PayListActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		Log.d("Account", "payFragment return view");
		return view;
	}
	
	// ��ʼ��֧��ҳ����spinner
	private void InitPayMemberSp() {
		sp_payMember = (Spinner) view.findViewById(R.id.pay_member_sp);
		// ʵ������ArrayAdapter 
		adapterPayMember =ArrayAdapter.createFromResource(getActivity(), 
				R.array.Member, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterPayMember.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_payMember.setAdapter(adapterPayMember);
		sp_payMember.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				payMemberStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
	}
	
	

	private void InitPayProjectSp() {
		sp_payProject = (Spinner) view.findViewById(R.id.pay_project_sp);
		// ʵ������ArrayAdapter 
		adapterPayProject =ArrayAdapter.createFromResource(getActivity(), 
				R.array.Project, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterPayProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_payProject.setAdapter(adapterPayProject);
		sp_payProject.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				payProjectStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
		
	}


	private void InitPayAccountSp() {
		sp_payAccount = (Spinner) view.findViewById(R.id.pay_zhanghu_sp);
		// ʵ������ArrayAdapter 
		adapterPayAccount =ArrayAdapter.createFromResource(getActivity(), 
				R.array.Account, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterPayAccount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_payAccount.setAdapter(adapterPayAccount);
		sp_payAccount.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				payAccountStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
		
	}

	private void InitPayCategorySp() {

		sp_payCategory = (Spinner)view.findViewById(R.id.pay_category_sp);
		// ʵ������ArrayAdapter 
		adapterPayCate = new ArrayAdapter<CharSequence>(getActivity(), R.array.pay_Category, R.layout.spinner_item);
		adapterPayCate =ArrayAdapter.createFromResource(getActivity(), 
				R.array.pay_Category, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterPayCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_payCategory.setAdapter(adapterPayCate);
		sp_payCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				payCategoryStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
	}
	
	// ��ʼ������ѡ����
	private void InitPayTimetv() {
		tv_payTime = (TextView) view.findViewById(R.id.tv_time1);
		tv_payTime.setText("����:");
		tv_payTime.setTypeface(FontManager.typeface);

		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		String Week = "����";
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "��";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "һ";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "��";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "��";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "��";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "��";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "��";
		}

		tv_payTime.setText(date + " " + Week);
		tv_payTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}

			
		});
	}
	
	// ֧������ѡ��������
	private void showDatePickerDialog() {
		Calendar localCalendar = Calendar.getInstance();
		int year = localCalendar.get(Calendar.YEAR);
		int month = localCalendar.get(Calendar.MONTH);
		int day = localCalendar.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog date_dialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				String sTime = year + "-" + (monthOfYear + 1) + "-"
						+ dayOfMonth;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				try {
					c.setTime(format.parse(sTime));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				String Week = "����";
				if (c.get(Calendar.DAY_OF_WEEK) == 1) {
					Week += "��";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 2) {
					Week += "һ";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 3) {
					Week += "��";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 4) {
					Week += "��";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 5) {
					Week += "��";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 6) {
					Week += "��";
				}
				if (c.get(Calendar.DAY_OF_WEEK) == 7) {
					Week += "��";
				}
				tv_payTime.setText(sTime + " " + Week);
			}
		}, year, month, day);
		
		date_dialog.show();
	}
	
}
