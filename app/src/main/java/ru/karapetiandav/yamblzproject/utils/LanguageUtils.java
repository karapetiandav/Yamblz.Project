package ru.karapetiandav.yamblzproject.utils;


import ru.karapetiandav.yamblzproject.data.model.Language;

public class LanguageUtils {

    public Language getSupportedLanguageByText(String text) {
        if (text.matches(".*[а-я].*") || text.matches(".*[А-Я].*")) {
            return Language.RUS;
        } else {
            return Language.ENG;
        }
    }
}
