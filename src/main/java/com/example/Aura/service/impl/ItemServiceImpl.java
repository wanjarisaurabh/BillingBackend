package com.example.Aura.service.impl;

import com.example.Aura.entity.CategoryEntity;
import com.example.Aura.entity.ItemEntity;
import com.example.Aura.io.ItemRequest;
import com.example.Aura.io.ItemResponse;
import com.example.Aura.repository.CategoryRepository;
import com.example.Aura.repository.ItemRepository;
import com.example.Aura.service.CategoryService;
import com.example.Aura.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final FileUploadServiceImpl fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity existingCat = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Category not found : -"+request.getCategoryId()));
        newItem.setCategory(existingCat);
        newItem.setImgUrl(imgUrl);
        newItem = itemRepository.save(newItem);//which gives use the id
        return convertToResponse(newItem);
    }

    private ItemEntity convertToEntity(ItemRequest request) {

        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();

    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemEntity -> convertToResponse(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String id) {
        ItemEntity existingItem = itemRepository.findByItemId(id)
                .orElseThrow(() -> new RuntimeException("Item not Found "+id));
        boolean isFIleDeleted = fileUploadService.deleteFile(existingItem.getImgUrl());
        if(isFIleDeleted){
            itemRepository.delete(existingItem);
        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unaable to delete ");
        }

    }
}
