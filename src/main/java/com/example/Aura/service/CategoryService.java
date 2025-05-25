package com.example.Aura.service;

import com.example.Aura.io.CategoryRequest;
import com.example.Aura.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryResponse add(CategoryRequest request , MultipartFile file);

    List<CategoryResponse> read();

    void delete(String categoryId);
}
