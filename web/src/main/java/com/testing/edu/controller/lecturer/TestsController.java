package com.testing.edu.controller.lecturer;

import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.Tests;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/lecturers/tests/")
public class TestsController {


    public static List<TestDTO> toTestDtoFromList(List<Tests> list){
        List<TestDTO> resultList = new ArrayList<>();
        for (Tests test : list) {
            resultList.add(new TestDTO(
                    test.getId(),
                    test.getTitle(),
                    test.getType().name(),
                    test.getMaxGrade(),
                    test.getAvaible()
            ));
        }
        return resultList;
    }
}
