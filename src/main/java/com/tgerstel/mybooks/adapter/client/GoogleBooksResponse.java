package com.tgerstel.mybooks.adapter.client;

import java.util.List;

public record GoogleBooksResponse (int totalItems, List<GoogleBookItem> items) {
}