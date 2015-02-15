package com.billytest.ip;

class IpTransUtil {

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

	public static String dec2Ip(Long dec) {

		String result = "";

		Long tmp = new Long(0L);

		for (int i = 3; i >= 0; i--) {

			tmp = (dec / (1 << 8L * i));

			result += tmp;

			dec -= (tmp << 8L * i);

			if (i != 0)
				result += ".";
		}

		return result;
	}

}
