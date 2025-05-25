package com.example.Aura.service;

import com.example.Aura.io.ItemRequest;
import com.example.Aura.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

public interface ItemService {

    ItemResponse add(ItemRequest request, MultipartFile file);

    List<ItemResponse> fetchItems();

    void deleteItem(String id);
}
