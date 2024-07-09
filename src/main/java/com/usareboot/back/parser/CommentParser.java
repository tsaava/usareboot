package com.usareboot.back.parser;

import com.usareboot.back.models.ParsedComment;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommentParser {
    private static final Pattern SIZE_PATTERN = Pattern.compile("размер\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern COLOR_PATTERN = Pattern.compile("цвет\\s+([А-Яа-яA-Za-z0-9-]+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern COUNT_PATTERN = Pattern.compile("количество\\s+|кол-во\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
    private static final Pattern LINK_PATTERN = Pattern.compile("ссылка\\s+(http\\S+)|http\\S+", Pattern.CASE_INSENSITIVE);

    public ParsedComment parse(String comment) {
        String size = extractMatch(comment, SIZE_PATTERN);
        String color = extractMatch(comment, COLOR_PATTERN);
        String count = extractMatch(comment, COUNT_PATTERN);
        String link = extractMatch(comment, LINK_PATTERN);
        return new ParsedComment(size, color, count, link);
    }

    private String extractMatch(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
                return matcher.group(1)!= null ? matcher.group(1) : matcher.group();
        }
        return null;
    }
}
