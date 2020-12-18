package com.zhixinhuixue.querykylin.controller;

import com.zhixinhuixue.querykylin.DTO.ClazzExamMethodScoreResDTO;
import com.zhixinhuixue.querykylin.DTO.WrongTopicRePracticeReqDTO;
import com.zhixinhuixue.querykylin.entity.ZSYResult;
import com.zhixinhuixue.querykylin.entity.MethodInfo;
import org.apache.kylin.jdbc.shaded.com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO
 *
 * @author hsh
 * @create 2020年12月17日
 */
@RestController
public class KylinController {


    @Autowired
    @Qualifier("kylinTemplate")
    private JdbcTemplate kylinTemplate;

    @PostMapping("/clazz/method/scoreratio")
    public ZSYResult<List<ClazzExamMethodScoreResDTO>> methodScoreRatioLimit(@Valid @RequestBody WrongTopicRePracticeReqDTO wrongTopicRePracticeReqDTO) {


        // 处理传入参数
        String beginTime = wrongTopicRePracticeReqDTO.getBeginTime();
        int beginInt = Integer.parseInt(beginTime.replaceAll("-", ""));

        String endTime = wrongTopicRePracticeReqDTO.getEndTime();
        int endInt = Integer.parseInt(endTime.replaceAll("-", ""));

        List<String> clazzIds = wrongTopicRePracticeReqDTO.getClazzIds();
        StringBuffer classString = new StringBuffer();
        if (!CollectionUtils.isEmpty(clazzIds)) {
            for (int i = 0; i < clazzIds.size(); i++) {
                if (i < clazzIds.size() - 1) {
                    classString.append("'").append(clazzIds.get(i)).append("'").append(",");
                } else {
                    classString.append("'").append(clazzIds.get(i)).append("'");
                }
            }
        }
        String classList = classString.toString();

        Integer subjectId = wrongTopicRePracticeReqDTO.getSubjectId();
        Integer textbookId = wrongTopicRePracticeReqDTO.getTextbookId();
        double minScoreRatio = wrongTopicRePracticeReqDTO.getMinScoreRatio().doubleValue() / 100;
        double maxScoreRatio = wrongTopicRePracticeReqDTO.getMaxScoreRatio().doubleValue() / 100;


        // 拼接sql
        String sql = "SELECT\n" +
                "\tmethod_id,\n" +
                "\tsum( scoring ) / sum( score ) AS score_rate,\n" +
                "\tsum( is_wrong ) AS wrong_num,\n" +
                "\tcount( DISTINCT exam_id ) AS exam_num,\n" +
                "\tcount( DISTINCT exam_topic ) AS topic_num \n" +
                "FROM\n" +
                "\texam_student_topic_extend \n" +
                "WHERE\n" +
                "\texam_time >= " + beginInt + "\n" +
                "\tAND exam_time <= " + endInt + "\n" +
                "\tAND class_id IN (" + classList + ")\n" +
                "\tAND subject_id = " + subjectId + " \n" +
                "\tAND text_book_id = " + textbookId + " \n" +
                "GROUP BY\n" +
                "\tmethod_id \n" +
                "HAVING\n" +
                "\tscore_rate >= " + minScoreRatio + " \n" +
                "\tAND score_rate <= " + maxScoreRatio + " \n" +
                "ORDER BY\n" +
                "\tscore_rate,\n" +
                "\tmethod_id";


        List<MethodInfo> query = kylinTemplate.query(sql, new MethodInfoMapper());

        List<ClazzExamMethodScoreResDTO> objects = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(query)) {
            for (int i = 0; i < query.size(); i++) {
                ClazzExamMethodScoreResDTO clazzExamMethodScoreResDTO = new ClazzExamMethodScoreResDTO();
                BeanUtils.copyProperties(query.get(i), clazzExamMethodScoreResDTO);
                objects.add(clazzExamMethodScoreResDTO);
            }
        }
        return ZSYResult.success().data(objects);
    }


    /**
     * 映射结果
     */
    class MethodInfoMapper implements RowMapper<MethodInfo>{

        @Override
        public MethodInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.setMethodId(resultSet.getInt("method_id"));
            methodInfo.setScoreRatio(resultSet.getDouble("score_rate"));
            methodInfo.setWrongNum(resultSet.getLong("wrong_num"));
            methodInfo.setExamNum(resultSet.getLong("exam_num"));
            methodInfo.setTopicNum(resultSet.getLong("topic_num"));
            return methodInfo;
        }
    }


}