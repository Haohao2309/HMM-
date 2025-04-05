/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HRMApplication;

/**
 *
 * @author Admin
 */
import EmployeeController.*;
import Employee.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HRMApplication {
    private static final EmployeeController employeeController = new EmployeeController();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("===== HỆ THỐNG QUẢN LÝ NHÂN SỰ =====");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Nhập lựa chọn: ");
            
            switch (choice) {
                case 1:
                    displayAllEmployees();
                    break;
                case 2:
                    findEmployeeById();
                    break;
                case 3:
                    addNewEmployee();
                    break;
                case 4:
                    updateExistingEmployee();
                    break;
                case 5:
                    deleteExistingEmployee();
                    break;
                case 0:
                    running = false;
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Xem danh sách nhân viên");
        System.out.println("2. Tìm nhân viên theo ID");
        System.out.println("3. Thêm nhân viên mới");
        System.out.println("4. Cập nhật thông tin nhân viên");
        System.out.println("5. Xóa nhân viên");
        System.out.println("0. Thoát");
        System.out.println("================");
    }

    private static void displayAllEmployees() {
        System.out.println("\n===== DANH SÁCH NHÂN VIÊN =====");
        List<Employee> employees = employeeController.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("Không có nhân viên nào trong hệ thống.");
            return;
        }
        
        System.out.printf("%-5s %-20s %-20s %-15s %-15s %-10s\n", 
                        "ID", "Họ tên", "Email", "Phòng ban", "Vị trí", "Trạng thái");
        System.out.println("------------------------------------------------------------------------------");
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-20s %-20s %-15s %-15s %-10s\n", 
                            emp.getUserId(), 
                            emp.getFirstName() + " " + emp.getLastName(), 
                            emp.getEmail(), 
                            emp.getDepartmentName(), 
                            emp.getPositionName(), 
                            emp.getStatus());
        }
        
        System.out.println("------------------------------------------------------------------------------");
    }

    private static void findEmployeeById() {
        int id = getIntInput("Nhập ID nhân viên: ");
        Employee employee = employeeController.getEmployeeById(id);
        
        if (employee == null) {
            System.out.println("Không tìm thấy nhân viên với ID: " + id);
            return;
        }
        
        System.out.println("\n===== THÔNG TIN NHÂN VIÊN =====");
        System.out.println("ID: " + employee.getUserId());
        System.out.println("Tên đăng nhập: " + employee.getUsername());
        System.out.println("Họ và tên: " + employee.getFirstName() + " " + employee.getLastName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Ngày sinh: " + (employee.getDateOfBirth() != null ? dateFormat.format(employee.getDateOfBirth()) : "N/A"));
        System.out.println("Giới tính: " + employee.getGender());
        System.out.println("Số điện thoại: " + employee.getPhoneNumber());
        System.out.println("Địa chỉ: " + employee.getAddress());
        System.out.println("Phòng ban: " + employee.getDepartmentName());
        System.out.println("Vị trí: " + employee.getPositionName());
        System.out.println("Quản lý: " + employee.getManagerName());
        System.out.println("Ngày vào làm: " + dateFormat.format(employee.getHireDate()));
        System.out.println("Lương: " + employee.getSalary());
        System.out.println("Trạng thái: " + employee.getStatus());
    }

    private static void addNewEmployee() {
        System.out.println("\n===== THÊM NHÂN VIÊN MỚI =====");
        
        Employee employee = new Employee();
        
        employee.setUsername(getStringInput("Tên đăng nhập: "));
        String password = getStringInput("Mật khẩu: ");
        employee.setEmail(getStringInput("Email: "));
        employee.setFirstName(getStringInput("Họ: "));
        employee.setLastName(getStringInput("Tên: "));
        
        try {
            String dobStr = getStringInput("Ngày sinh (dd/MM/yyyy) hoặc bỏ qua: ");
            if (!dobStr.isEmpty()) {
                employee.setDateOfBirth(dateFormat.parse(dobStr));
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Đã bỏ qua ngày sinh.");
        }
        
        employee.setGender(getStringInput("Giới tính (Nam/Nữ/Khác): "));
        employee.setPhoneNumber(getStringInput("Số điện thoại: "));
        employee.setAddress(getStringInput("Địa chỉ: "));
        
        employee.setDepartmentId(getIntInput("ID phòng ban: "));
        employee.setPositionId(getIntInput("ID vị trí: "));
        
        String managerIdStr = getStringInput("ID quản lý (hoặc bỏ qua): ");
        if (!managerIdStr.isEmpty()) {
            employee.setManagerId(Integer.parseInt(managerIdStr));
        }
        
        try {
            String hireDateStr = getStringInput("Ngày vào làm (dd/MM/yyyy) hoặc bỏ qua để lấy ngày hiện tại: ");
            if (!hireDateStr.isEmpty()) {
                employee.setHireDate(dateFormat.parse(hireDateStr));
            } else {
                employee.setHireDate(new Date());
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Sử dụng ngày hiện tại.");
            employee.setHireDate(new Date());
        }
        
        employee.setSalary(getDoubleInput("Lương: "));
        employee.setStatus("ACTIVE");
        
        try {
            boolean success = employeeController.addEmployee(employee, password);
            if (success) {
                System.out.println("Thêm nhân viên thành công! ID: " + employee.getUserId());
            } else {
                System.out.println("Thêm nhân viên thất bại!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private static void updateExistingEmployee() {
        System.out.println("\n===== CẬP NHẬT THÔNG TIN NHÂN VIÊN =====");
        
        int id = getIntInput("Nhập ID nhân viên cần cập nhật: ");
        Employee employee = employeeController.getEmployeeById(id);
        
        if (employee == null) {
            System.out.println("Không tìm thấy nhân viên với ID: " + id);
            return;
        }
        
        // Hiển thị thông tin hiện tại
        System.out.println("Thông tin hiện tại:");
        System.out.println("Họ và tên: " + employee.getFirstName() + " " + employee.getLastName());
        System.out.println("Email: " + employee.getEmail());
        
        // Cập nhật thông tin
        String email = getStringInput("Email mới (Enter để giữ nguyên): ");
        if (!email.isEmpty()) {
            employee.setEmail(email);
        }
        
        String firstName = getStringInput("Họ mới (Enter để giữ nguyên): ");
        if (!firstName.isEmpty()) {
            employee.setFirstName(firstName);
        }
        
        String lastName = getStringInput("Tên mới (Enter để giữ nguyên): ");
        if (!lastName.isEmpty()) {
            employee.setLastName(lastName);
        }
        
        try {
            String dobStr = getStringInput("Ngày sinh mới (dd/MM/yyyy) (Enter để giữ nguyên): ");
            if (!dobStr.isEmpty()) {
                employee.setDateOfBirth(dateFormat.parse(dobStr));
            }
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Giữ nguyên ngày sinh cũ.");
        }
        
        String gender = getStringInput("Giới tính mới (Nam/Nữ/Khác) (Enter để giữ nguyên): ");
        if (!gender.isEmpty()) {
            employee.setGender(gender);
        }
        
        String phone = getStringInput("Số điện thoại mới (Enter để giữ nguyên): ");
        if (!phone.isEmpty()) {
            employee.setPhoneNumber(phone);
        }
        
        String address = getStringInput("Địa chỉ mới (Enter để giữ nguyên): ");
        if (!address.isEmpty()) {
            employee.setAddress(address);
        }
        
        String deptIdStr = getStringInput("ID phòng ban mới (Enter để giữ nguyên): ");
        if (!deptIdStr.isEmpty()) {
            employee.setDepartmentId(Integer.parseInt(deptIdStr));
        }
        
        String posIdStr = getStringInput("ID vị trí mới (Enter để giữ nguyên): ");
        if (!posIdStr.isEmpty()) {
            employee.setPositionId(Integer.parseInt(posIdStr));
        }
        
        String managerIdStr = getStringInput("ID quản lý mới (Enter để giữ nguyên, 0 để xóa): ");
        if (!managerIdStr.isEmpty()) {
            int managerId = Integer.parseInt(managerIdStr);
            if (managerId == 0) {
                employee.setManagerId(null);
            } else {
                employee.setManagerId(managerId);
            }
        }
        
        String salaryStr = getStringInput("Lương mới (Enter để giữ nguyên): ");
        if (!salaryStr.isEmpty()) {
            employee.setSalary(Double.parseDouble(salaryStr));
        }
        
        String status = getStringInput("Trạng thái mới (ACTIVE/INACTIVE/ON_LEAVE) (Enter để giữ nguyên): ");
        if (!status.isEmpty()) {
            employee.setStatus(status);
        }
        
        try {
            boolean success = employeeController.updateEmployee(employee);
            if (success) {
                System.out.println("Cập nhật thông tin nhân viên thành công!");
            } else {
                System.out.println("Cập nhật thông tin nhân viên thất bại!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private static void deleteExistingEmployee() {
        System.out.println("\n===== XÓA NHÂN VIÊN =====");
        
        int id = getIntInput("Nhập ID nhân viên cần xóa: ");
        
        try {
            boolean success = employeeController.deleteEmployee(id);
            if (success) {
                System.out.println("Xóa nhân viên thành công!");
            } else {
                System.out.println("Xóa nhân viên thất bại!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số thực hợp lệ.");
            }
        }
    }
}

