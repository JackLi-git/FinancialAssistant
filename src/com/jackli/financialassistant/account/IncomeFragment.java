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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.util.DBManager;
import com.jackli.financialassistant.util.FontManager;

public class IncomeFragment extends Fragment {
	
	View view;
	private TextView tv_incomeMoney;
	private TextView tv_incomeCategory;
	private TextView tv_incomeAccount;
	private TextView tv_incomeDate;
	private TextView tv_incomeProject;
	private TextView tv_incomeMember;
	private TextView tv_incomeTime;
	
	private EditText et_incomeMoney;
	private EditText et_incomeNote;
	
	private Button btn_incomeSave;
	private Button btn_incomeCheck;
	
	private Spinner sp_incomeCategory;
	private Spinner sp_incomeAccount;
	private Spinner sp_incomeProject;
	private Spinner sp_incomeMember;
	
	private String incomeCategoryStr;
	private String incomeAccountStr;
	private String incomeProjectStr;
	private String incomeMemberStr;
	private ArrayAdapter<CharSequence> adapterincomeCate= null;
	private ArrayAdapter<CharSequence> adapterincomeAccount= null;
	private ArrayAdapter<CharSequence> adapterincomeProject= null;
	private ArrayAdapter<CharSequence> adapterincomeMember= null;
	
	// ���ϵͳ��ǰ������
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public String date = format.format(new java.util.Date());
	
	//???????????
	//DatePickerDialog.OnDateSetListener OnDateSetListener;
	
	private boolean flag = false;	//�ж�������ݿ��Ƿ�ɹ�������
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view= inflater.inflate(R.layout.account_income, container, false);
		
	    
		//��View�пؼ��Ĳ�������
		tv_incomeMoney =(TextView) view.findViewById(R.id.income_Text_money);
		tv_incomeCategory =(TextView) view.findViewById(R.id.income_Text_category);
		tv_incomeAccount =(TextView) view.findViewById(R.id.income_Text_zhanghu);
		tv_incomeDate =(TextView) view.findViewById(R.id.income_Text_time);
		tv_incomeProject =(TextView) view.findViewById(R.id.income_Text_project);
		tv_incomeMember =(TextView) view.findViewById(R.id.income_Text_member);
		et_incomeMoney = (EditText) view.findViewById(R.id.money_et2);
		et_incomeNote = (EditText) view.findViewById(R.id.income_et_note);
		btn_incomeSave = (Button) view.findViewById(R.id.btn_income_save);
		btn_incomeCheck = (Button) view.findViewById(R.id.btn_income_check);
		
		//��������
		tv_incomeMoney.setTypeface(FontManager.typeface);
		tv_incomeCategory.setTypeface(FontManager.typeface);
		tv_incomeAccount.setTypeface(FontManager.typeface);
		tv_incomeDate.setTypeface(FontManager.typeface);
		tv_incomeProject.setTypeface(FontManager.typeface);
		tv_incomeMember.setTypeface(FontManager.typeface);
		et_incomeMoney.setTypeface(FontManager.typeface);
		et_incomeNote.setTypeface(FontManager.typeface);
		btn_incomeSave.setTypeface(FontManager.typeface);
		btn_incomeSave.setTypeface(FontManager.typeface);
		
		InitincomeCategorySp();
		InitincomeAccountSp();
		InitincomeTimetv();
		InitincomeProjectSp();
		InitincomeMemberSp();

		//֧�����������
		btn_incomeSave.setTypeface(FontManager.typeface);
		btn_incomeSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] income_content = new String[] {
						et_incomeMoney.getText().toString(), incomeCategoryStr,
						incomeAccountStr, tv_incomeTime.getText().toString(),
						incomeProjectStr, incomeMemberStr,
						et_incomeNote.getText().toString() };
				if (et_incomeMoney.getText().toString().length() == 0) {
					Toast toast = Toast.makeText(getActivity(),"���������ѽ��", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
				} else {
					flag = DBManager.addIncome(income_content);
					Toast toast;
					if (flag) {
						toast = Toast.makeText(getActivity(), "��ӳɹ�",Toast.LENGTH_SHORT);
						//toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						et_incomeMoney.setText("");
						sp_incomeCategory.setSelection(0, true);
						sp_incomeAccount.setSelection(0, true);
						sp_incomeProject.setSelection(0, true);
						sp_incomeMember.setSelection(0, true);
						et_incomeNote.setText("");
					} else {
						toast = Toast.makeText(getActivity(), "���ʧ��",Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
			}
		});

		btn_incomeCheck.setTypeface(FontManager.typeface);
		btn_incomeCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), IncomeListActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		Log.d("Account", "incomeFragment return view");
		return view;
	}
	
	// ��ʼ��֧��ҳ����spinner
	private void InitincomeMemberSp() {
		sp_incomeMember = (Spinner) view.findViewById(R.id.income_member_sp);
		// ʵ������ArrayAdapter 
		adapterincomeMember =ArrayAdapter.createFromResource(getActivity(), 
				R.array.Member, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterincomeMember.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_incomeMember.setAdapter(adapterincomeMember);
		sp_incomeMember.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				incomeMemberStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
	}
	
	

	private void InitincomeProjectSp() {
		sp_incomeProject = (Spinner) view.findViewById(R.id.income_project_sp);
		// ʵ������ArrayAdapter 
		adapterincomeProject =ArrayAdapter.createFromResource(getActivity(), 
				R.array.Project, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterincomeProject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_incomeProject.setAdapter(adapterincomeProject);
		sp_incomeProject.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				incomeProjectStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
		
	}


	private void InitincomeAccountSp() {
		sp_incomeAccount = (Spinner) view.findViewById(R.id.income_zhanghu_sp);
		// ʵ������ArrayAdapter 
		adapterincomeAccount =ArrayAdapter.createFromResource(getActivity(), 
				R.array.Account, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterincomeAccount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_incomeAccount.setAdapter(adapterincomeAccount);
		sp_incomeAccount.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				incomeAccountStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
		
	}

	private void InitincomeCategorySp() {

		sp_incomeCategory = (Spinner)view.findViewById(R.id.income_category_sp);
		// ʵ������ArrayAdapter 
		adapterincomeCate = new ArrayAdapter<CharSequence>(getActivity(), R.array.income_Category, R.layout.spinner_item);
		adapterincomeCate =ArrayAdapter.createFromResource(getActivity(), 
				R.array.income_Category, R.layout.spinner_item);
		//����չ����ʱ�������˵�����ʽ
		adapterincomeCate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_incomeCategory.setAdapter(adapterincomeCate);
		sp_incomeCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv =(TextView) view;
				tv.setTypeface(FontManager.typeface);
				incomeCategoryStr = parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

		});
	}
	
	// ��ʼ������ѡ����
	private void InitincomeTimetv() {
		tv_incomeTime = (TextView) view.findViewById(R.id.tv_time2);
		tv_incomeTime.setText("����:");
		tv_incomeTime.setTypeface(FontManager.typeface);

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

		tv_incomeTime.setText(date + " " + Week);
		tv_incomeTime.setOnClickListener(new OnClickListener() {
			
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
				tv_incomeTime.setText(sTime + " " + Week);
			}
		}, year, month, day);
		
		date_dialog.show();
	}
}
