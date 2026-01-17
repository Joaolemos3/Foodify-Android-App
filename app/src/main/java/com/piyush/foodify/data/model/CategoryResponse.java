package com.piyush.foodify.data.model;

import java.util.List;

public class CategoryResponse {
    private List<Category> categories;

    public CategoryResponse() {}

    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
}