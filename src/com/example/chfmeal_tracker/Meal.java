package com.example.chfmeal_tracker;

public class Meal {
	// private variables

	int _NDB_No;
	int _Type;
	String _Date;
	double _Serving;
	String food_name;
	double calorie;
	double sodium;

	public Meal(String _NDB_No, String food_name, double _Serving,
			double calorie, double sodium, String _Date, int _Type) {
		super();
		this._NDB_No = Integer.parseInt(_NDB_No);
		this._Type = _Type;
		this._Date = _Date;
		this._Serving = _Serving;
		this.food_name = food_name;
		this.calorie = calorie;
		this.sodium = sodium;
	}

	public Meal(String _NDB_No, int _Type, double _Serving) {
		super();
		this._NDB_No = Integer.parseInt(_NDB_No);
		this._Type = _Type;
		this._Serving = _Serving;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public double getCalorie() {
		return calorie;
	}

	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}

	public double getSodium() {
		return sodium;
	}

	public void setSodium(double sodium) {
		this.sodium = sodium;
	}

	public void set_NDB_No(int _NDB_No) {
		this._NDB_No = _NDB_No;
	}

	public int get_NDB_No() {
		return _NDB_No;
	}

	public int get_Type() {
		return _Type;
	}

	public String get_Date() {
		return _Date;
	}

	public double get_Serving() {
		return _Serving;
	}

	public void set_NDB_No(String _NDB_No) {
		this._NDB_No = Integer.parseInt(_NDB_No);
	}

	public void set_Type(int _Type) {
		this._Type = _Type;
	}

	public void set_Date(String _Date) {
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
