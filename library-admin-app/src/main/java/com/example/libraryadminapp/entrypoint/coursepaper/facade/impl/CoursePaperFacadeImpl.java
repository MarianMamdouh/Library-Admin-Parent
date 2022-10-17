package com.example.libraryadminapp.entrypoint.coursepaper.facade.impl;

import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperUpdateRequestModel;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.facade.CoursePaperFacade;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.service.CoursePaperService;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.response.CoursePaperListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CoursePaperFacadeImpl implements CoursePaperFacade {

    private final CoursePaperService coursePaperService;

    @Override
    public void createCoursePaper(final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO) {

        final CoursePaperCreationRequestModel coursePaperCreationRequestModel = buildCoursePaperCreationRequestModel(coursePaperCreationRequestDTO);
        coursePaperService.createCoursePaper(coursePaperCreationRequestModel);
    }

    @Override
    public void updateCoursePaper(final CoursePaperUpdateRequestDTO coursePaperUpdateRequestDTO) {

        final CoursePaperUpdateRequestModel coursePaperUpdateRequestModel = buildCoursePaperUpdateRequestModel(coursePaperUpdateRequestDTO);

        coursePaperService.updateCoursePaper(coursePaperUpdateRequestModel);
    }

    @Override
    public void deleteCoursePaper(final String coursePaperName) {

        coursePaperService.deleteCoursePaper(coursePaperName);
    }

    @Override
    public Page<CoursePaperListResponseDTO> getAllCoursePapers() {

        return coursePaperService.getAllCoursePapers()
                .map(coursePaperListResponseModel ->
                CoursePaperListResponseDTO
                        .builder()
                        .coursePaperName(coursePaperListResponseModel.getCoursePaperName())
                        .subjectName(coursePaperListResponseModel.getSubjectName())
                        .professorName(coursePaperListResponseModel.getProfessorName())
                        .price(coursePaperListResponseModel.getPrice())
                        .academicYear(coursePaperListResponseModel.getAcademicYear())
                        .facultyName(coursePaperListResponseModel.getFacultyName())
                        .build()
                );
    }

    @Override
    public Page<CoursePaperListResponseDTO> searchCoursePapers(final String searchName) {

        return coursePaperService.searchCoursePapers(searchName)
                .map(coursePaperListResponseModel ->
                CoursePaperListResponseDTO
                        .builder()
                        .coursePaperName(coursePaperListResponseModel.getCoursePaperName())
                        .subjectName(coursePaperListResponseModel.getSubjectName())
                        .professorName(coursePaperListResponseModel.getProfessorName())
                        .price(coursePaperListResponseModel.getPrice())
                        .build()
                );
    }

    @Override
    public List<CoursePaperListResponseDTO> searchCoursePapersByMobileNumber(final String searchName, final String mobileNumber) {

        return coursePaperService.searchCoursePapersByMobileNumber(searchName, mobileNumber)
                .stream()
                .map(coursePaperListResponseModel ->
                        CoursePaperListResponseDTO
                                .builder()
                                .coursePaperName(coursePaperListResponseModel.getCoursePaperName())
                                .subjectName(coursePaperListResponseModel.getSubjectName())
                                .professorName(coursePaperListResponseModel.getProfessorName())
                                .price(coursePaperListResponseModel.getPrice())
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<CoursePaperListResponseDTO> getAllAvailableCoursePapersForStudent(String mobileNumber) {

        return coursePaperService.getAllAvailableCoursePapersForStudent(mobileNumber)
                .stream()
                .map(coursePaperListResponseModel ->
                        CoursePaperListResponseDTO
                                .builder()
                                .coursePaperName(coursePaperListResponseModel.getCoursePaperName())
                                .subjectName(coursePaperListResponseModel.getSubjectName())
                                .professorName(coursePaperListResponseModel.getProfessorName())
                                .price(coursePaperListResponseModel.getPrice())
                                .academicYear(coursePaperListResponseModel.getAcademicYear())
                                .facultyName(coursePaperListResponseModel.getFacultyName())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private CoursePaperCreationRequestModel buildCoursePaperCreationRequestModel(final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO) {

        return CoursePaperCreationRequestModel
                .builder()
                .coursePaperName(coursePaperCreationRequestDTO.getCoursePaperName())
                .subjectName(coursePaperCreationRequestDTO.getSubjectName())
                .professorName(coursePaperCreationRequestDTO.getProfessorName())
                .price(coursePaperCreationRequestDTO.getPrice())
                .academicYear(coursePaperCreationRequestDTO.getAcademicYear())
                .facultyName(coursePaperCreationRequestDTO.getFacultyName())
                .build();
    }

    private CoursePaperUpdateRequestModel buildCoursePaperUpdateRequestModel(final CoursePaperUpdateRequestDTO coursePaperUpdateRequestDTO) {

        return CoursePaperUpdateRequestModel
                .builder()
                .coursePaperName(coursePaperUpdateRequestDTO.getCoursePaperName())
                .subjectName(coursePaperUpdateRequestDTO.getSubjectName())
                .professorName(coursePaperUpdateRequestDTO.getProfessorName())
                .price(coursePaperUpdateRequestDTO.getPrice())
                .academicYear(coursePaperUpdateRequestDTO.getAcademicYear())
                .facultyName(coursePaperUpdateRequestDTO.getFacultyName())
                .build();
    }
}
