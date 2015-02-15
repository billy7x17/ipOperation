package com.billytest.ip;

import java.util.ArrayList;
import java.util.List;

public class MaskDecodeUtil {

	public static String getMask(String ipSegment) {

		String maskNum = ipSegment.split("/")[1];

		long mask = ((((long) Math.pow(2D, Double.valueOf(maskNum))) - 1L) << (32 - Integer
				.valueOf(maskNum)));

		return IpTransUtil.dec2Ip(mask);
	}

	public static List<String> getExtremeIp(String ipSegment) {

		String seg[] = ipSegment.split("/");

		String ip = seg[0];

		String mask = getMask(ipSegment);

		String ipBin = Long.toBinaryString(IpTransUtil.ip2Dec(ip));

		String maskBin = Long.toBinaryString((IpTransUtil.ip2Dec(mask)));

		byte[] result = binaryAnd(ipBin, maskBin);

		result[result.length - 1] = 1;

		byte[] firstResult = binaryAnd(ipBin,
				"11111111111111111111111111111111");

		Long last = new Long(0L);

		Long first = new Long(0L);

		for (int i = 0; i < result.length; i++) {
			last = last + (long) (result[i] * Math.pow(2D, 31 - i));
			first += (long) (firstResult[i] * Math.pow(2D, 31 - i));
		}

		List<String> list = new ArrayList<String>();

		list.add(String.valueOf(IpTransUtil.dec2Ip(first)));
		list.add(String.valueOf(IpTransUtil.dec2Ip(last)));

		return list;
	}

	private static byte[] binaryAnd(String a, String b) {

		byte[] result = new byte[32];

		for (int i = 0; i < a.length(); i++)
			result[i] = (byte) ((a.charAt(i) - '0') & (b.charAt(i) - '0'));

		return result;
	}
}
