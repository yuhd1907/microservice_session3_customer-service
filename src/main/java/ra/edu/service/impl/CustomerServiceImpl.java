package ra.edu.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.edu.dto.CustomerRequestDTO;
import ra.edu.dto.CustomerResponseDTO;
import ra.edu.dto.LoginRequestDTO;
import ra.edu.entity.Customer;
import ra.edu.exception.ResourceNotFoundException;
import ra.edu.repository.CustomerRepository;
import ra.edu.service.CustomerService;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO register(CustomerRequestDTO requestDTO) {
        Customer customer = Customer.builder()
                .fullName(requestDTO.getFullName())
                .email(requestDTO.getEmail())
                .password(BCrypt.hashpw(requestDTO.getPassword(), BCrypt.gensalt()))
                .build();
        
        Customer savedCustomer = customerRepository.save(customer);
        return mapToResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToResponseDTO(customer);
    }

    @Override
    public Object login(LoginRequestDTO loginDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(loginDTO.getEmail());
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            if (BCrypt.checkpw(loginDTO.getPassword(), customer.getPassword())) {
                return mapToResponseDTO(customer);
            }
        }
        return "email or password incorrect";
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setId(customer.getId());
        responseDTO.setFullName(customer.getFullName());
        responseDTO.setEmail(customer.getEmail());
        return responseDTO;
    }
}
