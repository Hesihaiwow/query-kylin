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

    private Long wrongNum;

    private Double scoreRatio;

    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }

    private Long examNum;

    private Long topicNum;


    public Long getWrongNum() {
        return wrongNum;
    }

    public void setWrongNum(Long wrongNum) {
        this.wrongNum = wrongNum;
    }

    public Double getScoreRatio() {
        return scoreRatio;
    }

    public void setScoreRatio(Double scoreRatio) {
        this.scoreRatio = scoreRatio;
    }

    public Long getExamNum() {
        return examNum;
    }

    public void setExamNum(Long examNum) {
        this.examNum = examNum;
    }

    public Long getTopicNum() {
        return topicNum;
    }

    public void setTopicNum(Long topicNum) {
        this.topicNum = topicNum;
    }
}