package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int DATE = 0;
    private static final int NAME = 1;
    private static final int HOURS = 2;
    private static final int WAGE = 3;
    private static final String HYPHEN = " - ";

    public String getSalaryInfo(String[] names, String[] data,
                                String dateFrom, String dateTo) {
        LocalDate fromDate = LocalDate.parse(dateFrom, FORMATTER);
        LocalDate toDate = LocalDate.parse(dateTo, FORMATTER);
        int[] salaries = calculateSalary(names, data, fromDate, toDate);

        return createReport(names, salaries, dateFrom, dateTo);
    }

    private String createReport(String[] names, int[] salary, String dateFrom, String dateTo) {
        StringBuilder report = new StringBuilder();
        report.append("Report for period ").append(dateFrom).append(HYPHEN).append(dateTo);

        for (int i = 0; i < names.length; i++) {
            report.append(System.lineSeparator()).append(names[i])
                    .append(HYPHEN).append(salary[i]);
        }
        return report.toString();
    }

    public int[] calculateSalary(String[] names, String[] data,
                                 LocalDate dateFrom, LocalDate dateTo) {
        int[] salaries = new int[names.length];

        for (String datum : data) {
            String[] info = datum.split(" ");
            for (int y = 0; y < names.length; y++) {
                if (names[y].equals(info[NAME])
                        && (LocalDate.parse(info[DATE], FORMATTER).isAfter(dateFrom)
                        || LocalDate.parse(info[DATE], FORMATTER).equals(dateFrom))
                        && (LocalDate.parse(info[DATE], FORMATTER).isBefore(dateTo)
                        || LocalDate.parse(info[DATE], FORMATTER).equals(dateTo))) {
                    salaries[y] += Integer.parseInt(info[HOURS]) * Integer.parseInt(info[WAGE]);
                }
            }
        }
        return salaries;
    }
}
