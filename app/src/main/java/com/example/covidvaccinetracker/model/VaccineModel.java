/*
Developed by : Dev Nakum(20CE059)
To show data from API(model class)
*/

package com.example.covidvaccinetracker.model;

public class VaccineModel {

    private String vaccineCenter;
    private String vaccinationCharges;
    private String vaccinationAge;
    private String vaccinationTimings;
    private String vaccineName;
    private String vaccineCenterTime;
    private String vaccinationCenterAddress;
    private String vaccineAvailable;
    private String dose1;
    private String dose2;

    public VaccineModel() {
    }

    public String getVaccineCenter() {      // get the vaccination center
        return vaccineCenter;
    }

    public void setVaccineCenter(String vaccineCenter) {        // set the vaccination center
        this.vaccineCenter = vaccineCenter;
    }

    public String getVaccinationCharges() {     // get the vaccine charge it is free or paid
        return vaccinationCharges;
    }

    public void setVaccinationCharges(String vaccinationCharges) {      // set the vaccine charge it is free or paid
        this.vaccinationCharges = vaccinationCharges;
    }

    public String getVaccinationAge() {     // get the age
        return vaccinationAge;
    }

    public void setVaccinationAge(String vaccinationAge) {      // set the age
        this.vaccinationAge = vaccinationAge;
    }

    public String getVaccinationTimings() {     // get the starting time for vaccine
        return vaccinationTimings;
    }

    public void setVaccinationTimings(String vaccinationTimings) {      // set the starting time for vaccine
        this.vaccinationTimings = vaccinationTimings;
    }

    public String getVaccineName() {       // get vaccine name
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {        // set vaccine name
        this.vaccineName = vaccineName;
    }

    public String getVaccineCenterTime() {      // get the ending time for vaccine
        return vaccineCenterTime;
    }

    public void setVaccineCenterTime(String vaccineCenterTime) {        // set the ending time for vaccine
        this.vaccineCenterTime = vaccineCenterTime;
    }

    public String getVaccinationCenterAddress() {       // get the vaccination address
        return vaccinationCenterAddress;
    }

    public void setVaccinationCenterAddress(String vaccinationCenterAddress) {      // set the vaccination address
        this.vaccinationCenterAddress = vaccinationCenterAddress;
    }

    public String getVaccineAvailable() {       // get the number of vaccine is available
        return vaccineAvailable;
    }

    public void setVaccineAvailable(String vaccineAvailable) {      // set the number vaccine is  available or not
        this.vaccineAvailable = vaccineAvailable;
    }

    public void setDose1(String dose1) {        // set the number of dose 1 of available vaccine
        this.dose1 = dose1;
    }

    public void setDose2(String dose2) {        // set the number of dose 2 of available vaccine
        this.dose2 = dose2;
    }

    public String getDose1() {      // get the number of dose 1 of available vaccine
        return dose1;
    }

    public String getDose2() {      // get the number of dose 2 of available vaccine
        return dose2;
    }


    // create constructor
    public VaccineModel(String vaccineCenter, String vaccinationCharges, String vaccinationAge, String vaccinationTimings, String vaccineName, String vaccineCenterTime, String vaccinationCenterAddress, String vaccinationAvailable,String dose1,String dose2) {
        this.vaccineCenter = vaccineCenter;
        this.vaccinationCharges = vaccinationCharges;
        this.vaccinationAge = vaccinationAge;
        this.vaccinationTimings = vaccinationTimings;
        this.vaccineName = vaccineName;
        this.vaccineCenterTime = vaccineCenterTime;
        this.vaccinationCenterAddress = vaccinationCenterAddress;
        this.vaccineAvailable = vaccineAvailable;
        this.dose1 = dose1;
        this.dose2 = dose2;

    }
}