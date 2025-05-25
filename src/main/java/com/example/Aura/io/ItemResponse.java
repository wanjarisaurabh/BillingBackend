package com.example.Aura.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {



    private String itemId;
    private String name ;
    private String categoryId;
    private String description;
    private BigDecimal price;
    private String imgUrl;
    private String categoryName;
    private String categoryIS;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
