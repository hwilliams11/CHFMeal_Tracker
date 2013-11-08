package com.example.chfmeal_tracker;

public class Score {
        String date;
        int meal_type;
        double ideal_sodium;
        double ideal_calorie;
        double actual_sodium;
        double actual_calorie;

        public Score(String date, int meal_type, double ideal_calorie,
                        double ideal_sodium, double actual_calorie, double actual_sodium) {
                super();
                this.date = date;
                this.meal_type = meal_type;
                this.ideal_sodium = ideal_sodium;
                this.ideal_calorie = ideal_calorie;
                this.actual_sodium = actual_sodium;
                this.actual_calorie = actual_calorie;
        }

        public Score(int meal_type, double ideal_sodium, double ideal_calorie,
                        double actual_sodium, double actual_calorie) {
                super();

                this.meal_type = meal_type;
                this.ideal_sodium = ideal_sodium;
                this.ideal_calorie = ideal_calorie;
                this.actual_sodium = actual_sodium;
                this.actual_calorie = actual_calorie;
        }

        public String getDate() {
                return date;
        }

        public void setDate(String date) {
                this.date = date;
        }

        public int getMeal_type() {
                return meal_type;
        }

        public void setMeal_type(int meal_type) {
                this.meal_type = meal_type;
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