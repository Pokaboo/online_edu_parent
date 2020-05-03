package com.pokaboo.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pokaboo.eduservice.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Pokaboo
 * @since 2020-05-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 根据条件 分页查询
     * @param pageParam
     * @param teacherQuery
     */
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}
