package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class LanguagePojo {
    private String languageId;
    private String language;

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "LanguagePojo{" +
                "languageId='" + languageId + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
