package com.example.Aura.io;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Builder
public class CategoryResponse {


    private String categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String imgUrl;
    private String name;
    private String discription;
    private String bgColor;
    private Integer items;
}
