package com.employeeManagement.Services;

import com.employeeManagement.Dto.LoginResponse;
import com.employeeManagement.Dto.ManagerRequest;
import com.employeeManagement.entity.Department;
import com.employeeManagement.entity.Manager;
import com.employeeManagement.repository.DepartmentRepository;
import com.employeeManagement.repository.ManagerRepository;
import com.employeeManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Manager createManager(ManagerRequest request) {
        Department department = departmentRepository.findById(request.departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Manager manager = new Manager();
        manager.setFirstName(request.firstName);
        manager.setLastName(request.lastName);
        manager.setEmailId(request.emailId);
        manager.setPassword(passwordEncoder.encode(request.password));
        manager.setGender(request.gender);
        manager.setAge(request.age);
        manager.setContactNo(request.contactNo);
        manager.setExperience(request.experience);
        manager.setStreet(request.street);
        manager.setPincode(request.pincode);
        manager.setDepartment(department);

        return managerRepository.save(manager);
    }

    public LoginResponse login(String emailId, String password) {
        Optional<Manager> managerOpt = managerRepository.findByEmailId(emailId);

        if (managerOpt.isEmpty()) {
            return new LoginResponse(null, "Manager with this email not found");
        }

        Manager manager = managerOpt.get();

        if (!passwordEncoder.matches(password, manager.getPassword())) {
            return new LoginResponse(null, "Invalid email or password");
        }

        String token = jwtUtil.generateToken(emailId, "MANAGER");

        return new LoginResponse(token, "Login Successful");
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

//    public Manager updateManager(Long id, ManagerRequest request) {
//        Manager manager = managerRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Manager not found"));
//
//        Department dept = departmentRepository.findById(request.getDepartmentId())
//                .orElseThrow(() -> new RuntimeException("Department not found"));
//
//        manager.setFirstName(request.getFirstName());
//        manager.setLastName(request.getLastName());
//        manager.setEmailId(request.getEmailId());
//        manager.setPassword(passwordEncoder.encode(request.getPassword()));
//        manager.setGender(request.getGender());
//        manager.setAge(request.getAge());
//        manager.setContactNo(request.getContactNo());
//        manager.setExperience(request.getExperience());
//        manager.setStreet(request.getStreet());
//        manager.setPincode(request.getPincode());
//        manager.setDepartment(dept);
//
//        return managerRepository.save(manager);
//    }


    public Manager updateManager(Long id, ManagerRequest request) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Department dept = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        manager.setFirstName(request.getFirstName());
        manager.setLastName(request.getLastName());
        manager.setEmailId(request.getEmailId());
        manager.setGender(request.getGender());
        manager.setAge(request.getAge());
        manager.setContactNo(request.getContactNo());
        manager.setExperience(request.getExperience());
        manager.setStreet(request.getStreet());
        manager.setPincode(request.getPincode());
        manager.setDepartment(dept);

        // ✅ Password encoding safety check
        if (!request.getPassword().startsWith("$2a$")) {
            manager.setPassword(passwordEncoder.encode(request.getPassword()));
        } else {
            manager.setPassword(request.getPassword());
        }


        return managerRepository.save(manager);
    }

    public String deleteManager(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        managerRepository.delete(manager);
        return "Manager deleted successfully";
    }

    public Manager getManagerByEmail(String email) {
        return managerRepository.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
    }

}

