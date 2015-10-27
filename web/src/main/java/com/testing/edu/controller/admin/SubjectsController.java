package com.testing.edu.controller.admin;

import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.entity.Subject;
import com.testing.edu.service.SubjectService;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/subjects/")
public class SubjectsController {
    private final Logger logger = Logger.getLogger(SubjectsController.class);

    @Autowired
    private SubjectService subjectService;

    /**
     * Add subject
     * @param subjectDTO object with subject data
     * @return a response body with http status {@literal OK} if subject
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addSubject(@RequestBody SubjectDTO subjectDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            subjectService.addSubject(
                    subjectDTO.getTitle(),
                    subjectDTO.getMultiplier(),
                    subjectDTO.getHours()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add subject ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit subject
     * @param subjectDTO object with subject data
     * @return a response body with http status {@literal OK} if subject
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{subjectId}", method = RequestMethod.POST)
    public ResponseEntity editSubject(@RequestBody SubjectDTO subjectDTO,
                                          @PathVariable Integer subjectId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            subjectService.editSubject(
                    new Long(subjectId),
                    subjectDTO.getTitle(),
                    subjectDTO.getMultiplier(),
                    subjectDTO.getHours()
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing subject ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete subject
     * @param subjectId Long id of counter type
     * @return a response body with http status {@literal OK} if counter type
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{subjectId}", method = RequestMethod.DELETE)
    public ResponseEntity removeSubject(@PathVariable Integer subjectId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            subjectService.removeSubject(new Long(subjectId));
        } catch (Exception e) {
            logger.error("Got exeption while remove subject ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Get subject with id
     * @param id Integer id of subject
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}")
    public SubjectDTO getSubject(@PathVariable("id") Integer id) {
        Subject subject = subjectService.findById(new Long(id));
        SubjectDTO subjectDTO = new SubjectDTO(
                subject.getId(),
                subject.getTitle(),
                subject.getMultiplier(),
                subject.getMultiplier()
        );
        return subjectDTO;
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
    public PageDTO<SubjectDTO> pageSubjectWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                             @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                             SubjectDTO searchData) {
        ListToPageTransformer<Subject> queryResult = subjectService.getSubjectBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchData.getTitle(),
                searchData.getMultiplier(),
                searchData.getHours(),
                sortCriteria,
                sortOrder
        );
        List<SubjectDTO> content = toCounterTypeDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<SubjectDTO> getCounterTypePage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageSubjectWithSearch(pageNumber, itemsPerPage, null, null, null);
    }

    /**
     * Convert list of counter types to list CounterTypeDTO
     * @param list
     * @return
     */
    public static List<SubjectDTO> toCounterTypeDtoFromList(List<Subject> list){
        List<SubjectDTO> resultList = new ArrayList<>();
        for (Subject subject : list) {
            resultList.add(new SubjectDTO(
                    subject.getId(),
                    subject.getTitle(),
                    subject.getMultiplier(),
                    subject.getHours()
            ));
        }
        return resultList;
    }
}
