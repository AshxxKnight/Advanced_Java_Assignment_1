package org.example;

public class Product {

        private String id;
        private String name;
        private String color;
        private String gender;
        private String size;
        private double price;
        private double rating;
        private String availability;

        //constructor
        public Product() {

        }
        public Product(String id, String name, String color, String gender, String size, double price, double rating, String availability) {
            this.id = id;
            this.name = name;
            this.color = color;
            this.gender = gender;
            this.price = price;
            this.size = size;
            this.rating = rating;
            this.availability = availability;
        }

        //setters
        public void setId(String id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setColor(String color) {
            this.color = color;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public void setSize(String size) {
            this.size = size;
        }
        public void setRating(double rating) {
            this.rating = rating;
        }
        public void setAvailability(String availability) {
            this.availability = availability;
        }

        //getters
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }

        public String getGender() {
            return gender;
        }

        public String getSize() {
            return size;
        }

        public double getPrice() {
            return price;
        }

        public double getRating() {
            return rating;
        }

        public String getAvailability() {
            return availability;
        }
    }

