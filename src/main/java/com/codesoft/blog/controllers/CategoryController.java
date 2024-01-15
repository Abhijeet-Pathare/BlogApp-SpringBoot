package com.codesoft.blog.controllers;

import com.codesoft.blog.entities.Category;
import com.codesoft.blog.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.codesoft.blog.payloads.CategoryDto;
import com.codesoft.blog.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	//create
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
	}

	//update
	@PutMapping("/categories/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable(value = "catId") Integer categoryId){
		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
	}


	//delete
	@DeleteMapping("/categories/{catId}")
	public void deleteCategory(@PathVariable(value = "catId") Integer categoryId){
		categoryService.deleteCategory(categoryId);
	}
	//get
	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Integer categoryId){
		CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}

	//getAll
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> findAllCategories(){
		List<CategoryDto> listOfCategoryDtos = categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDto>>(listOfCategoryDtos,HttpStatus.OK);
	}
}
