package com.pokaboo.eduservice.service;

import com.pokaboo.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-24
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(EduSubjectService eduSubjectService, MultipartFile file);
}
