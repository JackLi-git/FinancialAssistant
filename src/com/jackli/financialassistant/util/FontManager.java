package com.jackli.financialassistant.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontManager {
    public static Typeface typeface;    // �����Զ�������

    //��ʼ������(������ͨ����)
    public static void initTypeface(Activity activity){
        if (typeface == null){
            typeface = Typeface.createFromAsset(activity.getAssets(),"fonts/newfont.ttf");
        }
    }

    // ת������
    public static void changeFont(ViewGroup viewGroup,Activity activity){
        View view;
        for (int i = 0;i < viewGroup.getChildCount();i++ ){
            view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {             //instanceof��Java��һ����Ԫ����������==��>��<��ͬһ�ණ����������������ĸ��ɵģ�
                ((TextView)view).setTypeface(typeface); // ����Ҳ��Java�ı����ؼ��֡����������ǲ�������ߵĶ����Ƿ������ұߵ����ʵ����
            }else if (view instanceof EditText){        // ����boolean���͵����ݡ�
                ((EditText)view).setTypeface(typeface);
            }else if (view instanceof Button){
                ((Button) view).setTypeface(typeface);
            }else if (view instanceof ViewGroup) {
                changeFont((ViewGroup) view, activity);
            }
        }
    }

    //��ô�������activity,����activity�����ݴ���0�������еĿؼ�����ViewGroup�����ȡ�ÿؼ�������
    public static ViewGroup getContentView(Activity activity) {
        ViewGroup systemContent = (ViewGroup) activity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        ViewGroup content = null;
        if (systemContent.getChildCount() > 0
                && systemContent.getChildAt(0) instanceof ViewGroup) {
            content = (ViewGroup) systemContent.getChildAt(0);
        }
        return content;
    }
    

}
