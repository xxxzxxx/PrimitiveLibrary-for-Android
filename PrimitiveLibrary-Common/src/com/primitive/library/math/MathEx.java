package com.primitive.library.math;

public class MathEx {
	/**
	 * 最大公約数
	 * Greatest Common Divisor
	 * @param a
	 * @param b
	 * @return
	 */
	public static int GCD(final int a,final int b){
		int a1 = a;
		int b1 = b;
		for(;b1 != 0;){
			a1 = b;
			b1 = a % b;
		}
		return a1;
	}

	/**
	 * 最小公倍数
	 * Least Common Multiple
	 * @param a
	 * @param b
	 * @return
	 */
	public static int LCM(final int a,final int b){
		return a * a / GCD(a,b);
	}

	/**
	 * 合算
	 * @param a
	 * @param b
	 * @return
	 */
	public static int SUM(final int[] a){
		int sum = 0;
		for(int i : a) sum+=i;
		return sum;
	}
	/**
	 * 平均
	 * @param a
	 * @param b
	 * @return
	 */
	public static double AVG(final int[] a){
		int sum = SUM(a);
		return (double)sum / a.length;
	}
}
