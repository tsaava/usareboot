package com.usareboot.back.parser;

import com.usareboot.back.models.ParsedComment;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommentParser {
//    private static final Pattern SIZE_PATTERN = Pattern.compile("размер\\s+(\\w+)|Размер:\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
//    private static final Pattern COLOR_PATTERN = Pattern.compile("цвет\\s+([А-Яа-яA-Za-z0-9-]+)", Pattern.CASE_INSENSITIVE);
//    private static final Pattern COUNT_PATTERN = Pattern.compile("количество\\s+|кол-во\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
//    private static final Pattern LINK_PATTERN = Pattern.compile("ссылка\\s+(http\\S+)|http\\S+", Pattern.CASE_INSENSITIVE);
//    private static final Pattern ONE_COUNT_PATTERN = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
//    private static final Pattern ONE_SIZE_PATTERN = Pattern.compile("[a-zA-Z]+", Pattern.CASE_INSENSITIVE);

    //    private static final Pattern SIZE_PATTERN_NEW = Pattern.compile("(\\d+[./-]?\\d*|\\.\\d+)", Pattern.CASE_INSENSITIVE);
// Полная таблица транслитерации (рус → латиница)
    private static final Map<Character, String> TRANSLIT_MAP = Map.ofEntries(
            Map.entry('Л', "L"), Map.entry('л', "l"),
            Map.entry('М', "M"), Map.entry('м', "m"),
            Map.entry('С', "S"), Map.entry('с', "s"),
            Map.entry('Х', "X"), Map.entry('х', "x")
    );

    public ParsedComment parse(String comment) {
        ParsedComment result = new ParsedComment();

        String normalizedComment = transliterateRussian(comment);
        // Ищем все возможные числа и размеры с разделителями / или -
//        Matcher matcher = Pattern.compile("(\\d+[./-]?\\d*|\\.\\d+)").matcher(comment);
        Matcher matcher = Pattern.compile(
                "(\\d+[./-]?\\d*[A-Za-z]?)" +  // Числовые размеры (42, 36.5, 40/41)
                        "|([A-Za-z]{1,5}\\d*[A-Za-z]?)"  // Буквенные размеры (XL, р42)
        ).matcher(normalizedComment);
        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        if (matches.isEmpty()) {
            return result; // Не найдено чисел/размеров
//            throw new RuntimeException("Ошибка парсинга комментария");
        }

        // Случай: только число <5 (это количество, размера нет)
        if (matches.size() == 1 && isValidQuantity(matches.get(0))) {
            result.setCount(matches.get(0));
            return result;
        }

        // Проверяем количество (<5) и определяем размер
        if (matches.size() >= 2) {
            String first = matches.get(0);
            String second = matches.get(1);

            if (isValidQuantity(second)) {
                result.setCount(second.replaceAll("[^0-9]", ""));
                result.setSize(first.toUpperCase());
            } else {
                result.setSize(second.toUpperCase());
            }
        } else {
            result.setSize(matches.get(0).toUpperCase());
        }

        return result;
    }

    // Транслитерация русских букв в латиницу
    private String transliterateRussian(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            String translitChar = TRANSLIT_MAP.getOrDefault(c, String.valueOf(c));
            result.append(translitChar);
        }
        return result.toString();
    }

    private boolean isValidQuantity(String numberStr) {
        try {
            // Удаляем все нецифровые символы (для случаев типа "2шт")
            String cleanNumber = numberStr.replaceAll("[^0-9]", "");
            if (cleanNumber.isEmpty()) return false;

            int quantity = Integer.parseInt(cleanNumber);
            return quantity < 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // Ищем все возможные числа и размеры с разделителями / или -
    /*public ParsedComment parse(String comment) {
        try {
            String size = extractMatch(comment, SIZE_PATTERN);
            String size2 = extractMatch(comment, ONE_SIZE_PATTERN);
            String color = extractMatch(comment, COLOR_PATTERN);
            String count = extractMatch(comment, COUNT_PATTERN);
            String count2 = extractMatch(comment, ONE_COUNT_PATTERN);
            String link = extractMatch(comment, LINK_PATTERN);
            if (count != null && Integer.parseInt(count) > 5) {
//                size = count;
                count = "1";
            }
            return new ParsedComment(size != null ? size : size2, color, count != null ? count : count2, link);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка парсинга комментария");
        }
    }*/

    /*private String extractMatch(String text, Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                try {
                    return matcher.group(1);
                } catch (Exception e) {
                    return matcher.group();
                } //!=null ? matcher.group(1) : matcher.group();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка extractMatch");
        }
    }*/
}
