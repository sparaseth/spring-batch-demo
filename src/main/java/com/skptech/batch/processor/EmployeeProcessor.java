package com.skptech.batch.processor;

import com.skptech.batch.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeProcessor.class);

    @Override
    public Employee process(Employee employee) throws Exception {
        LOGGER.info("&&&&&&& EmployeeProcessor->process &&&&&&&");
        /*
        TODO - Add additional logic like filter/transform etc if required
        if(employee.getCountry().equals("United States")) {
            return employee;
        }else{
            return null;
        }
        */
        return employee;
    }

}
