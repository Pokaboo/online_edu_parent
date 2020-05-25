package com.pokaboo.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pokaboo.eduservice.entity.EduSubject;
import com.pokaboo.eduservice.entity.excel.ExcelSubjectData;
import com.pokaboo.eduservice.service.EduSubjectService;
import com.pokaboo.servicebase.exceptionhandler.MyException;

import java.sql.Wrapper;


/**
 * @author pokab
 * 使用EasyExcel 读取Excel 监听器
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    /**
     * EasyExcel 没有交给Spring管理，此时需要保存读取的数据是需要收到的传service
     */
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if(excelSubjectData == null){
            throw  new MyException(20001,"文件数据为空！");
        }

        // 一级分类添加
        EduSubject primarySubject = this.primarySubjectCheck(eduSubjectService, excelSubjectData.getOneSubjectName());
        if(primarySubject == null){
            primarySubject = new EduSubject();
            primarySubject.setParentId("0");
            primarySubject.setTitle(excelSubjectData.getOneSubjectName());
            eduSubjectService.save(primarySubject);
        }

        // 二级分类添加
        EduSubject secondarySubject = this.secondarySubjectCheck(eduSubjectService, excelSubjectData.getTwoSubjectName(), primarySubject.getId());
        if(secondarySubject == null){
            secondarySubject = new EduSubject();
            secondarySubject.setTitle(excelSubjectData.getTwoSubjectName());
            secondarySubject.setParentId(primarySubject.getId());
            eduSubjectService.save(secondarySubject);
        }
    }


    /**
     * 一级分类重复校验
     */
    private EduSubject primarySubjectCheck(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", "0");
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }

    /**
     * 二级分类重复校验
     */
    private EduSubject secondarySubjectCheck(EduSubjectService eduSubjectService, String name, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", pid);
        EduSubject eduSubject = eduSubjectService.getOne(queryWrapper);
        return eduSubject;
    }



    /**
     *  读取完成后执行
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
