package com.databps.bigdaf.admin.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

	/**
	 *
	 * @param host
	 * @return
	 * @throws IOException
	 */
	public static boolean ping(String host) throws IOException {
		InetAddress address = InetAddress.getByName(host);
		return address.isReachable(3000);
	}

	/**
	 * hostname
	 * @return
	 */
	public static String getHostNameForLiunx() {
		try {
			return (InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException uhe) {
			String host = uhe.getMessage();
			if (host != null) {
				int colon = host.indexOf(':');
				if (colon > 0) {
					return host.substring(0, colon);
				}
			}
			return "UnknownHost";
		}
	}

	public static void main(String args[]) throws IOException {
		boolean bool = NetUtils.ping("sbx4");
		System.out.println(bool);

		String hostName = NetUtils.getHostNameForLiunx();
		System.out.println(hostName);
	}

}

