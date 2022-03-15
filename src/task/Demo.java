package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {

        Scanner sc;
        try {
            sc = new Scanner(new File("employeesData.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        ArrayList<Employee> employees = fillArray(sc);
        findPairEmployeesThatHaveWorkedTheLongest(employees);

    }

    //the idea of this function is to check if overlapping intervals exist and print the ids
    //of employees who have worked together for the longest period of time
    private static void findPairEmployeesThatHaveWorkedTheLongest(ArrayList<Employee> employees) {
        long maxWorkingDays = 0L;
        int employee1Id = 0;
        int employee2Id = 0;
        for (int i = 0; i < employees.size(); i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Employee e1 = employees.get(i);
                Employee e2 = employees.get(j);
                if (employees.get(i).getDateFrom().isAfter(employees.get(j).getDateFrom())) {
                    e1 = employees.get(j);
                    e2 = employees.get(i);
                }
                if (e1.getProjectId() == e2.getProjectId()) { //check if the project is same
                    //check whether dateFrom of the second employee is before the dateTo of first - if so -> proceed, else no -> no overlapping
                    if (e2.getDateFrom().isBefore(e1.getDateTo())) {
                        //case 2 of picture idea in git
                        if (e2.getDateTo().isBefore(e1.getDateTo())) {
                            long diff = ChronoUnit.DAYS.between(e2.getDateTo(), e2.getDateFrom());
                            if (diff > maxWorkingDays) {
                                maxWorkingDays = diff;
                                employee1Id = e1.getEmployeeId();
                                employee2Id = e2.getEmployeeId();
                            }
                        } else { // case 1 of idea picture in git
                            long diff = ChronoUnit.DAYS.between(e2.getDateFrom(), e1.getDateTo());
                            if (diff > maxWorkingDays) {
                                maxWorkingDays = diff;
                                employee1Id = e1.getEmployeeId();
                                employee2Id = e2.getEmployeeId();
                            }
                        }
                    }
                }
            }
        }

        //   System.out.println(maxWorkingDays);
        System.out.println(employee1Id);
        System.out.println(employee2Id);
    }

    private static ArrayList<Employee> fillArray(Scanner sc) {
        ArrayList<Employee> employees = new ArrayList<>();
        while (sc.hasNext()) {
            String[] words = sc.nextLine().split(","); // take each row and split it by ,
            Employee e = new Employee();
            e.setEmployeeId(Integer.parseInt(words[0]));
            e.setProjectId((Integer.parseInt(words[1])));
            e.setDateFrom(LocalDate.parse(words[2]));
            if (words[3].equals("null")) {
                e.setDateTo(LocalDate.now());
            } else {
                e.setDateTo(LocalDate.parse(words[3]));
            }
            employees.add(e);

        }
        return employees;
    }
}
