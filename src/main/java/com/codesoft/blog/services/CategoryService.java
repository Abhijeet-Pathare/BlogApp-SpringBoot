package com.codesoft.blog.services;

import java.util.List;

import com.codesoft.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//CREATE
	public CategoryDto createCategory(CategoryDto categoryDto);
	//UPDATE
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//DELETE 
	public void deleteCategory(Integer categoryId);
	
	//GET
	public CategoryDto getCategoryById(Integer categoryId);
	
	//GET ALL
	public List<CategoryDto> getAllCategories();
	
}
