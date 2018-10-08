package com.ezyindustries.goes_englishcourse.Score;

import java.util.HashMap;
import java.util.Map;

public class scoreData {

    private String VocabularyScore;
    private String ToeflScore;
    private String Point;

    public scoreData() {

    }

    public scoreData(String vocabularyScore, String toeflScore, String point) {
        VocabularyScore = vocabularyScore;
        ToeflScore = toeflScore;
        Point = point;
    }

    public String getVocabularyScore() {
        return VocabularyScore;
    }

    public void setVocabularyScore(String vocabularyScore) {
        VocabularyScore = vocabularyScore;
    }

    public String getToeflScore() {
        return ToeflScore;
    }

    public void setToeflScore(String toeflScore) {
        ToeflScore = toeflScore;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }
}