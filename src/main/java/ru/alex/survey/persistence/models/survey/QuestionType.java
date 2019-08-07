package ru.alex.survey.persistence.models.survey;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum QuestionType {
    SINGLE_CHOICE("Single choice", Constants.CHOICE_TYPE),
    MULTIPLE_CHOICE("Multiple choice", Constants.CHOICE_TYPE),
    STRING_INPUT("String input", Constants.INPUT_TYPE);

    private final String fullName;
    private final String type;

    public String getFullName() {
        return fullName;
    }

    public String getType() {
        return type;
    }

    public static class Constants {
        public static final String CHOICE_TYPE = "choice";
        public static final String INPUT_TYPE = "input";
    }
}
