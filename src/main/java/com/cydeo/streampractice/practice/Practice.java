package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.model.*;
import com.cydeo.streampractice.service.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

//--Display all the employees-------------------------------------------------------------------------------------------
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
    }

//--Display all the countries-------------------------------------------------------------------------------------------
    public static List<Country> getAllCountries() {
        return countryService.readAll();
    }

//--Display all the departments-----------------------------------------------------------------------------------------
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
    }

//--Display all the jobs------------------------------------------------------------------------------------------------
    public static List<Job> getAllJobs() {
        return jobService.readAll();
    }

//--Display all the locations-------------------------------------------------------------------------------------------
    public static List<Location> getAllLocations() {
        return locationService.readAll();
    }

//--Display all the regions---------------------------------------------------------------------------------------------
    public static List<Region> getAllRegions() {
        return regionService.readAll();
    }

//--Display all the job histories---------------------------------------------------------------------------------------
    public static List<JobHistory> getAllJobHistories() {
     return jobHistoryService.readAll();
    }

//--Display all the employees' first names------------------------------------------------------------------------------
    public static List<String> getAllEmployeesFirstName() {
       return employeeService.readAll().stream().map(Employee::getFirstName).collect(Collectors.toList());
    }

//--Display all the countries' names------------------------------------------------------------------------------------
    public static List<String> getAllCountryNames() {
        return countryService.readAll().stream().map(Country::getCountryName).collect(Collectors.toList());
    }

//--Display all the departments' managers' first names------------------------------------------------------------------
    public static List<String> getAllDepartmentManagerFirstNames() {
       return departmentService.readAll().stream()
               .map(Department::getManager).map(man->man.getFirstName()).collect(Collectors.toList());
                   }

//--Display all the departments where manager name of the department is 'Steven'----------------------------------------
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return departmentService.readAll().stream()
                .filter(x->x.getManager().getFirstName().equals("Steven")).collect(Collectors.toList());
    }

//--Display all the departments where postal code of the location of the department is '98199'--------------------------
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
       return departmentService.readAll().stream()
               .filter(x->x.getLocation().getPostalCode().equals("98199")).collect(Collectors.toList());
    }

//--Display the region of the IT department-----------------------------------------------------------------------------
    public static Region getRegionOfITDepartment() throws Exception {
                                                                                                   //alternative(alt)1
//        return getAllDepartments().stream()
//                .filter(department -> department.getDepartmentName().equals("IT"))
//                .findFirst().get().getLocation().getCountry().getRegion();

        return getAllDepartments().stream()                                                            //alt2
                .filter(department -> department.getDepartmentName().equals("IT"))
                .findFirst().orElseThrow(() -> new Exception("No department found!"))
                .getLocation().getCountry().getRegion();

    }

//--Display all the departments where the region of department is 'Europe'----------------------------------------------
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return departmentService.readAll().stream()
                .filter(department -> department.getLocation().getCountry().getRegion().equals("Europe"))
                .collect(Collectors.toList());
    }

//--Display if there is any employee with salary less than 1000. If there is none, the method should return true--------
    public static boolean checkIfThereIsNoSalaryLessThan1000() {
    //   return employeeService.readAll().stream().allMatch(employee -> employee.getSalary() > 1000);              alt1
    //   return employeeService.readAll().stream().noneMatch((employee -> employee.getSalary()<1000));             alt2

        return !getAllEmployees().stream()                                                                        //alt3
                .anyMatch(employee -> employee.getSalary() < 1000);
    }

//--Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)--------------
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        return employeeService.readAll().stream()                                                                 //alt1
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT")) //At this point we have all IT employees
                .allMatch(x->x.getSalary()>2000);

//         return getAllEmployees().stream().filter(employee -> employee.getDepartment().equals("IT"))              alt2
//                 .noneMatch(employee -> employee.getSalary()<2000);

//            return getAllEmployees().stream()                                                                     alt3
//                    .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
//                    .map(Employee::getSalary)
//                    .noneMatch(salary -> salary < 2000);

//        return !getAllEmployees().stream().filter(employee -> employee.getDepartment().equals("IT"))              alt4
//                .anyMatch(employee -> employee.getSalary()<2000);

    }

//--Display all the employees whose salary is less than 5000------------------------------------------------------------
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary()<5000).collect(Collectors.toList());
    }

//--Display all the employees whose salary is between 6000 and 7000-----------------------------------------------------
    public static List<Employee> getAllEmployeesSalaryBetween() {
                                                                                                                 //alt1
//        return getAllEmployees().stream().filter(employee -> employee.getSalary()>6000 && employee.getSalary()<7000 )
//                .collect(Collectors.toList());

          return getAllEmployees().stream()                                                                      //alt2
                .filter(employee -> employee.getSalary() > 6000)
                .filter(employee -> employee.getSalary() < 7000)
                .collect(Collectors.toList());
    }

