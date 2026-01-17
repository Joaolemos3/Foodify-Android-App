package com.piyush.foodify.data.model;

import java.util.List;

public class PositionStackResponse {
    private List<LocationData> data;
    
    public List<LocationData> getData() {
        return data;
    }
    
    public void setData(List<LocationData> data) {
        this.data = data;
    }
    
    public static class LocationData {
        private double latitude;
        private double longitude;
        private String label;
        private String name;
        private String type;
        private String number;
        private String street;
        private String postal_code;
        private String confidence;
        private String region;
        private String region_code;
        private String county;
        private String locality;
        private String administrative_area;
        private String neighbourhood;
        private String country;
        private String country_code;
        private String continent;
        
        // Getters and setters
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
        
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        
        public String getLocality() { return locality; }
        public void setLocality(String locality) { this.locality = locality; }
    }
}