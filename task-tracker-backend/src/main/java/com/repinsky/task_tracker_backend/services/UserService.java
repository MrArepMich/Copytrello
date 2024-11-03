package com.repinsky.task_tracker_backend.services;

import com.repinsky.task_tracker_backend.dto.RegisterUserDto;
import com.repinsky.task_tracker_backend.exceptions.InputDataException;
import com.repinsky.task_tracker_backend.models.User;
import com.repinsky.task_tracker_backend.producer.RegistrationProducer;
import com.repinsky.task_tracker_backend.repositories.UserRepository;
import com.repinsky.task_tracker_backend.utils.InputValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static com.repinsky.task_tracker_backend.constants.InfoMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final InputValidationUtil validationService = new InputValidationUtil();
    private final RegistrationProducer registrationProducer;

    public void createNewUser(RegisterUserDto registerUserDto) throws InputDataException {
        if (registerUserDto.getPassword() == null) {
            throw new InputDataException(PASSWORD_CANNOT_BE_EMPTY.getValue());
        } else if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            throw new InputDataException(PASSWORD_MISMATCH.getValue());
        } else {
            User user = new User();
            String encryptedPassword = passwordEncoder.encode(registerUserDto.getPassword());
            String validationMessage = validateAndSaveUserFields(registerUserDto, encryptedPassword, user);
            if (validationMessage != null) {
                throw new InputDataException(validationMessage);
            }

            userRepository.save(user);
            registrationProducer.sendSuccessRegistration(registerUserDto.getEmail());
        }
    }

    private String validateAndSaveUserFields(RegisterUserDto registerUserDto, String encryptedPassword, User user) {
        user.setRoles(Collections.singleton(roleService.getManagerRole()));

        String email = registerUserDto.getEmail();
        String validationMessage = "";

        validationMessage = validationService.acceptableEmail(email);
        if (validationMessage.equals("")) {
            Optional<User> userByEmail = findByEmail(email);
            if (userByEmail.isPresent()) {
                return EMAIL_ALREADY_EXISTS.getValue();
            }
            user.setEmail(email);
        } else {
            return validationMessage;
        }

        validationMessage = validationService.acceptablePassword(registerUserDto.getPassword());
        // we take the password from the DTO, because we check the validity of the unencrypted password and save it
        // to the database
        if (validationMessage.equals("")) {
            user.setPassword(encryptedPassword);
        } else {
            return validationMessage;
        }

        return null;    //  successful validation
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        return null;
    }
}
