package model;

import java.util.ArrayList;
import java.util.List;

public class Physiotherapist extends Person {
    private List<String> expertise;
    private List<Treatment> treatments;

    public Physiotherapist(String id, String name, String address, String phone, List<String> expertise) {
        super(id, name, address, phone);
        this.expertise = new ArrayList<>(expertise);
        this.treatments = new ArrayList<>();
    }

    public List<String> getExpertise() { return expertise; }
    public List<Treatment> getTreatments() { return treatments; }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }


    @Override
    public String toString() {
        return super.toString() + ", Expertise: " + String.join(", ", expertise);
    }
}
