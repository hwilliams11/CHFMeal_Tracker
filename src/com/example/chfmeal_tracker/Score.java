package com.example.chfmeal_tracker;

import java.util.Date;

public class Score {
	Date date;
	double ideal_sodium;
	double ideal_calorie;
	double actual_sodium;
	double actual_calorie;

	public Score(Date date, double ideal_sodium, double ideal_calorie,
			double actual_sodium, double actual_calorie) {
		super();
		this.date = date;
		this.ideal_sodium = ideal_sodium;
		this.ideal_calorie = ideal_calorie;
		this.actual_sodium = actual_sodium;
		this.actual_calorie = actual_calorie;
	}

	public Score(double ideal_sodium, double ideal_calorie,
			double actual_sodium, double actual_calorie) {
		super();
		this.date = new Date();
		this.ideal_sodium = ideal_sodium;
		this.ideal_calorie = ideal_calorie;
		this.actual_sodium = actual_sodium;
		this.actual_calorie = actual_calorie;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public double getActual_sodium() {
		return actual_sodium;
	}

	public void setActual_sodium(double actual_sodium) {
		this.actual_sodium = actual_sodium;
	}

	public double getActual_calorie() {
		return actual_calorie;
	}

	public void setActual_calorie(double actual_calorie) {
		this.actual_calorie = actual_calorie;
	}

}
