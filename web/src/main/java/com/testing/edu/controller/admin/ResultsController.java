package com.testing.edu.controller.admin;

import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.ResultDTO;
import com.testing.edu.entity.Result;
import com.testing.edu.service.ResultService;
import com.testing.edu.service.utils.ListToPageTransformer;
import com.testing.edu.service.utils.TypeConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/results/")
public class ResultsController {

    private static final Logger logger = Logger.getLogger(ResultsController.class);

    @Autowired
    private ResultService resultService;

    /**
     * Delete subject
     * @param resultId Long id of counter type
     * @return a response body with http status {@literal OK} if counter type
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{resultId}", method = RequestMethod.DELETE)
    public ResponseEntity removeSubject(@PathVariable Long resultId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            resultService.removeResult(resultId);
        } catch (Exception e) {
            logger.error("Got exeption while remove subject ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Get result with id
     * @param id Long id of result
     * @return testDTO
     */
    @RequestMapping(value = "get/{id}")
    public ResultDTO getResultByStudent(@PathVariable("id") Long id) {
        Result result = resultService.findById(id);
        ResultDTO resultDTO = new ResultDTO(
                result.getId(),
                result.getStudents().getLastName(),
                result.getStudents().getFirstName(),
                result.getStudents().getMiddleName(),
                result.getStudents().getGroups().getTitle(),
                result.getTests().getSubject().getTitle(),
                result.getTestTitle(),
                result.getTests().getType().name(),
                result.getMark(),
                result.getMaxGrade()
        );
        return resultDTO;
    }

    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<ResultDTO> pageResultsByStudentWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                             @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                             ResultDTO searchData) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        ListToPageTransformer<Result> queryResult = resultService.getResultByStudentBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<ResultDTO> content = toResultDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<ResultDTO> getResultStudentPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageResultsByStudentWithSearch(pageNumber, itemsPerPage, null, null, null);
    }



    public static List<ResultDTO> toResultDtoFromList(List<Result> list){
        List<ResultDTO> resultList = new ArrayList<>();
        for (Result result : list) {
            resultList.add(new ResultDTO(
                    result.getId(),
                    result.getStudents().getLastName(),
                    result.getStudents().getFirstName(),
                    result.getStudents().getMiddleName(),
                    result.getStudents().getGroups().getTitle(),
                    result.getTests().getSubject().getTitle(),
                    result.getTestTitle(),
                    result.getTests().getType().name(),
                    result.getMark(),
                    result.getMaxGrade()
            ));
        }
        return resultList;
    }

}
