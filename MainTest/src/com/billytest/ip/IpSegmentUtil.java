package com.billytest.ip;

import java.util.ArrayList;
import java.util.List;

/**
 * ip�ι�����
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015��2��15�� ����2:59:46
 */
public class IpSegmentUtil {

    /**
     * getMask ����IP�λ�ȡ��������
     * @param ipSegment ip�Σ���ʽΪ192.168.1.1/24
     * @return ��������
     * @throws MaskErrorException ������λ�����Ϸ������׳��쳣
     */
    public static String getMask(String ipSegment) throws MaskErrorException {

        String[] ips = ipSegment.split("/");

        if (ips.length < 2) throw new MaskErrorException();

        String maskNum = ips[1];

        if (Integer.valueOf(maskNum) < 1 || Integer.valueOf(maskNum) > 32) {
            throw new MaskErrorException();
        }

        long mask = ((((long) Math.pow(2D, Double.valueOf(maskNum))) - 1L) << (32 - Integer
                .valueOf(maskNum)));

        return IpTransUtil.dec2Ip(mask);
    }

    /**
     * getExtremeIp ����ip�λ�ȡ��βIP
     * @param ipSegment ip�Σ���ʽΪ192.168.1.1/24
     * @return ����λIP��ɵ�list��list[0]Ϊ��IP��list[1]ΪβIP
     * @throws MaskErrorException ������λ�����Ϸ������׳��쳣
     */
    public static List<String> getExtremeIp(String ipSegment) throws MaskErrorException {

        String ip = ipSegment.split("/")[0];

        String mask = getMask(ipSegment);

        Long ipLong = IpTransUtil.ip2Dec(ip);

        Long maskLong = IpTransUtil.ip2Dec(mask);

        /*
         * Long.lowestOneBit(maskLong) - 1 Ϊ ����Ĳ��� ip������Ĳ���ΪIP�������һ��IP
         */
        Long last = ipLong | (Long.lowestOneBit(maskLong) - 1);

        /*
         * ip������ΪIP���е�һ��IP
         */
        Long first = ipLong & maskLong;

        List<String> list = new ArrayList<String>();

        String firstIp = String.valueOf(IpTransUtil.dec2Ip(first));
        String lastIp = String.valueOf(IpTransUtil.dec2Ip(last));

        /*
         * ȥ���㲥��ַ�������
         */
        if (firstIp.endsWith(".0")) firstIp = firstIp.replaceAll("0$", "1");

        if (lastIp.endsWith(".255")) lastIp = lastIp.replaceAll("255$", "254");

        list.add(firstIp);
        list.add(lastIp);

        return list;
    }

    /**
     * getIpSegmentSize ����IP����βIP��ȡIP������IP����
     * @param firstIp ��λIP
     * @param lastIp βλIP
     * @return IP��������IP����
     */
    public static Integer getIpSegmentSize(String firstIp, String lastIp) {

        return getIpList(firstIp, lastIp).size();

    }

    /**
     * getIpList ������βIP��ȡ����IPlist
     * @param first ��λIP
     * @param last βλIP
     * @return IP��������IP��list
     */
    public static List<String> getIpList(String first, String last) {

        Long firstIp = IpTransUtil.ip2Dec(first);
        Long lastIp = IpTransUtil.ip2Dec(last);

        /*
         * ��IP��βIP˳����Եߵ�
         */
        if (firstIp > lastIp) {
            Long tmp = firstIp;
            firstIp = lastIp;
            lastIp = tmp;
        }

        List<String> ipList = new ArrayList<String>();

        /*
         * ȥ���㲥��ַ������ź󣬸�ֵlist
         */
        for (long _current_ip = firstIp; _current_ip <= lastIp; _current_ip++) {
            String ip = IpTransUtil.dec2Ip(_current_ip);
            if (!ip.endsWith(".0") && !ip.endsWith(".255")) ipList.add(ip);
        }

        return ipList;
    }

}
