package com.example.chfmeal_tracker;

import android.util.Log;

public class Food {
	// private variables
	int _NDB_No;
	String _food_name;
	double _water_g;
	double _calories;
	double _protein_g;
	double _carbohydrate_g;
	double _fiber_g;
	double _sugar_g;
	double _calcium_mg;
	double _sodium_mg;
	double _cholesterol_mg;
	double _gmwt1;
	String _gmwt1_desc;
	double _gmwt2;
	String _gmwt2_desc;

    public Food(String _NDB_No, String _food_name, String _calories,
            String _sodium_mg) {
	    super();
	    this._NDB_No = Integer.parseInt(_NDB_No);
	    this._food_name = _food_name;
	    this._calories = Double.parseDouble(_calories);
	    this._sodium_mg = Double.parseDouble(_sodium_mg);
	}

    public Food(int _NDB_No, String _food_name, double _calories,
            double _sodium_mg) {
	    super();
	    this._NDB_No = _NDB_No;
	    this._food_name = _food_name;
	    this._calories = _calories;
	    this._sodium_mg = _sodium_mg;
	}
	public Food(int _NDB_No, String _food_name, double _calories,double _protein_g,double _carbohydrate_g,double _fiber_g,
			double _sugar_g,double _sodium_mg, double _cholesterol_mg, double _gmwt1, String _gmwt1_desc,
			double _gmwt2, String _gmwt2_desc) {
		

		super();
		this._NDB_No = _NDB_No;
		this._food_name = _food_name;
		this._calories = _calories;
		this._protein_g = _protein_g;
		this._carbohydrate_g = _carbohydrate_g;
		this._fiber_g = _fiber_g;
		this._sugar_g = _sugar_g;
		this._sodium_mg = _sodium_mg;
		this._cholesterol_mg = _cholesterol_mg;
		this._gmwt1 = _gmwt1;
		this._gmwt1_desc = _gmwt1_desc;
		this._gmwt2 = _gmwt2;
		this._gmwt2_desc = _gmwt2_desc;
		Log.d("mydebug","in constructor: "+this);
	}

	public Food(int _NDB_No, String _food_name, double _water_g,
			double _calories, double _protein_g, double _carbohydrate_g,
			double _fiber_g, double _sugar_g, double _calcium_mg,
			double _sodium_mg, double _cholesterol_mg, double _gmwt1,
			String _gmwt1_desc, double _gmwt2, String _gmwt2_desc) {
		super();
		this._NDB_No = _NDB_No;
		this._food_name = _food_name;
		this._water_g = _water_g;
		this._calories = _calories;
		this._protein_g = _protein_g;
		this._carbohydrate_g = _carbohydrate_g;
		this._fiber_g = _fiber_g;
		this._sugar_g = _sugar_g;
		this._calcium_mg = _calcium_mg;
		this._sodium_mg = _sodium_mg;
		this._cholesterol_mg = _cholesterol_mg;
		this._gmwt1 = _gmwt1;
		this._gmwt1_desc = _gmwt1_desc;
		this._gmwt2 = _gmwt2;
		this._gmwt2_desc = _gmwt2_desc;
		
		
	}
	public Food(String[]data){
		
		this(Integer.parseInt(data[0]),data[1],Double.parseDouble(data[2]),
				Double.parseDouble(data[3]),Double.parseDouble(data[4]),Double.parseDouble(data[5]),
				Double.parseDouble(data[6]),Double.parseDouble(data[7]),Double.parseDouble(data[8]),
				Double.parseDouble(data[9]),Double.parseDouble(data[10]),Double.parseDouble(data[11]),
				data[12],Double.parseDouble(data[13]),data[14]);
	}
	public int get_NDB_No() {
		return _NDB_No;
	}

	public void set_NDB_No(int _NDB_No) {
		this._NDB_No = _NDB_No;
	}

	public String get_food_name() {
		return _food_name;
	}

	public void set_food_name(String _food_name) {
		this._food_name = _food_name;
	}

	public double get_water_g() {
		return _water_g;
	}

	public void set_water_g(double _water_g) {
		this._water_g = _water_g;
	}

	public double get_calories() {
		return _calories;
	}

	public void set_calories(double _calories) {
		this._calories = _calories;
	}

	public double get_protein_g() {
		return _protein_g;
	}

	public void set_protein_g(double _protein_g) {
		this._protein_g = _protein_g;
	}

	public double get_carbohydrate_g() {
		return _carbohydrate_g;
	}

	public void set_carbohydrate_g(double _carbohydrate_g) {
		this._carbohydrate_g = _carbohydrate_g;
	}

	public double get_fiber_g() {
		return _fiber_g;
	}

	public void set_fiber_g(double _fiber_g) {
		this._fiber_g = _fiber_g;
	}

	public double get_sugar_g() {
		return _sugar_g;
	}

	public void set_sugar_g(double _sugar_g) {
		this._sugar_g = _sugar_g;
	}

	public double get_calcium_mg() {
		return _calcium_mg;
	}

	public void set_calcium_mg(double _calcium_mg) {
		this._calcium_mg = _calcium_mg;
	}

	public double get_sodium_mg() {
		return _sodium_mg;
	}

	public void set_sodium_mg(double _sodium_mg) {
		this._sodium_mg = _sodium_mg;
	}

	public double get_cholesterol_mg() {
		return _cholesterol_mg;
	}

	public void set_cholesterol_mg(double _cholesterol_mg) {
		this._cholesterol_mg = _cholesterol_mg;
	}

	public double get_gmwt1() {
		return _gmwt1;
	}

	public void set_gmwt1(double _gmwt1) {
		this._gmwt1 = _gmwt1;
	}

	public String get_gmwt1_desc() {
		return _gmwt1_desc;
	}

	public void set_gmwt1_desc(String _gmwt1_desc) {
		this._gmwt1_desc = _gmwt1_desc;
	}

	public double get_gmwt2() {
		return _gmwt2;
	}

	public void set_gmwt2(double _gmwt2) {
		this._gmwt2 = _gmwt2;
	}

	public String get_gmwt2_desc() {
		return _gmwt2_desc;
	}

	public void set_gmwt2_desc(String _gmwt2_desc) {
		this._gmwt2_desc = _gmwt2_desc;
	}

	@Override
	public String toString() {
		return "Food [_NDB_No=" + _NDB_No + ", _food_name=" + _food_name
				+ ", _water_g=" + _water_g + ", _calories=" + _calories
				+ ", _protein_g=" + _protein_g + ", _carbohydrate_g="
				+ _carbohydrate_g + ", _fiber_g=" + _fiber_g + ", _sugar_g="
				+ _sugar_g + ", _gmwt1="+_gmwt1+", _gmwt2="+_gmwt2+"]";
	}

}
