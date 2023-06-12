package com.m4xvel.aeroreserve.fragments;

public class Booking {
        private String fullName;
        private String phoneNumber;
        private String selectedClass;
        private String photoUrl;
        private String email;
        private String city1;
        private String city2;
        private String date_from;
        private String date_to;
        private String time_from;
        private String time_to;
        private String bookingId;

        public Booking() {}

        public Booking(String fullName, String phoneNumber, String selectedClass, String photoUrl, String city1, String city2, String date_from, String date_to, String time_from, String time_to, String email) {
                this.fullName = fullName;
                this.phoneNumber = phoneNumber;
                this.selectedClass = selectedClass;
                this.photoUrl = photoUrl;
                this.city1 = city1;
                this.city2 = city2;
                this.date_from = date_from;
                this.date_to = date_to;
                this.time_from = time_from;
                this.time_to = time_to;
                this.email = email;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getSelectedClass() {
                return selectedClass;
        }

        public void setSelectedClass(String selectedClass) {
                this.selectedClass = selectedClass;
        }

        public String getCity1() {
                return city1;
        }

        public void setCity1(String city1) {
                this.city1 = city1;
        }

        public String getCity2() {
                return city2;
        }

        public void setCity2(String city2) {
                this.city2 = city2;
        }

        public String getDate_from() {
                return date_from;
        }

        public void date_from(String date_from) {
                this.date_from = date_from;
        }

        public String getDate_to() {
                return date_to;
        }

        public void date_to(String date_to) {
                this.date_to = date_to;
        }

        public String getTime_from() {
                return time_from;
        }

        public void time_from(String time_from) {
                this.time_from = time_from;
        }

        public String getTime_to() {
                return time_to;
        }

        public void time_to(String time_to) {
                this.time_to = time_to;
        }

        public String getEmail() {
                return email;
        }

        public void email(String email) {
                this.email = email;
        }

        public String getPhotoUrl() {
                return photoUrl;
        }
        public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
        }

        public void setBookingId(String bookingId) {
                this.bookingId = bookingId;
        }

        public String getBookingId() {
                return bookingId;
        }
}
