package com.jackli.financialassistant.calculator;

public class Judge {
	
	public String judge(String string, String c) {
		switch (string.charAt(string.length() - 1)) {
		case '+':
		case '-':
		case '��':
		case '��':
			//������һ���ַ��Ѿ���������ˣ����Ϊ��������������
			string = string.substring(0, string.length() - 1) + c;
			break;
		default:
			//����ֱ�����������
			string += c;
			break;
		}
		return string;
	}

//	public static String dispose(String string) {
//		int leng = string.length() - 1;
//		Character character;
//		if (0 == leng) {
//			return "error";
//		}
//		for (int i = 0; i < leng; i++) {
//			character = string.charAt(i);
//			if (Character.isLetter(character)) {
//				return "error";
//			}
//		}
//
//		return string;
//	}

	//�ж���ʲô����£������ֺ���ӡ�.��
	//ֻ�е�ǰ�����е�������һ������������С���������µ����.���Ż��ں�����ϡ�.��
	public String judgePoint(String string) {	//123-->123.   123+456-->123+456.
		int p = string.length() - 1;
		boolean flag = true;
		//��ȡ���һ���ַ�
		Character tmp = string.charAt(p);
		//����ܳ���Ϊ1���������һ����λ������ֱ���ں���ӡ�.��
		if (0 == p){
			string += ".";
		}
		//������һ���������ҳ��Ȳ�Ϊ1������1��
		if (Character.isDigit(tmp) && 0 != p) {
			while (flag) {
				//�����һ����ʽ�������а����������ֵ�������Ҳ��ǡ�.����(�һ�û�˳�˵��������������м�û�С�.��)���ں�����ϡ�.��
				if (!Character.isDigit(tmp)) {
					flag = false;
					if (tmp != '.')
						string += ".";
				}
				if (0 == --p && (tmp != '.')) {
					string += ".";
					break;
				}
				tmp = string.charAt(p);
			}
		}
		return string;
	}

	//�ж��Ƿ������������
	public static boolean isMathOperator(Character c) {
		switch (c) {
		case '+':
		case '-':
		case '��':
		case '��':
			return true;
		default:
			return false;
		}
	}

	//�ж�������Ƿ�Ϊ����
	public String digit_judge(String string, String c, boolean flag) {
		//�����0�����Ǹոա�=�������ֵ����ֱ�Ӹ��ǣ������ں�����ϸ�����
		if ("0".equals(string)) {
			string = c;
		} else if (flag) {
			string = c;
		} else
			string += c;
		return string;
	}

	//��������ж�
	public String digit_dispose(String string) {
		if ("error".equals(string)) {
			return string;
		}
		Double double1 = new Double(string);	//�ַ���ת����Double����
		if (double1 > 999999999999999.0)	//�������ֵ
			return "��";
//		long l = (long) (double1 * 1e4);
//		double1 = (Double) (l / 1e4);
		string = "" + double1;
		return string;
	}
}
