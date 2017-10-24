package com.springapp.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtil {
	//private static List<String> _allLocalIpAddressList = new ArrayList<String>();
	
	/**
     * ip地址是否合法
     * @param s
     * @return
     */
    public static boolean ipValid(String s) {
        String regex0 = "(2[0-4]\\d)" + "|(25[0-5])";
        String regex1 = "1\\d{2}";
        String regex2 = "[1-9]\\d";
        String regex3 = "\\d";
        String regex = "(" + regex0 + ")|(" + regex1 + ")|(" + regex2 + ")|(" + regex3 + ")";
        regex = "(" + regex + ")\\.(" + regex + ")\\.(" + regex + ")\\.(" + regex + ")";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
	public static String getLocalIPAddress(){
        try {
            InetAddress myIP = InetAddress.getLocalHost();
            return myIP.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } 
    }

    public static List<String> getAllLocalIPAddress() {
        Enumeration<NetworkInterface> all = null;
        try {
            all = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ignore) {
            ignore.printStackTrace();
        }
        List<String> allIP = new ArrayList<String>();
        for (; all != null && all.hasMoreElements();) {
            NetworkInterface ni = all.nextElement();
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            for (; addresses.hasMoreElements();) {
            	InetAddress ip = addresses.nextElement();
            	if (ip != null && ip instanceof Inet4Address) {
            		allIP.add(ip.getHostAddress());
            	}
            }
        }
        return allIP;
    }
    
    public static boolean isLocalIPAddress(String ipAddress) {
        return getAllLocalIPAddress().contains(ipAddress);
    }
}
