package com.billytest.ip;

import java.util.List;

public class MainTest {

	public static void main(String[] args) throws MaskErrorException {
		String ip1 = "192.168.1.1";

		String ip2 = "192.168.2.1";

		String ipSegment = "192.168.1.1/16";

		Long dec1 = IpTransUtil.ip2Dec(ip1);

		Long dec2 = IpTransUtil.ip2Dec(ip2);

		System.out.println(dec1);

		System.out.println(dec2);

		System.out.println(IpSegmentUtil.getIpSegmentSize(ip1,ip2));

		System.out.println(IpTransUtil.dec2Ip(dec1));

		System.out.println(IpTransUtil.dec2Ip(dec2));

		// System.out.println(new Long(4294967040L) >> 8);
		//
		// System.out.println(1L << 8);

		System.out.println(IpSegmentUtil.getMask(ipSegment));
		
		System.out.println("192.168.0.0".replaceAll("0$", "1"));
		
		System.out.println("");

		List<String> list = IpSegmentUtil.getExtremeIp("192.168.1.128/24");

		for (String string : list) {
			System.out.println(string);
		}

		List<String> ipList = IpSegmentUtil.getIpList(list.get(1), list.get(0));
		
		for (String i : ipList) {
			System.out.println(i);
		}
		
		System.out.println("total : " + IpSegmentUtil.getIpSegmentSize(list.get(1), list.get(0)));
	}

}
