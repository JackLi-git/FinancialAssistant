package com.jackli.financialassistant.welcomepage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.Constants;
import com.jackli.financialassistant.util.FontManager;

public class WelcomePageActivity extends Activity {
	  private Bitmap bitmap;
	    private ImageView imageView;
	    private Handler handler;
	    private boolean isFirstIn = false;

	    private static final int LOWBRIGHTNESS = -230;  //����һ��������Сֵ
	    private static final double everycut = 5;
	    private int brightness = 70;        //����ֵ

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ǿ������
	        setContentView(R.layout.welcome_page);
	        //����Դ��ȡͼƬ��Bitmap
	        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_android);
	        imageView = (ImageView)findViewById(R.id.iv_welcome);
	        handler = new Handler(){
	            @Override
	            public void handleMessage(Message msg) {
	                switch (msg.what){
	                    case Constants.DECREASE:
	                        brightness -= everycut * 1.05;  //��С���Ȳ�����ֵ
	                        brightChanged(brightness, bitmap, imageView);   //����ͼƬ����Ϊ�µ�ֵ
	                        break;
	                    case Constants.GO_INDEX:
	                        goIndex();
	                        break;
	                    case Constants.GO_GUIDE:
	                        goGuide();
	                        break;
	                }
	                super.handleMessage(msg);
	            }
	        };
	        new Thread(){       //�����ı�ͼƬ���Ȳ������߳�
	            @Override
	            public void run() {
	                while (true) {
	                    handler.sendEmptyMessage(Constants.DECREASE);// Handler��������
	                    if (brightness < LOWBRIGHTNESS) {   //�ж������Ƿ�С�ڶ������Сֵ
	                        init();             //����С�ڶ������Сֵ��ִ�г�ʼ������
	                        break;              //�����߳�
	                    }
	                    try {
	                        Thread.sleep(100);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	                super.run();
	            }
	        }.start();
	        
	        //��������Ϊ������ͨ
	        FontManager.initTypeface(this);
	        ViewGroup vg = FontManager.getContentView(this);
	        FontManager.changeFont(vg,this);
	    }

	    public void init(){
	        SharedPreferences pref = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME,MODE_PRIVATE);
	        isFirstIn = pref.getBoolean("isFirstIn",true);
	        if (isFirstIn){
	            //����ǵ�һ�ν�����Ҫ��ת��Ӧ�õ�����ҳ����Ϣ��������Ϣ���ӳ�ʱ��Ϊ100����
	            handler.sendEmptyMessageDelayed(Constants.GO_GUIDE,Constants.KEEP_INDEX_TIME);
	        }else{
	            //������ǵ�һ�ν�����Ҫ��ת��Ӧ�õ���ҳ����Ϣ��������Ϣ���ӳ�ʱ��Ϊ100����
	            handler.sendEmptyMessageDelayed(Constants.GO_INDEX,Constants.KEEP_INDEX_TIME);
	        }
	    }

	    //?????????????
	    public void brightChanged(int brightness, Bitmap bitmap,ImageView imageView) {
	        //���ͼƬ�ĸߺͿ�
	        int imgHeight = bitmap.getHeight();
	        int imgWidth = bitmap.getWidth();
	        //����һ���µ�Bitmap����
	        Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888); //���ߡ�ɫ��ģʽ
	        ColorMatrix cMatrix = new ColorMatrix();    //������ɫ����
	        cMatrix.set(                                //��������
	                new float[] {
	                        1, 0, 0, 0, brightness, // �ı�����
	                        0, 1, 0, 0, brightness,
	                        0, 0, 1, 0, brightness,
	                        0, 0, 0, 1, 0
	                });

	        Paint paint = new Paint();      //��������
	        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));  //���û��ʵ��˾�Ч��
	        Canvas canvas = new Canvas(bmp);    //��Bitmap���󴴽�����
	        // ��Canvas�ϻ���һ���Ѿ����ڵ�Bitmap��������dstBitmap�ͺ�srcBitmapһ��һ����
	        canvas.drawBitmap(bitmap, 0, 0, paint);
	        imageView.setImageBitmap(bmp);  //��ImageView�м���ͼƬ
	    }

	    private void goIndex() {
	        Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
	        startActivity(intent);
	        finish();
	    }

	    private void goGuide() {
	        Intent intent = new Intent(WelcomePageActivity.this, GuideActivity.class);
	        startActivity(intent);
	        finish();
	    }
}
