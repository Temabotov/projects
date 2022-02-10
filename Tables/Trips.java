package Tables;

import java.io.Serializable;

public class Trips implements Serializable {

    private int trips_id;
    private String city;
    private String address;
    private String address_prib;
    private String date;
    private String time;
    private String place;
    private String price;
    private String car_brand;
    private String filtr_city;
    private String filtr_date;
    private String number_of_seats;
    private String name;
    private String surname;
    private String email;
    private String phone;

    public Trips(String date, String time, String city, String address, String address_prib, String place, String car_brand, String price) {
        this.date = date;
        this.time = time;
        this.city =city;
        this.address =address;
        this.address_prib = address_prib;
        this.place = place;
        this.car_brand = car_brand;
        this.price = price;
    }

    public Trips(String filtr_city, String filtr_date) {
        this.filtr_city = filtr_city;
        this.filtr_date =filtr_date;
    }

    public Trips(String number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public Trips(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email =email;
        this.phone =phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTrips_id() {
        return trips_id;
    }

    public void setTrips_id(int trips_id) {
        this.trips_id = trips_id;
    }

    public String getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(String number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public String getFiltr_city() {
        return filtr_city;
    }

    public void setFiltr_city(String filtr_city) {
        this.filtr_city = filtr_city;
    }

    public String getFiltr_date() {
        return filtr_date;
    }

    public void setFiltr_date(String filtr_date) {
        this.filtr_date = filtr_date;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_prib() {
        return address_prib;
    }

    public void setAddress_prib(String address_prib) {
        this.address_prib = address_prib;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

}

