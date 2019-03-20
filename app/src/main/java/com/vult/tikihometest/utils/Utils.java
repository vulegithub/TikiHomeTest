package com.vult.tikihometest.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static final String EMPTY_CHARACTER = "";
    public static final String BLANK_CHARACTER = " ";
    public static final String BREAK_LINE_CHARACTER = "\n";

    public static String convertToTwoLinesValue(String keyword) {

        List<String> wordList = new ArrayList<>(Arrays.asList(keyword.split(BLANK_CHARACTER)));

        // return original value if only 0 or 1 word
        if (wordList.size() == 0 || wordList.size() == 1) {
            return keyword;
        }

        // if two words, only need to replace blank character by break line character.
        if (wordList.size() == 2) return keyword.replace(BLANK_CHARACTER, BREAK_LINE_CHARACTER);

        String result = null;

        // total character of words from left to right based on increase of index
        int countOfChar = 0;
        for (int index = 0; index < wordList.size(); index++) {

            int lengthOfWord = wordList.get(index).length();
            countOfChar += lengthOfWord;

            // if total temp string >= 1/2 total in keyword then break line.
            if (countOfChar >= (keyword.length() - wordList.size() + 1) / 2) {
                if (index == 0) {
                    // replace blank by break line
                    result = new StringBuffer(keyword).
                            replace(countOfChar, countOfChar + 1, BREAK_LINE_CHARACTER).toString();
                } else {
                    // replace blank by break line
                    result = new StringBuffer(keyword).
                            replace(countOfChar - lengthOfWord + index - 1, countOfChar - lengthOfWord + index, BREAK_LINE_CHARACTER).toString();
                }
                break;
            }
        }

        return result;
    }
}
