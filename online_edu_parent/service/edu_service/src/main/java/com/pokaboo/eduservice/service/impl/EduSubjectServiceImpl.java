package com.pokaboo.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.pokaboo.eduservice.entity.EduSubject;
import com.pokaboo.eduservice.entity.excel.ExcelSubjectData;
import com.pokaboo.eduservice.listener.SubjectExcelListener;
import com.pokaboo.eduservice.mapper.EduSubjectMapper;
import com.pokaboo.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(EduSubjectService eduSubjectService, MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
