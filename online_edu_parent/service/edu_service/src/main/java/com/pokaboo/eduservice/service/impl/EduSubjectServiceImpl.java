package com.pokaboo.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pokaboo.eduservice.entity.EduSubject;
import com.pokaboo.eduservice.entity.excel.ExcelSubjectData;
import com.pokaboo.eduservice.entity.subject.PrimarySubject;
import com.pokaboo.eduservice.entity.subject.SecondSubject;
import com.pokaboo.eduservice.listener.SubjectExcelListener;
import com.pokaboo.eduservice.mapper.EduSubjectMapper;
import com.pokaboo.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<PrimarySubject> findAllSubject() {
        List<PrimarySubject> subjects = new ArrayList<>();
        QueryWrapper<EduSubject> queryWrapperOne = new QueryWrapper<>();
        queryWrapperOne.eq("parent_id","0");
        queryWrapperOne.orderByAsc("sort", "id");
        List<EduSubject> eduSubjectListOne = baseMapper.selectList(queryWrapperOne);

        QueryWrapper<EduSubject> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id","0");
        queryWrapperTwo.orderByAsc("sort", "id");
        List<EduSubject> subjectListTwo = baseMapper.selectList(queryWrapperTwo);

        if(eduSubjectListOne != null && eduSubjectListOne.size() > 0){
            for (EduSubject eduSubjectOne : eduSubjectListOne) {
                PrimarySubject primarySubject = new PrimarySubject();
                BeanUtils.copyProperties(eduSubjectOne,primarySubject);


                List<SecondSubject> secondSubjectList = new ArrayList<>();
                if(subjectListTwo != null && subjectListTwo.size() > 0){
                    for (EduSubject eduSubjectTwo: subjectListTwo) {
                        if(eduSubjectTwo != null && eduSubjectOne.getId().equals(eduSubjectTwo.getParentId())){
                            SecondSubject secondSubject = new SecondSubject();
                            BeanUtils.copyProperties(eduSubjectTwo,secondSubject);
                            secondSubjectList.add(secondSubject);
                        }
                    }
                }
                primarySubject.setChildren(secondSubjectList);
                subjects.add(primarySubject);
            }
        }

        return subjects;
    }
}
