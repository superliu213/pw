package com.springapp.common.op;

/**
 * hibernate实体类的接口
 *
 */
public interface Model {
	/**
	 * 
	 * @return
	 */
	String toString();
	
	/**
	 * 
	 * @param other 比较的对象
	 * @return
	 */
	boolean equals(Object other);
	
	/**
	 * 
	 * @return
	 */
	int hashCode();
}
