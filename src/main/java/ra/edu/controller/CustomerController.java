package ra.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.dto.CustomerRequestDTO;
import ra.edu.dto.CustomerResponseDTO;
import ra.edu.dto.LoginRequestDTO;
import ra.edu.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> register(@RequestBody CustomerRequestDTO requestDTO) {
        CustomerResponseDTO response = customerService.register(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        CustomerResponseDTO response = customerService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginDTO) {
        Object response = customerService.login(loginDTO);
        if (response instanceof String) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // User can get unauthorized for failed login
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
