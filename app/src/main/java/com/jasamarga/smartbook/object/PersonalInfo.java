package com.jasamarga.smartbook.object;

import java.util.List;

/**
 * Created by apridosandyasa on 8/9/16.
 */
public class PersonalInfo {

    private String assignment_number;
    private String user_name;
    private String password;
    private String last_name;
    private String parent_last_name;
    private String sex;
    private String agama;
    private String date_of_birth;
    private String town_of_birth;
    private String alamat;
    private String tel_number_1;
    private String tel_number_2;
    private String email_address;
    private String grade;
    private String position_name;
    private String parent_assignment_number;
    private String location_address;
    private String location_name;
    private String employment_category;
    private String unit_kerja;
    private String urlfoto;

    public String getNpp() {
        return assignment_number;
    }

    public void setNpp(String assignment_number) {
        this.assignment_number = assignment_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return last_name;
    }

    public void setNama(String last_name) {
        this.last_name = last_name;
    }

    public String getNama_atasan() {
        return parent_last_name;
    }

    public void setNama_atasan(String parent_last_name) {
        this.parent_last_name = parent_last_name;
    }

    public String getJenis_kelamin() {
        return sex;
    }

    public void setJenis_kelamin(String sex) {
        this.sex = sex;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getTempatlahir() {
        return town_of_birth;
    }

    public void setTempatlahir(String town_of_birth) {
        this.town_of_birth = town_of_birth;
    }

    public String getTanggallahir() {
        return date_of_birth;
    }

    public void setTanggallahir(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp1() {
        return tel_number_1;
    }

    public void setTelp1(String tel_number_1) {
        this.tel_number_1 = tel_number_1;
    }

    public String getTelp2() {
        return tel_number_2;
    }

    public void setTelp2(String tel_number_2) {
        this.tel_number_2 = tel_number_2;
    }

    public String getEmail() {
        return email_address;
    }

    public void setEmail(String email_address) {
        this.email_address = email_address;
    }

    public String getGolongan() {
        return grade;
    }

    public void setGolongan(String grade) {
        this.grade = grade;
    }

    public String getJabatan() {
        return position_name;
    }

    public void setJabatan(String position_name) {
        this.position_name = position_name;
    }

    public String getNpp_atasan() {
        return parent_assignment_number;
    }

    public void setNpp_atasan(String parent_assignment_number) {
        this.parent_assignment_number = parent_assignment_number;
    }

    public String getKantor_alamat() {
        return location_address;
    }

    public void setKantor_alamat(String location_address) {
        this.location_address = location_address;
    }

    public String getKantor_desc() {
        return location_name;
    }

    public void setKantor_desc(String kantor_desc) {
        this.location_name = kantor_desc;
    }

    public String getStatus_desc() {
        return employment_category;
    }

    public void setStatus_desc(String employment_category) {
        this.employment_category = employment_category;
    }

    public String getUnit_desc() {
        return unit_kerja;
    }

    public void setUnit_desc(String unit_kerja) {
        this.unit_kerja = unit_kerja;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

}
