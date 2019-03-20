package com.vult.tikihometest;


import com.vult.tikihometest.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.vult.tikihometest.utils.Utils.BREAK_LINE_CHARACTER;
import static com.vult.tikihometest.utils.Utils.EMPTY_CHARACTER;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Test
    public void convertToTwoLinesValue_empty() {
        assertEquals("convertToTwoLinesValue_empty is not correct", EMPTY_CHARACTER, Utils.convertToTwoLinesValue(EMPTY_CHARACTER));
    }

    @Test
    public void convertToTwoLinesValue_blank() {
        assertEquals("convertToTwoLinesValue_blank is not correct"
                , Utils.BLANK_CHARACTER,
                Utils.convertToTwoLinesValue(Utils.BLANK_CHARACTER));
    }

    @Test
    public void convertToTwoLinesValue_moreBlank() {
        String moreBlank = "        ";
        assertEquals("convertToTwoLinesValue_moreBlank is not correct"
                , moreBlank,
                Utils.convertToTwoLinesValue(moreBlank));
    }

    @Test
    public void convertToTwoLinesValue_oneWord() {
        assertEquals("convertToTwoLinesValue_oneWord is not correct", "xiaomi",
                Utils.convertToTwoLinesValue("xiaomi"));
    }

    @Test
    public void convertToTwoLinesValue_twoWords() {
        assertEquals("convertToTwoLinesValue_twoWords is not correct"
                , "tai" + BREAK_LINE_CHARACTER + "nghe",
                Utils.convertToTwoLinesValue("tai nghe"));
    }

    @Test
    public void convertToTwoLinesValue_threeWords_normal() {
        assertEquals("convertToTwoLinesValue_threeWords_normal is not correct"
                , "bitis" + BREAK_LINE_CHARACTER + "hunter x",
                Utils.convertToTwoLinesValue("bitis hunter x"));
    }

    @Test
    public void convertToTwoLinesValue_threeWords_longWordAtStart() {
        assertEquals("convertToTwoLinesValue_threeWords_longWordAtStart is not correct"
                , "Nguyễn" + BREAK_LINE_CHARACTER + "văn A",
                Utils.convertToTwoLinesValue("Nguyễn văn A"));
    }

    @Test
    public void convertToTwoLinesValue_threeWords_longWordAtMiddle() {
        assertEquals("convertToTwoLinesValue_threeWords_longWordAtMiddle is not correct"
                , "Là" + BREAK_LINE_CHARACTER + "maybelline nè",
                Utils.convertToTwoLinesValue("Là maybelline nè"));
    }

    @Test
    public void convertToTwoLinesValue_threeWords_longWordAtEnd() {
        assertEquals("convertToTwoLinesValue_threeWords_longWordAtEnd is not correct"
                , "tai nghe" + BREAK_LINE_CHARACTER + "bluetooth",
                Utils.convertToTwoLinesValue("tai nghe bluetooth"));
    }

    @Test
    public void convertToTwoLinesValue_longWords() {
        assertEquals("convertToTwoLinesValue_longWords is not correct"
                , "anh chính là" + BREAK_LINE_CHARACTER + "thanh xuân của em",
                Utils.convertToTwoLinesValue("anh chính là thanh xuân của em"));
    }
}