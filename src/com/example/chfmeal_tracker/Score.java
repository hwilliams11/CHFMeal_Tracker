package com.example.chfmeal_tracker;

public class Score {
	String date;

	double ideal_sodium;
	double ideal_calorie;

	public Score(String date, double ideal_calorie, double ideal_sodium) {
		super();
		this.date = date;

		this.ideal_sodium = ideal_sodium;
		this.ideal_calorie = ideal_calorie;

	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getIdeal_sodium() {
		return ideal_sodium;
	}

	public void setIdeal_sodium(double ideal_sodium) {
		this.ideal_sodium = ideal_sodium;
	}

	public double getIdeal_calorie() {
		return ideal_calorie;
	}

	public void setIdeal_calorie(double ideal_calorie) {
		this.ideal_calorie = ideal_calorie;
	}

}