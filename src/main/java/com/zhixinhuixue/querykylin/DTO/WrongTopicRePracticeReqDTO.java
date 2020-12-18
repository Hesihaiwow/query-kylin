package com.zhixinhuixue.querykylin.DTO;




import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


public class WrongTopicRePracticeReqDTO {

    private String semesterId;
    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 班级id
     */
    @NotNull(message = "班级ID不能为空")
    @NotEmpty(message = "班级ID不能为空")
    private List<String> clazzIds;


    /**
     * 班级名称
     */
    private List<String> clazzNames;

    /**
     * 限制个数
     */
    @NotNull(message = "限制个数不能为空")
    private int limit;

    /**
     * 学科id
     */
    @NotNull(message = "学科ID不能为空")
    private Integer subjectId;

    /**
     * 学科名称
     */
    private String subjectName;

    /**
     * 教程id
     */
    @NotNull(message = "教材ID不能为空")
    private Integer textbookId;

    /**
     * 教程名称
     */
    private String textbookName;

    /**
     * 考试次数
     */
    @NotNull(message = "考试次数不能为空")
    private Integer examNum;

    /**
     * 最小得分率
     */
    @NotNull(message = "最小得分率不能为空")
    private BigDecimal minScoreRatio;

    /**
     * 最大得分率
     */
    @NotNull(message = "最大得分率不能为空")
    private BigDecimal maxScoreRatio;

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getClazzIds() {
        return clazzIds;
    }

    public void setClazzIds(List<String> clazzIds) {
        this.clazzIds = clazzIds;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getTextbookId() {
        return textbookId;
    }

    public void setTextbookId(Integer textbookId) {
        this.textbookId = textbookId;
    }

    public Integer getExamNum() {
        return examNum;
    }

    public void setExamNum(Integer examNum) {
        this.examNum = examNum;
    }

    public BigDecimal getMinScoreRatio() {
        return minScoreRatio;
    }

    public void setMinScoreRatio(BigDecimal minScoreRatio) {
        this.minScoreRatio = minScoreRatio;
    }

    public BigDecimal getMaxScoreRatio() {
        return maxScoreRatio;
    }

    public void setMaxScoreRatio(BigDecimal maxScoreRatio) {
        this.maxScoreRatio = maxScoreRatio;
    }

    public List<String> getClazzNames() {
        return clazzNames;
    }

    public void setClazzNames(List<String> clazzNames) {
        this.clazzNames = clazzNames;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTextbookName() {
        return textbookName;
    }

    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }
}
