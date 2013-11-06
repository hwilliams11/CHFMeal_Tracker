package com.example.chfmeal_tracker;

import java.sql.Date;

public class MealItem {
	//private variables
	
	String _NDB_No;
	int _Type;
	Date _Date;
	double _Serving;

	public MealItem(String _NDB_No, int _Type, Date _Date, double _Serving) {
		super();
		this._NDB_No = _NDB_No;
		this._Type = _Type;
		this._Date = _Date;
		this._Serving = _Serving;
	}
	

	public String get_NDB_No() {
		return _NDB_No;
	}



	public int get_Type() {
		return _Type;
	}



	public Date get_Date() {
		return _Date;
	}



	public double get_Serving() {
		return _Serving;
	}



	public void set_NDB_No(String _NDB_No) {
		this._NDB_No = _NDB_No;
	}



	public void set_Type(int _Type) {
		this._Type = _Type;
	}



	public void set_Date(Date _Date) {
		this._Date = _Date;
	}



	public void set_Serving(double _Serving) {
		this._Serving = _Serving;
	}


	@Override
	public String toString() {
		return "MealItem [_NDB_No=" + _NDB_No + ", _Type=" + _Type + ", _Date="
				+ _Date + ", _Serving=" + _Serving + "]";
	}
	
}
