package com.example.libraryadminapp.core.domain.coursepaper.service;

import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.response.CoursePaperListResponseModel;
import org.springframework.data.domain.Page;

public interface CoursePaperService {

    void createCoursePaper(final CoursePaperCreationRequestModel coursePaperCreationRequestModel);

    Page<CoursePaperListResponseModel> getAllCoursePapers();
}
