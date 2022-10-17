package com.example.libraryadminapp.core.domain.coursepaper.service;

import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperUpdateRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.response.CoursePaperListResponseModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoursePaperService {

    void createCoursePaper(CoursePaperCreationRequestModel coursePaperCreationRequestModel);

    void updateCoursePaper(CoursePaperUpdateRequestModel coursePaperUpdateRequestModel);

    void deleteCoursePaper(String coursePaperName);

    Page<CoursePaperListResponseModel> getAllCoursePapers();

    Page<CoursePaperListResponseModel> searchCoursePapers(String searchName);
    List<CoursePaperListResponseModel> searchCoursePapersByMobileNumber(String searchName, String mobileNumber);

    List<CoursePaperListResponseModel> getAllAvailableCoursePapersForStudent(String mobileNumber);

}
