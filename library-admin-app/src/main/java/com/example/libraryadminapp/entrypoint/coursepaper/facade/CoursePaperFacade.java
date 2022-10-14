package com.example.libraryadminapp.entrypoint.coursepaper.facade;

import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.response.CoursePaperListResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoursePaperFacade {

    void createCoursePaper(CoursePaperCreationRequestDTO coursePaperCreationRequestDTO);

    void updateCoursePaper(CoursePaperUpdateRequestDTO coursePaperUpdateRequestDTO);

    void deleteCoursePaper(String coursePaperName);

    List<CoursePaperListResponseDTO> getAllCoursePapers();

    List<CoursePaperListResponseDTO> searchCoursePapers(String searchName);

    List<CoursePaperListResponseDTO> getAllAvailableCoursePapersForStudent(String studentName);


}
