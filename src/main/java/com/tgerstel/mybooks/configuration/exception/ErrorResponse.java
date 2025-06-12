package com.tgerstel.mybooks.configuration.exception;

public record ErrorResponse(int statusCode, String reason) {
}

