package com.jackli.financialassistant.welcomepage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jackli.financialassistant.R;

public class GuideActivity extends Activity{
	private List<View> views;
	private ImageView[] dots;	//�ײ�С��ͼƬ
	private int currentIndex;	//��¼��ǰѡ��λ��
	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//ǿ������
		setContentView(R.layout.guide);
		initViews();	// ��ʼ��ҳ��
		initDots();		// ��ʼ���ײ�С��
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);	//���LayoutInflaterʵ��
		views = new ArrayList<View>();
		// ��ʼ������ͼƬ�б�
		views.add(inflater.inflate(R.layout.viewpager_one, null));
		views.add(inflater.inflate(R.layout.viewpager_two, null));
		views.add(inflater.inflate(R.layout.viewpager_three, null));
		views.add(inflater.inflate(R.layout.viewpager_four, null));

		// ��ʼ��Adapter
		vpAdapter = new ViewPagerAdapter(views, this);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(vpAdapter);
		// ��ҳ��ı����
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {	
			
			// ���µ�ҳ�汻ѡ��ʱ����
			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0);	//���õײ�СԲ��Ϊѡ��״̬
			}
			// ����ǰҳ�汻����ʱ����
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			// ������״̬�ı�ʱ����
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.guide_ll);
		dots = new ImageView[views.size()];
		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(false);	// ����Ϊ��ɫ
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(true);// ��ʼ����һ������Ϊ��ɫ����ѡ��״̬
	}

	//���õײ�СԲ��Ϊѡ��״̬
	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);	//�ı�ҳ��󴫽�����λ����Ϊ��ɫ
		dots[currentIndex].setEnabled(false);//֮ǰ����Ϊ��ɫ
		currentIndex = position;
	}

}
