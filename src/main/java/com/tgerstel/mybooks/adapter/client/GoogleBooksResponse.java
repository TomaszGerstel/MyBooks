package com.tgerstel.mybooks.adapter.client;

import lombok.Data;

import java.util.List;

@Data
public class GoogleBooksResponse {
    private int totalItems;
    private List<GoogleBookItem> items;
}