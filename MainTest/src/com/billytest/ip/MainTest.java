package com.billytest.ip;

import java.util.Arrays;
import java.util.List;

public class MainTest {

	public static void main(String[] args) {
		String ip1 = "192.168.1.1";

		String ip2 = "192.168.2.1";

		String ipSegment = "192.168.1.1/23";

		Long dec1 = IpTransUtil.ip2Dec(ip1);

		Long dec2 = IpTransUtil.ip2Dec(ip2);

		System.out.println(dec1);

		System.out.println(dec2);

		System.out.println(Math.abs(dec2 - dec1));

		System.out.println(IpTransUtil.dec2Ip(dec1));

		System.out.println(IpTransUtil.dec2Ip(dec2));

		// System.out.println(new Long(4294967040L) >> 8);
		//
		// System.out.println(1L << 8);

		System.out.println(MaskDecodeUtil.getMask(ipSegment));

		List<String> list = MaskDecodeUtil.getExtremeIp("192.168.1.1/23");

		for (String string : list) {
			System.out.println(string);
		}

	}

}
