package com.zhixinhuixue.querykylin.entity;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author hsh
 * @create 2020年12月17日
 */
public class MethodInfo {


    private Integer methodId;

    private Integer wrongNum;

    private BigDecimal scoreRatio;

    private Integer examNum;

    private Integer topicNum;


    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
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
}