package com.piyush.foodify.data.model;

import java.util.List;

public class FoursquareResponse {
    private List<FoursquarePlace> results;
    
    public List<FoursquarePlace> getResults() {
        return results;
    }
    
    public void setResults(List<FoursquarePlace> results) {
        this.results = results;
    }
    
    public static class FoursquarePlace {
        private String fsq_id;
        private String name;
        private Location location;
        private List<Category> categories;
        
        public String getFsq_id() { return fsq_id; }
        public void setFsq_id(String fsq_id) { this.fsq_id = fsq_id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public Location getLocation() { return location; }
        public void setLocation(Location location) { this.location = location; }
        
        public List<Category> getCategories() { return categories; }
        public void setCategories(List<Category> categories) { this.categories = categories; }
        
        public static class Location {
            private String address;
            private String locality;
            private String region;
            private String postcode;
            private String country;
            
            public String getAddress() { return address; }
            public void setAddress(String address) { this.address = address; }
            
            public String getLocality() { return locality; }
            public void setLocality(String locality) { this.locality = locality; }
            
            public String getRegion() { return region; }
            public void setRegion(String region) { this.region = region; }
            
            public String getPostcode() { return postcode; }
            public void setPostcode(String postcode) { this.postcode = postcode; }
            
            public String getCountry() { return country; }
            public void setCountry(String country) { this.country = country; }
        }
        
        public static class Category {
            private String name;
            
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
        }
    }
}