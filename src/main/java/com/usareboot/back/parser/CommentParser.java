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
    private static final Pattern ONE_COUNT_PATTERN = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
    private static final Pattern ONE_SIZE_PATTERN = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);


    public ParsedComment parse(String comment) {
        try {
            String size = extractMatch(comment, SIZE_PATTERN);

            String size2 = extractMatch(comment, ONE_SIZE_PATTERN);
            String color = extractMatch(comment, COLOR_PATTERN);
            String count = extractMatch(comment, COUNT_PATTERN);
            String count2 = extractMatch(comment, ONE_COUNT_PATTERN);
            String link = extractMatch(comment, LINK_PATTERN);
            return new ParsedComment(size!=null?size:size2, color, count!=null?count:count2, link);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка парсинга комментария");
        }
    }

    private String extractMatch(String text, Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                log.info("true");
//                log.info("matcher.group(1): {}",matcher.group(1));
                log.info("matcher.group(1): {}",matcher.group());
                try {
                    return matcher.group(1);
                } catch(Exception e) {
                    return matcher.group();
                } //!=null ? matcher.group(1) : matcher.group();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка extractMatch");
        }
    }
}
