package kafka.system.PeopleService.service;


import kafka.system.PeopleService.persistence.model.Admin;
import kafka.system.PeopleService.persistence.repository.AdminRepository;
import kafka.system.core.dto.AuthService.AdminCredentialDto;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void register(AdminCredentialDto dto){
        Admin admin = new Admin(
                dto.getId(),
                dto.getTimeStamp()
        );

        adminRepository.save(admin);
    }
}
