package com.pokaboo.eduservice.service;

import com.pokaboo.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pokaboo.eduservice.entity.subject.PrimarySubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-24
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param eduSubjectService
     * @param file
     */
    void addSubject(EduSubjectService eduSubjectService, MultipartFile file);

    /**
     * 获取所有的课程分类
     * @return
     */
    List<PrimarySubject> findAllSubject();
}
