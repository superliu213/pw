package com.springapp.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {
	/**
	 * 数组1-数组2
	 * 
	 * @param array1
	 * @param array2
	 */
	public static <T> T[] SubArray(T[] array1, T[] array2) {
		List<T> arrayList = new ArrayList<T>();
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array2.length; j++) {
				if (array1[i].equals(array2[j])) {
					break;
				} else {
					if (j == array2.length - 1) {
						arrayList.add(array1[i]);
					}
				}
			}
		}
		
		return arrayList.toArray(array1);
	}

	/**
	 * 数组1+数组2
	 * 
	 * @param array1
	 * @param array2
	 */
	public static <T> T[] AddArray(T[] array1, T[] array2) {
		List<T> arrayList = new ArrayList<T>();
		if (array1 != null) {
			arrayList.addAll(Arrays.asList(array1));
		}
		if (array2 != null) {
			arrayList.addAll(Arrays.asList(array2));
		}

		return arrayList.toArray(array1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] array1 = new String[] { "a", "b", "c" };

		String[] array2 = new String[] { "b", "a" };

		// Object[] result = SubArray(array1, array2);
		// for (int i = 0; i < result.length; i++) {
		// System.out.println(result[i]);
		// }

		String[] result2 = AddArray(array1, array2);
		for (String s : result2) {
			System.out.println(s);
		}
	}

}
