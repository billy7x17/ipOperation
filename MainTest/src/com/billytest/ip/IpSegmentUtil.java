package com.billytest.ip;

import java.util.ArrayList;
import java.util.List;

public class IpSegmentUtil {

	public static String getMask(String ipSegment) throws MaskErrorException {

		String maskNum = ipSegment.split("/")[1];
		
		if (Integer.valueOf(maskNum) < 1 || Integer.valueOf(maskNum) > 32) {
			throw new MaskErrorException();
		}

		long mask = ((((long) Math.pow(2D, Double.valueOf(maskNum))) - 1L) << (32 - Integer
				.valueOf(maskNum)));

		return IpTransUtil.dec2Ip(mask);
	}

	public static List<String> getExtremeIp(String ipSegment) throws MaskErrorException {

		String seg[] = ipSegment.split("/");

		String ip = seg[0];

		String mask = getMask(ipSegment);

		Long ipLong = IpTransUtil.ip2Dec(ip);

		Long maskLong = IpTransUtil.ip2Dec(mask);

		Long last = ipLong | ((Long.lowestOneBit(maskLong) - 1));

		Long first = (ipLong & maskLong);

		List<String> list = new ArrayList<String>();

		String firstIp = String.valueOf(IpTransUtil.dec2Ip(first));
		String lastIp = String.valueOf(IpTransUtil.dec2Ip(last));

		if (firstIp.endsWith(".0"))
			firstIp = firstIp.replaceAll("0$", "1");

		if (lastIp.endsWith(".255"))
			lastIp = lastIp.replaceAll("255$", "254");

		list.add(firstIp);
		list.add(lastIp);

		return list;
	}

	public static Integer getIpSegmentSize(String firstIp, String lastIp) {

		return getIpList(firstIp, lastIp).size();

	}

	public static List<String> getIpList(String first, String last) {

		Long firstIp = IpTransUtil.ip2Dec(first);
		Long lastIp = IpTransUtil.ip2Dec(last);

		List<String> ipList = new ArrayList<String>();

		for (long _current_ip = firstIp; _current_ip <= lastIp; _current_ip++) {
			String ip = IpTransUtil.dec2Ip(_current_ip);
			if (!ip.endsWith(".0") && !ip.endsWith(".255"))
				ipList.add(ip);
		}

		return ipList;
	}

}
