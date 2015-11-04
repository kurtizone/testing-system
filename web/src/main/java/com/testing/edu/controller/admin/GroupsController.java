package com.testing.edu.controller.admin;

import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.GroupDTO;
import com.testing.edu.entity.Groups;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.utils.ListToPageTransformer;
import com.testing.edu.service.utils.TypeConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupsController {

    private final Logger logger = Logger.getLogger(GroupsController.class);

    @Autowired
    private GroupsService groupsService;

    /**
     * Add group
     * @param groupDTO object with group data
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addGroup(@RequestBody GroupDTO groupDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            groupsService.addGroup(
                    groupDTO.getLastName(),
                    groupDTO.getFirstName(),
                    groupDTO.getMiddleName(),
                    groupDTO.getAcademicStatus(),
                    groupDTO.getDegree()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit group
     * @param groupDTO object with group data
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public ResponseEntity editGroup(@RequestBody GroupDTO groupDTO,
                                       @PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            groupsService.editGroup(
                    id,
                    groupDTO.getLastName(),
                    groupDTO.getFirstName(),
                    groupDTO.getMiddleName(),
                    groupDTO.getAcademicStatus(),
                    groupDTO.getDegree()
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete group
     * @param id Long id of group
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeGroup(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            groupsService.removeGroup(id);
        } catch (Exception e) {
            logger.error("Got exeption while remove group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Get group with id
     * @param id Long id of group
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}")
    public GroupDTO getGroup(@PathVariable("id") Long id) {
        Groups groups = groupsService.findById(id);
        GroupDTO groupDTO = new GroupDTO(
                groups.getId(),
                groups.getLastName(),
                groups.getFirstName(),
                groups.getMiddleName(),
                groups.getAcademicStatus().name(),
                groups.getDegree().name()
        );
        return groupDTO;
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
    public PageDTO<GroupDTO> pageGroupWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                       @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                       GroupDTO  searchData) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        ListToPageTransformer<Groups> queryResult = groupsService.getGroupBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<GroupDTO> content = toGroupDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<GroupDTO> getGroupPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageGroupWithSearch(pageNumber, itemsPerPage, null, null, null);
    }

    /**
     * Convert list of counter types to list CounterTypeDTO
     * @param list
     * @return
     */
    public static List<GroupDTO> toGroupDtoFromList(List<Groups> list){
        List<GroupDTO> resultList = new ArrayList<>();
        for (Groups group : list) {
            resultList.add(new GroupDTO(
                    group.getId(),
                    group.ge
                    group.getDegree().name()
            ));
        }
        return resultList;
    }

}
