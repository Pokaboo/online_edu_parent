package com.pokaboo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pokaboo.eduservice.entity.EduTeacher;
import com.pokaboo.eduservice.mapper.EduTeacherMapper;
import com.pokaboo.eduservice.query.TeacherQuery;
import com.pokaboo.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Pokaboo
 * @since 2020-05-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     *  按天将分页查询
     * @param pageParam
     * @param teacherQuery
     */
    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQuery != null) {

            if (!StringUtils.isEmpty(teacherQuery.getName())) {
                queryWrapper.like("name", teacherQuery.getName());
            }
            if (null != teacherQuery.getLevel()) {
                queryWrapper.eq("level", teacherQuery.getLevel());
            }

            if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
                queryWrapper.ge("gmt_create", teacherQuery.getBegin());
            }

            if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
                queryWrapper.le("gmt_create", teacherQuery.getEnd());
            }
            baseMapper.selectPage(pageParam, queryWrapper);
        }

    }
}
