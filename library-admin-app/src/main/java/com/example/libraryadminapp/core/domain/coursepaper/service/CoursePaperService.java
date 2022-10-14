package com.example.libraryadminapp.core.domain.coursepaper.service;

import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperUpdateRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.response.CoursePaperListResponseModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoursePaperService {

    void createCoursePaper(final CoursePaperCreationRequestModel coursePaperCreationRequestModel);

    void updateCoursePaper(final CoursePaperUpdateRequestModel coursePaperUpdateRequestModel);

    void deleteCoursePaper(String coursePaperName);

    Page<CoursePaperListResponseModel> getAllCoursePapers();

    List<CoursePaperListResponseModel> searchCoursePapers(String searchName);

    List<CoursePaperListResponseModel> getAllAvailableCoursePapersForStudent(String studentName);

}
