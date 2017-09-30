package com.example.sohan.currencyconvertor.network;

/**
 * Enum defining constants for following request types
 * 1. Request of type JSON
 * 2. Request  of type JSON with auth header
 * 3. Request of type string
 * 4. Request  of type string with auth header
 */
public enum RequestType {
    REQUEST_GSON,
    REQUEST_AUTH_HEADER_GSON,
    REQUEST_STRING,
    REQUEST_AUTH_HEADER_STRING
}
