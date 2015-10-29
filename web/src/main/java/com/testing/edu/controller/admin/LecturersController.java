package com.testing.edu.controller.admin;


import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.LecturerDTO;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/lecturers/")
public class LecturersController {

    private final Logger logger = Logger.getLogger(LecturersController.class);

    @Autowired
    private LecturersService lecturersService;

    /**
     * Add lecturer
     * @param lecturerDTO object with lecturer data
     * @return a response body with http status {@literal OK} if lecturer
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addLecturer(@RequestBody LecturerDTO lecturerDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            lecturersService.addLecturer(
                    lecturerDTO.getLastName(),
                    lecturerDTO.getFirstName(),
                    lecturerDTO.getMiddleName(),
                    lecturerDTO.getAcademicStatus(),
                    lecturerDTO.getDegree()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add lecturer ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit lecturer
     * @param lecturerDTO object with lecturer data
     * @return a response body with http status {@literal OK} if lecturer
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public ResponseEntity editLecturer(@RequestBody LecturerDTO lecturerDTO,
                                      @PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            lecturersService.editLecturer(
                    id,
                    lecturerDTO.getLastName(),
                    lecturerDTO.getFirstName(),
                    lecturerDTO.getMiddleName(),
                    lecturerDTO.getAcademicStatus(),
                    lecturerDTO.getDegree()
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing lecturer ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete lecturer
     * @param id Long id of lecturer
     * @return a response body with http status {@literal OK} if lecturer
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeLecturer(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            lecturersService.removeLecturer(id);
        } catch (Exception e) {
            logger.error("Got exeption while remove lecturer ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Get lecturer with id
     * @param id Long id of lecturer
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}")
    public LecturerDTO getLecturer(@PathVariable("id") Long id) {
        Lecturers lecturers = lecturersService.findById(id);
        LecturerDTO lecturerDTO = new LecturerDTO(
                lecturers.getId(),
                lecturers.getLastName(),
                lecturers.getFirstName(),
                lecturers.getMiddleName(),
                lecturers.getAcademicStatus().name(),
                lecturers.getDegree().name()
        );
        return lecturerDTO;
    }

    /**
     * Build page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param sortCriteria
     * @param sortOrder
     * @param searchData
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<LecturerDTO> pageLecturerWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                     @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                       LecturerDTO  searchData) {
        ListToPageTransformer<Lecturers> queryResult = lecturersService.getLecturerBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchData.getLastName(),
                searchData.getFirstName(),
                searchData.getMiddleName(),
                searchData.getAcademicStatus(),
                searchData.getDegree(),
                sortCriteria,
                sortOrder
        );
        List<LecturerDTO> content = toLecturerDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<LecturerDTO> getLecturerPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageLecturerWithSearch(pageNumber, itemsPerPage, null, null, null);
    }

    /**
     * Convert list of counter types to list CounterTypeDTO
     * @param list
     * @return
     */
    public static List<LecturerDTO> toLecturerDtoFromList(List<Lecturers> list){
        List<LecturerDTO> resultList = new ArrayList<>();
        for (Lecturers lecturer : list) {
            resultList.add(new LecturerDTO(
                    lecturer.getId(),
                    lecturer.getLastName(),
                    lecturer.getFirstName(),
                    lecturer.getMiddleName(),
                    lecturer.getAcademicStatus().name(),
                    lecturer.getDegree().name()
            ));
        }
        return resultList;
    }
}
