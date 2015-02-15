package com.billytest.ip;

/**
 * IP�ַ�����ʮ���Ƴ�������������ת���Ĺ�����
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015��2��15�� ����3:13:04
 */
class IpTransUtil {

    /**
     * ip2Dec �ַ���ת��Ϊ������
     * @param ip IP�ַ��� ��ʽΪxxx.xxx.xxx.xxx
     * @return �����͵�IP
     */
    public static Long ip2Dec(String ip) {

        String[] ss = ip.split("\\.");

        Long result = 0L;

        for (int i = 0; i < ss.length; i++) {
            Long t = Long.valueOf(ss[i]);

            Long tmp = t << (3 - i) * 8L;

            result += tmp;
        }

        return result;
    }

    /**
     * dec2Ip ������ת��Ϊ�ַ���
     * @param dec ������ʮ����IP
     * @return ��ʽΪxxx.xxx.xxx.xxx���ַ���
     */
    public static String dec2Ip(Long dec) {

        String result = "";

        Long tmp = new Long(0L);

        for (int i = 3; i >= 0; i--) {

            tmp = (dec / (1 << 8L * i));

            result += tmp;

            dec -= (tmp << 8L * i);

            if (i != 0) result += ".";
        }

        return result;
    }

}
