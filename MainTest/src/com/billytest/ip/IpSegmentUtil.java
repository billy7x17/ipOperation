package com.billytest.ip;

import java.util.ArrayList;
import java.util.List;

/**
 * ip段工具类
 * @author <a href="mailto:li-zr@neusoft.com">Zongrui Li</a>
 * @version $Revision 1.0 $ 2015年2月15日 下午2:59:46
 */
public class IpSegmentUtil {

    /**
     * getMask 根据IP段获取子网掩码
     * @param ipSegment ip段，格式为192.168.1.1/24
     * @return 子网掩码
     * @throws MaskErrorException 若掩码位数不合法，会抛出异常
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
     * getExtremeIp 根据ip段获取首尾IP
     * @param ipSegment ip段，格式为192.168.1.1/24
     * @return 由首位IP组成的list，list[0]为首IP，list[1]为尾IP
     * @throws MaskErrorException 若掩码位数不合法，会抛出异常
     */
    public static List<String> getExtremeIp(String ipSegment) throws MaskErrorException {

        String ip = ipSegment.split("/")[0];

        String mask = getMask(ipSegment);

        Long ipLong = IpTransUtil.ip2Dec(ip);

        Long maskLong = IpTransUtil.ip2Dec(mask);

        /*
         * Long.lowestOneBit(maskLong) - 1 为 掩码的补码 ip或掩码的补码为IP段中最后一个IP
         */
        Long last = ipLong | (Long.lowestOneBit(maskLong) - 1);

        /*
         * ip与掩码为IP段中第一个IP
         */
        Long first = ipLong & maskLong;

        List<String> list = new ArrayList<String>();

        String firstIp = String.valueOf(IpTransUtil.dec2Ip(first));
        String lastIp = String.valueOf(IpTransUtil.dec2Ip(last));

        /*
         * 去掉广播地址和网络号
         */
        if (firstIp.endsWith(".0")) firstIp = firstIp.replaceAll("0$", "1");

        if (lastIp.endsWith(".255")) lastIp = lastIp.replaceAll("255$", "254");

        list.add(firstIp);
        list.add(lastIp);

        return list;
    }

    /**
     * getIpSegmentSize 根据IP段首尾IP获取IP段所有IP个数
     * @param firstIp 首位IP
     * @param lastIp 尾位IP
     * @return IP段里所有IP个数
     */
    public static Integer getIpSegmentSize(String firstIp, String lastIp) {

        return getIpList(firstIp, lastIp).size();

    }

    /**
     * getIpList 根据首尾IP获取所有IPlist
     * @param first 首位IP
     * @param last 尾位IP
     * @return IP段里所有IP的list
     */
    public static List<String> getIpList(String first, String last) {

        Long firstIp = IpTransUtil.ip2Dec(first);
        Long lastIp = IpTransUtil.ip2Dec(last);

        /*
         * 首IP和尾IP顺序可以颠倒
         */
        if (firstIp > lastIp) {
            Long tmp = firstIp;
            firstIp = lastIp;
            lastIp = tmp;
        }

        List<String> ipList = new ArrayList<String>();

        /*
         * 去掉广播地址和网络号后，赋值list
         */
        for (long _current_ip = firstIp; _current_ip <= lastIp; _current_ip++) {
            String ip = IpTransUtil.dec2Ip(_current_ip);
            if (!ip.endsWith(".0") && !ip.endsWith(".255")) ipList.add(ip);
        }

        return ipList;
    }

}
