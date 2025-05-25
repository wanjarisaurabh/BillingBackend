package com.example.Aura.service.impl;

import com.example.Aura.entity.CategoryEntity;
import com.example.Aura.io.CategoryRequest;
import com.example.Aura.io.CategoryResponse;
import com.example.Aura.repository.CategoryRepository;
import com.example.Aura.repository.ItemRepository;
import com.example.Aura.service.CategoryService;
import com.example.Aura.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private  final FileUploadService fileUploadService;

    private  final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse add(CategoryRequest request  , MultipartFile file) {

String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);

        newCategory.setImgUrl(imgUrl);

      newCategory= categoryRepository.save(newCategory);



      //make sure u pass entity
      return convertToResponse(newCategory);


    }

    @Override
    public List<CategoryResponse> read() {
      return  categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
              .collect(Collectors.toList());

    }

    @Override
    public void delete(String categoryId) {
      CategoryEntity existingCategory =  categoryRepository.findByCategoryId(categoryId)
              .orElseThrow(()-> new RuntimeException("Category not found : CATEGORYID : "+categoryId));

fileUploadService.deleteFile(existingCategory.getImgUrl());

              categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {

        Integer ItemsCount =  itemRepository.countByCategoryId(newCategory.getId());

         return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .discription(newCategory.getDiscription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                 .updatedAt(newCategory.getUpdatedAt())
                 .items(ItemsCount)
                 .build();

    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
return CategoryEntity.builder()
        .categoryId(UUID.randomUUID().toString())
        .name(request.getName())
        .discription(request.getDiscription())
        .bgColor(request.getBgColor())
        .build();

    }
}
