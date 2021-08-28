package it.units.ceschia.help.entity;

public class UserInfoSpecific {

    String allergies;
    String diseases;
    String vaccines;
    String medicines;
    String bloodType;
    String rh;

    public UserInfoSpecific() {
    }

    public UserInfoSpecific(String allergies, String diseases, String vaccines, String medicines, String bloodType, String rh) {
        this.allergies = allergies;
        this.diseases = diseases;
        this.vaccines = vaccines;
        this.medicines = medicines;
        this.bloodType = bloodType;
        this.rh = rh;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getVaccines() {
        return vaccines;
    }

    public void setVaccines(String vaccines) {
        this.vaccines = vaccines;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }
}
