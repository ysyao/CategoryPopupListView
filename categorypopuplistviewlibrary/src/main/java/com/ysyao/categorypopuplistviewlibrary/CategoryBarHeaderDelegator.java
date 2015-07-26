package com.ysyao.categorypopuplistviewlibrary;

import android.view.View;

public interface CategoryBarHeaderDelegator {
    /**
     * ���delegator��ר��Ϊ��categorybar���趨�ģ�Ϊ���ǵ�popup windowչʾ��ʱ����һ���ص�
     * �������Ե���category bar��������Ӧ�ķ�����Ʃ�統չʾpopup window��ʱ��category bar����ı���
     * ���ܻ��ò�ͬ��ɫ����ʾ��ѡ�У����ʱ�����Ҫ�ڴ˷������������Ӧ����Ϊ��
     * @param view ����view����ʵ��TextView����ֱ��ת�ͣ�����ͼƬ�����޸�������ɫ��
     */
    void onShowing(View view);

    /**
     * ���delegator��ר��Ϊ��categorybar���趨�ģ�Ϊ���ǵ�popup window��ʧ��ʱ����һ���ص�
     * �������Ե���category bar��������Ӧ�ķ�����Ʃ�統popup window��ʧ��ʱ��category bar����ı���
     * ���ܴ�ѡ����ɫ�����ǰ����ɫ�����ʱ�����Ҫ�ڴ˷������������Ӧ����Ϊ��
     * @param view ����view����ʵ��TextView����ֱ��ת�ͣ�����ͼƬ�����޸�������ɫ��
     */
    void onDismissing(View view);
}
