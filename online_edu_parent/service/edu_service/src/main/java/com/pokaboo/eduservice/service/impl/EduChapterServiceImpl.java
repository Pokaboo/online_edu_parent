package com.pokaboo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pokaboo.eduservice.entity.EduChapter;
import com.pokaboo.eduservice.entity.EduVideo;
import com.pokaboo.eduservice.entity.vo.ChapterVo;
import com.pokaboo.eduservice.entity.vo.VideoVo;
import com.pokaboo.eduservice.mapper.EduChapterMapper;
import com.pokaboo.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pokaboo.eduservice.service.EduVideoService;
import com.pokaboo.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 根据课程id获取所有的章节以及课时信息
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> findAllChapterInfo(String courseId) {

        // 根据课程id获取章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        chapterWrapper.orderByAsc("sort", "id");
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterWrapper);

        // 根据课程id获取课时信息
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        videoWrapper.orderByAsc("sort", "id");
        List<EduVideo> eduVideoList = eduVideoService.list(videoWrapper);

        List<ChapterVo> chapterAndVideoList = new ArrayList<>();
        if(eduChapterList != null && eduChapterList.size() > 0){
            for (EduChapter eduChapter: eduChapterList) {
                ChapterVo chapterVo = new ChapterVo();
                BeanUtils.copyProperties(eduChapter,chapterVo);

                List<VideoVo> videoVoList = new ArrayList<>();
                for (EduVideo eduVideo:  eduVideoList) {
                    // 根据章节id判断课时是否属于章节
                    if(eduVideo.getChapterId().equals(eduChapter.getId())){
                        VideoVo videoVo = new VideoVo();
                        BeanUtils.copyProperties(eduVideo,videoVo);
                        videoVoList.add(videoVo);
                    }
                }
                chapterVo.setChildren(videoVoList);
                chapterAndVideoList.add(chapterVo);
            }
        }

        return chapterAndVideoList;
    }

    /**
     * 删除章节信息
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        // 如果该章节存在课时小节，不允许删除
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(videoQueryWrapper);
        if(count > 0){
            throw  new MyException(20001,"删除失败");
        }
        int deleteCount = baseMapper.deleteById(chapterId);
        return deleteCount > 0;
    }

    /**
     * 删除课程下的章节信息
     * @param courseId
     */
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        baseMapper.delete(chapterQueryWrapper);
    }
}
