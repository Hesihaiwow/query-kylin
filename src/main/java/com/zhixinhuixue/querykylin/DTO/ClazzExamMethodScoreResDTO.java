package com.zhixinhuixue.querykylin.DTO;


import java.math.BigDecimal;

public class ClazzExamMethodScoreResDTO {

    /**
     * 考点ID
     */
    private Integer methodId;

    /**
     * 考点名称
     */
    private String methodName;

    /**
     * 考试次数
     */
    private Integer examNum;

    /**
     * 题目数
     */
    private Integer topicNum;

    /**
     * 错题数
     */
    private Integer wrongNum;

    /**
     * 得分率
     */
    private BigDecimal scoreRatio;

    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getExamNum() {
        return examNum;
    }

    public void setExamNum(Integer examNum) {
        this.examNum = examNum;
    }

    public Integer getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Integer topicNum) {
        this.topicNum = topicNum;
    }

    public Integer getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(Integer wrongNum) {
        this.wrongNum = wrongNum;
    }

    public BigDecimal getScoreRatio() {
        return scoreRatio;
    }

    public void setScoreRatio(BigDecimal scoreRatio) {
        this.scoreRatio = scoreRatio;
    }
}
