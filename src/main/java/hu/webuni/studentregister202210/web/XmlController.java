package hu.webuni.studentregister202210.web;

import hu.webuni.studentregister202210.service.ExternalFreeSemesterService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/xml")
public class XmlController {

    private final ExternalFreeSemesterService externalFreeSemesterService;


    @GetMapping("/{id}")
    public int getFinancedSemesterNumberWS(@PathVariable("id") Long externalStudentId){
        log.info("Id is :"+externalStudentId);
        log.debug("alive");
        return externalFreeSemesterService.getFinancedSemesterNumberWS(externalStudentId);
    }
    @GetMapping("/async/{id}")
    public int getFinancedSemesterNumberWSAsync(@PathVariable("id") Long externalStudentId){
        log.info("Id is :"+externalStudentId);
        log.debug("alive");
        return externalFreeSemesterService.getFinancedSemesterNumberWSAsync(externalStudentId);
    }
}
