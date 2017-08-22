package com.gillyweed.android.asklah.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Envy 15 on 22/8/2017.
 */

public class Achievement {
    @SerializedName("question_no")
    private int questionNo;

    @SerializedName("answer_no")
    private int answerNo;

    @SerializedName("comment_no")
    private int commentNo;

    @SerializedName("points")
    private int points;

    public int getQuestionNo()
    {
        return this.questionNo;
    }

    public int getAnswerNo()
    {
        return this.answerNo;
    }

    public int getCommentNo()
    {
        return this.commentNo;
    }

    public int getPoints()
    {
        return this.points;
    }
}
