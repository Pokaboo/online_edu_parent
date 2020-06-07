package com.pokaboo.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pokaboo
 * 课程发布信息传输对象
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String title;
    /**
     * 课程封面
     */
    private String cover;
    /**
     * 课时数
     */
    private Integer lessonNum;
    /**
     * 一级分类
     */
    private String subjectLevelOne;
    /**
     * 二级分类
     */
    private String subjectLevelTwo;
    /**
     * 课程讲师
     */
    private String teacherName;
    /**
     * 课程价格
     */
    private String price;
}