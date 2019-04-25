package com.tians.controller;
import com.tians.model.entity.Course;
import com.tians.service.CourseService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
@SuppressWarnings("all")
@Controller
@RequestMapping("/course")
public class CourseController {
    private Logger log =  LoggerFactory.getLogger(CourseController.class);
    /**
     * 注入对象
     */
    /*@Resource(name="courseService")*/
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    /* *
      Map Model  ModelAndView 三种效果一样
      +RestFul风格
     */
    @RequestMapping("/view")
    public String viewcourse(@RequestParam(value = "id",defaultValue = "123") Integer id, Map map){
        Course course=courseService.getCourseById(id);
        map.put("course",course);
        return "course_overview";
    }
    @RequestMapping("/view2/{id}")
    public String viewcourse2(@PathVariable(value = "id",required = true)Integer id, Model model){
        Course course=courseService.getCourseById(id);
        log.debug("In viewCourse, course = {}", course);
        model.addAttribute("course",course);
        return "course_overview";
    }
    /*  html代码中的java代码接收
    <%   request.setAttribute("id", "123");
		 request.getRequestDispatcher("/course/view3").forward(request,response);
	 %>
    */
    @RequestMapping("/view3")
    public String viewcourse3(@RequestAttribute(value = "id",required = true)Integer id, ModelAndView modelAndView){
        Course course=courseService.getCourseById(id);
        modelAndView.addObject("course",course);
        return "course_overview";
    }
     //可以用PostMan发送请求
    //HttpServletRequest HttpServletResponse HttpSession
    @RequestMapping("/view4")
    public String viewCourse4(HttpServletRequest request) {
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        Course course = courseService.getCourseById(courseId);
        request.setAttribute("course",course);
        return "course_overview";
    }
    /*http://localhost:8080/course/admin?add*/
    @RequestMapping(value="/admin",method = RequestMethod.GET,params = "add")
    public String createCourse(){
        return "course_admin/edit";
    }
    @RequestMapping(value = "/save",method =  RequestMethod.POST)
    public String dosave(Course course){
        //日志打印！
        log.info(ReflectionToStringBuilder.toString(course));
        ///System.out.println(course.toString());
       //这里应该保存到数据库
          /*
        需要时可以使用：
        forward:地址栏不发生变化，转发页面和转发到的页面可以共享request里面的数据.
        redirect:地址栏发生变化，不能共享数据.
        */
        return "redirect:view";
    }
    /*
    *   MultipartFile接口中定义了如下很多有用的方法
    *   getSize()  获取文件长度，用来决定允许上传的文件大小
    *   isEmpty()  判断上传文件是否为空文件，用来拒绝空文件上传
    *   getInputStream() 将文件读取为java.io.InputStream流对象
    *   getContentType()  获取文件类型，用来判断此类型是否需要上传
    *   transferTo(dest)  将上传文件写到服务器上的制定的文件
    *   getOriginalFilename() 上传时文件名和后缀
    *   getName() 表单username名
    * */
    @RequestMapping(value="/upload",method=RequestMethod.GET)
    public String showUploadPage(@RequestParam(value="multi",required = false)Boolean multi){
        //上传多个文件
        if(multi!=null&&multi){
            return "course_admin/multifile";
        }
        //上传单个文件
        return "course_admin/file";

    }
    @RequestMapping(value = "/doUpload" , method=RequestMethod.POST)
    public String doUploadFile(@RequestParam(value="file")MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
            log.info("文件名：",multipartFile.getName());
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),new File("D://"+
                    System.currentTimeMillis()+multipartFile.getName()));
        }
        return "success";
    }
    /*
      <p> 选择文件:<input type="file" name="files">
      <p> 选择文件:<input type="file" name="files">
    * */
    @RequestMapping(value="/doUpload2",method=RequestMethod.POST)
    public String doUploadFile2(@RequestParam(value="files")MultipartFile[] files) throws IOException {
        String path = "D://";
        //判断file数组不能为空并且长度大于0
        if(files!=null&&files.length>0){
            //循环获取file数组中得文件
            for(int i = 0;i<files.length;i++){
                MultipartFile file = files[i];
                //保存文件
                if(!file.isEmpty())
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File(path+System.currentTimeMillis()+file.getName()));
            }
        }
        return "success";
    }

    @RequestMapping(value="/doUpload3",method=RequestMethod.POST)
    public String doUploadFile3(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();

        while(fileNames.hasNext()){
            MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());
            if(!file.isEmpty()){
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File("D://"+System.currentTimeMillis()+file.getName()));
            }
        }

        return "success";
    }

    /*

    GetMapping的作用相当于@RequestMapping + @ResponseBody
    常见的还有常用于RestFul编程
    @PostMapping
    @DeleteMapping
    @PutMapping
    * */
    @RequestMapping(value="/{courseId}")
    @ResponseBody
    public Course getCourseInjson(@PathVariable(value = "courseId") Integer id){
       return courseService.getCourseById(id);
    }
    /*
    ResponseEntity ：标识整个http相应：状态码、头部信息、响应体内容(spring)
    @ResponseBody：加在请求处理方法上，能够处理方法结果值作为http响应体（springmvc）
    @ResponseStatus：加在方法上、返回自定义http状态码(spring)
    */
    @RequestMapping(value="/json/{courseId}")
    public ResponseEntity<Course>  getCourseInjson2(@PathVariable Integer courseId){
        return  new ResponseEntity<Course>(courseService.getCourseById(courseId),HttpStatus.OK);
    }
}
