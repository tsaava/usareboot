package com.usareboot.back.parser;

import com.usareboot.back.models.ParsedComment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CommentParser {
    private static final Pattern SIZE_PATTERN = Pattern.compile("размер\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern COLOR_PATTERN = Pattern.compile("цвет\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern QUANTITY_PATTERN = Pattern.compile("количество\\s+(\\d+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern LINK_PATTERN = Pattern.compile("ссылка\\s+(http\\S+)|http\\S+", Pattern.CASE_INSENSITIVE);

    public ParsedComment parse(String comment) {
        String size = extractMatch(comment, SIZE_PATTERN);
        String color = extractMatch(comment, COLOR_PATTERN);
        String quantity = extractMatch(comment, QUANTITY_PATTERN);
        String link = extractMatch(comment, LINK_PATTERN);

        return new ParsedComment(size, color, quantity, link);
    }

    private String extractMatch(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1) != null ? matcher.group(1) : matcher.group();
        }
        return null;
    }





}
