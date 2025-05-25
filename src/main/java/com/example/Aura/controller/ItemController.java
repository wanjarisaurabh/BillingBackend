package com.example.Aura.controller;

import com.example.Aura.io.ItemRequest;
import com.example.Aura.io.ItemResponse;
import com.example.Aura.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;



//add , delete -- only admin
@RestController
@RequiredArgsConstructor
public class ItemController {


    private final ItemService itemService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/items")
    public ItemResponse addItem(@RequestPart("item")String itemString , @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;
        try {
            itemRequest = objectMapper.readValue(itemString,ItemRequest.class);
          return  itemService.add(itemRequest, file);
        }catch (JsonProcessingException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Error Occured");
        }
    }




    @GetMapping("/items")
    public List<ItemResponse> readItem() {
        return itemService.fetchItems();
    }



    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/items/{itemId}")
    public void removeItem(@PathVariable String itemId){
    try {
    itemService.deleteItem(itemId);
    }catch (Exception e){
    throw  new ResponseStatusException(HttpStatus.NOT_FOUND , "Item not found "+e.getMessage());
    }
    }



}
