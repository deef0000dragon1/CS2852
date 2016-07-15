/*
 * @Author Jeffrey Koehler
 * @date 3/21/2016
 * @purpose Data Structures Lab 2
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab2;

/** 
 * dot class object that is used to house the dots that are mode from DotViewer and CreatePoints. 
 * */
public class Dot {
	private double x;
	private double y;
	
	/** 
	 * creates the Dot
	 * @param double int, double y 
	 * */
	public Dot(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/** 
	 * return x
	 * */
	public double getX(){
		return x;
	}
	
	/** 
	 * return y
	 * */
	public double getY(){
		return y;
	}
}
