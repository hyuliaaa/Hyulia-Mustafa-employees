package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {

        Scanner sc = null;
        try {
            sc = new Scanner(new File("employeesData.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        ArrayList<Employee> employees = fillArray(sc);
        findPairEmployeesThatHaveWorkedTheLongest(employees);

    }


    private static void findPairEmployeesThatHaveWorkedTheLongest(ArrayList<Employee> employees){
        Long maxWorkingDays = 0L;
        int employee1Id = 0;
        int employee2Id = 0;
        for(int i = 0; i <employees.size();i++){
            for(int j = i+1; j <employees.size();j++){
                Employee e1 = employees.get(i);
                Employee e2 = employees.get(j);
                if(employees.get(i).getDateFrom().isAfter(employees.get(j).getDateFrom())){
                    e1 = employees.get(j);
                    e2 = employees.get(i);
                }
                if(e1.getProjectId()== e2.getProjectId()){ //check if the project is same
                    if(e2.getDateFrom().isBefore(e1.getDateTo())){
                        if(e2.getDateTo().isBefore(e1.getDateTo())) {
                            Long diff = ChronoUnit.DAYS.between(e2.getDateTo(),e2.getDateFrom());
                            if(diff > maxWorkingDays){
                                maxWorkingDays = diff;
                                employee1Id = e1.getEmployeeId();
                                employee2Id = e2.getEmployeeId();
                            }
                            System.out.println(e1);
                            System.out.println(e2);
                            System.out.println(diff);
                        }
                        else {
                            Long diff = ChronoUnit.DAYS.between(e2.getDateFrom(), e1.getDateTo());
                            if(diff > maxWorkingDays){
                                maxWorkingDays = diff;
                                employee1Id = e1.getEmployeeId();
                                employee2Id = e2.getEmployeeId();
                            }
                            System.out.println(e1);
                            System.out.println(e2);
                            System.out.println(diff);
                        }
                    }
                }
            }
        }

        System.out.println(maxWorkingDays);
        System.out.println(employee1Id);
        System.out.println(employee2Id);
    }
    private static ArrayList<Employee> fillArray(Scanner sc){
        ArrayList<Employee> employees = new ArrayList<>();
        while (sc.hasNext())
        {
            String[] words = sc.nextLine().split(","); // take each row and split it by ,
            Employee e = new Employee();
            e.setEmployeeId(Integer.parseInt(words[0]));
            e.setProjectId((Integer.parseInt(words[1])));
            e.setDateFrom(LocalDate.parse(words[2]));
            if(words[3].equals("null")){
                e.setDateTo(LocalDate.now());
            }
            else {
                e.setDateTo(LocalDate.parse(words[3]));
            }
            employees.add(e);

        }
        return employees;
    }
}
