package EmployeeController;

import EmployeeDAO.*;
import Employee.*;

import java.util.Date;
import java.util.List;

public class EmployeeController {
    private final EmployeeDAO employeeDAO;
    
    public EmployeeController() {
        this.employeeDAO = new EmployeeDAO();
    }
    
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }
    
    public Employee getEmployeeById(int userId) {
        return employeeDAO.getEmployeeById(userId);
    }
    
    public boolean addEmployee(Employee employee, String password) {
        // Mã hóa mật khẩu trước khi lưu vào database
        String hashedPassword = password;
        
        // Thêm một số xác thực dữ liệu
        if (employee.getUsername() == null || employee.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên đăng nhập không được để trống");
        }
        
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty() ||
            employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên không được để trống");
        }
        
        if (employee.getHireDate() == null) {
            employee.setHireDate(new Date()); // Mặc định là ngày hiện tại
        }
        
        if (employee.getStatus() == null || employee.getStatus().trim().isEmpty()) {
            employee.setStatus("ACTIVE"); // Mặc định là đang hoạt động
        }
        
        // Thêm nhân viên vào database
        return employeeDAO.addEmployee(employee, hashedPassword);
    }
    
    public boolean updateEmployee(Employee employee) {
        // Thêm một số xác thực dữ liệu
        if (employee.getUserId() <= 0) {
            throw new IllegalArgumentException("ID nhân viên không hợp lệ");
        }
        
        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }
        
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty() ||
            employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên không được để trống");
        }
        
        // Cập nhật thông tin nhân viên
        return employeeDAO.updateEmployee(employee);
    }
    
    public boolean deleteEmployee(int userId) {
        // Kiểm tra xem ID có hợp lệ không
        if (userId <= 0) {
            throw new IllegalArgumentException("ID nhân viên không hợp lệ");
        }
        
        // Gọi phương thức xóa từ DAO
        return employeeDAO.deleteEmployee(userId);
    }
}