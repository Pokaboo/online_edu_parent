package com.pokaboo.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pokab
 * 一级分类
 */
@Data
public class PrimarySubject {
    private String id;
    private String title;
    private List<SecondSubject> children  = new ArrayList<>();
}
