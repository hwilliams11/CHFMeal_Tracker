package com.example.chfmeal_tracker;

public class Meal {
	// private variables

	int _NDB_No;
	MealType _Type;
	String _Date;
	double _Serving;
	String food_name;
	double calories;
	double sodium;

	public enum MealType {
		BREAKFAST(0), LUNCH(1), DINNER(2), SNACK(3);

		int type;

		MealType(int type) {
			this.type = type;
		}

		public int getInt() {
			return type;
		}
	}

	public static MealType getMealType(int type) {

		switch (type) {
		case 0:
			return MealType.BREAKFAST;
		case 1:
			return MealType.LUNCH;
		case 2:
			return MealType.DINNER;
		case 3:
			return MealType.SNACK;
		default:
			return null;
		}
	}

	public Meal(String _NDB_No, String _Date, double _Serving, MealType _Type,
			double calories, double sodium) {
		super();
		this._NDB_No = Integer.parseInt(_NDB_No);
		this._Type = _Type;
		this._Date = _Date;
		this._Serving = _Serving;

		this.calories = calories;
		this.sodium = sodium;
	}

	public Meal(String _NDB_No, String _Date, double _Serving, double calories,
			double sodium, MealType _Type, String food_name) {
		super();
		this._NDB_No = Integer.parseInt(_NDB_No);
		this._Type = _Type;
		this._Date = _Date;
		this._Serving = _Serving;
		this.food_name = food_name;
		this.calories = calories;
		this.sodium = sodium;
	}

	public Meal(String _NDB_No, String _Date, double _Serving, MealType _Type) {
		super();
		this._NDB_No = Integer.parseInt(_NDB_No);
		this._Type = _Type;
		this._Serving = _Serving;
		this._Date = _Date;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalorie(double calories) {
		this.calories = calories;
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

	public MealType get_Type() {
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

	public void set_Type(MealType _Type) {
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