package com.tians.service;


import com.tians.model.entity.Course;

/**
 * 课程业务功能接口
 */
public interface CourseService {
    /**
     * 通过ID获取课程
     *
     * @param courseId
     * @return
     */
    Course getCourseById(Integer courseId);
}
