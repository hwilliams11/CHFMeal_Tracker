USE health.it

#
#SET FOREIGN_KEY_CHECKS = 0;
#drop table if exists meal
#drop table if exists food
#drop table if exists intake;

#SET FOREIGN_KEY_CHECKS = 1;

#DROP TABLE meals;

#DROP TABLE scores;

#CREATE TABLE meals(patientId INTEGER,NDB_No INTEGER,creation_date DATE,serving_size REAL,meal_type INTEGER,FOREIGN KEY(NDB_No)REFERENCES USDAfood(NDB_No),FOREIGN KEY(patientId)REFERENCES patient(patientId),PRIMARY KEY(patientId,creation_date,meal_type,NDB_No));

#CREATE TABLE scores(patientId INTEGER,creation_date DATE,meal_type INTEGER,ideal_calorie REAL,ideal_sodium REAL,actual_calorie REAL,actual_sodium REAL,FOREIGN KEY(patientId)REFERENCES patient(patientId),PRIMARY KEY(patientId,creation_date,meal_type ));
