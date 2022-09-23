package com.example.libraryadminapp.entrypoint.coursepaper.facade;

import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.response.CoursePaperListResponseDTO;
import org.springframework.data.domain.Page;

public interface CoursePaperFacade {

    void createCoursePaper(final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO);

    Page<CoursePaperListResponseDTO> getAllCoursePapers();
}
