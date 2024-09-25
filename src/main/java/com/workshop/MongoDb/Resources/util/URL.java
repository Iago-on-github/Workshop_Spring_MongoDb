package com.workshop.MongoDb.Resources.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class URL {
    public static String decodeParam(String text) {
        return URLDecoder.decode(text, StandardCharsets.UTF_8);
    }

    public static Instant convertDate(String textDate) {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("GMT"));
        try {
            return ZonedDateTime.parse(textDate, dt).toInstant();
        } catch (DateTimeException e) {
            throw new RuntimeException("Invalid date format", e);
        }
    }
}
