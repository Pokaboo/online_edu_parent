package com.pokaboo.eduservice.service;

import com.pokaboo.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pokaboo.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pookaboo
 * @since 2020-05-31
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id获取所有的章节以及课时信息
     * @param courseId
     * @return
     */
    List<ChapterVo> findAllChapterInfo(String courseId);

    /**
     * 删除章节信息
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);
}