//--Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)------------------------------
    public static Long getGrantDouglasSalary() throws Exception {
        return getAllEmployees().stream()
                .filter(x->x.getLastName().equals("Grant") && x.getFirstName().equals("Douglas"))
                .findFirst().orElseThrow(()->new Exception("No employee found!")).getSalary();
    }

//--Display the maximum salary an employee gets-------------------------------------------------------------------------
//   public static Long getMaxSalary() throws Exception {   orjinali bu ama bu haliyle sonraki ()'da kullanirsak exception'i
//                                                          handle etmemiz gerekiyor o yuuzden burada throws'u sildi 1:41:18
     public static Long getMaxSalary() {
//---------------------------------------alt1--------------------------------------------------
//        return getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .findFirst().get().getSalary();
//---------------------------------------alt2--------------------------------------------------
//        return getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .limit(1).collect(Collectors.toList()).get(0).getSalary();
//---------------------------------------alt3--------------------------------------------------
//          return getAllEmployees().stream()
//                  .max(Comparator.comparing(Employee::getSalary))   Give a Comparator to max()which will return us
//                  .get().getSalary();                               the max value.Bascally we'll give direction to the
//                                                                    ()what it needs to compare.Here it needs to compare
                                                                    //the salaries.We have to provide this info to the ()
                                                                    //To do that we can use Comparator.comparing() & say
                                                                    //that we'll compare based on employee salaries.&
                                                                    //after the comparison is completed give me the max. one
                                                                    //Since max() returns optional we use get() or orElse()
                                                                    //orElseThrow()..
//---------------------------------------alt4--------------------------------------------------

//          return getAllEmployees().stream()
//                    .map(Employee::getSalary)
//                    .reduce((sal1,sal2)-> (sal1>sal2)? sal1 :sal2)                //Bec.if we don't give an initial value
//                    .get();                                                       //reduce() returns optional()

//yukardakinda ternary'de () var asagidakinde yok

//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .reduce((salary1, salary2) -> salary1 > salary2 ? salary1 : salary2)
//                .get();

//---------------------------------------alt5--------------------------------------------------
//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .reduce(Long::max)
//                .get();
//---------------------------------------alt6--------------------------------------------------
//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .collect(Collectors.maxBy(Comparator.comparing(Long::longValue))).get();

//---------------------------------------alt7--------------------------------------------------
      return getAllEmployees().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Employee::getSalary))).get().getSalary();

//---------------------------------------alt8--------------------------------------------------
//        return getAllEmployees().stream()
//                .map(Employee::getSalary)
//                .mapToLong(i -> i)
//                .max().getAsLong();
//---------------------------------------alt9--------------------------------------------------
//        return getAllEmployees().stream()
//                .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                .map(Employee::getSalary)
//                .findFirst()
//                .get();

    }

//--Display the employee(s) who gets the maximum salary-----------------------------------------------------------------
    public static List<Employee> getMaxSalaryEmployee() {
        return getAllEmployees().stream()
                .filter(employee -> employee.getSalary().equals(getMaxSalary()))
                .collect(Collectors.toList());


//        Note: We can create stream from optional class
//        For this project, the one below works,
//        but it is a chance for it to not work if there is multiple object that is matching with the condition

//UO:The below one bir work around gibi oldugu icin CT cok sevmiyormus, yukardakini kullan
//Burada max() returning Optional. Optional class has a stream() inside of it like we did in the List. So we can get the
//max salary & put it into Optional & then put it into stream again. And from the stream what we can do? -We can convert
//streams to Lists.So we got the max salaried Employee then we put it back into stream just bec.we want to put it in a List
//max method only returns 1. If there are more than 1 employee with the same maximum salary this code won't show all of them.
//The max method only returns a single Optional result, which represents one of the employees with the highest salary.
//It does not handle multiple employees with the same maximum salary. So, if there are multiple employees with the same
//highest salary, your current code will return only one of them (the first one it encounters based on the stream order)
//and ignore any others.

//        return getAllEmployees().stream()
//                .max(Comparator.comparing(Employee::getSalary))
//                .stream().collect(Collectors.toList());
    }

//--Display the max salary employee's job-------------------------------------------------------------------------------
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        return getMaxSalaryEmployee().stream().findFirst().map(Employee::getJob).get();

    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        //TODO Implement the method
        return 1L;
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        //TODO Implement the method
        return 1L;
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        //TODO Implement the method
        return 1L;
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        //TODO Implement the method
        return 1L;
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        //TODO Implement the method
        return 1d;
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        //TODO Implement the method
        return new HashMap<>();
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        //TODO Implement the method
        return 1L;
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        //TODO Implement the method
        return new Employee();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        //TODO Implement the method
        return new Employee();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        //TODO Implement the method
        return 1L;
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength() throws Exception {
        //TODO Implement the method
        return 1;
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        //TODO Implement the method
        return new ArrayList<>();
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        //TODO Implement the method
        return new ArrayList<>();
    }

}
