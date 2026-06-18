package ra.edu.service;

import ra.edu.dto.CustomerRequestDTO;
import ra.edu.dto.CustomerResponseDTO;
import ra.edu.dto.LoginRequestDTO;

public interface CustomerService {
    CustomerResponseDTO register(CustomerRequestDTO requestDTO);
    CustomerResponseDTO getById(Long id);
    Object login(LoginRequestDTO loginDTO);
}
