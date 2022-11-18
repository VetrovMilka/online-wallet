package com.endava.wallet.controller;

import com.endava.wallet.entity.Authority;
import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.User;
import com.endava.wallet.entity.dto.ProfileDto;
import com.endava.wallet.entity.dto.ProfileDtoConverter;
import com.endava.wallet.entity.dto.UserDto;
import com.endava.wallet.entity.dto.UserDtoConverter;
import com.endava.wallet.service.ProfileService;
import com.endava.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final UserDtoConverter userDtoConverter;
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileDtoConverter profileDtoConverter;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(UserDto userDto, ProfileDto profileDto, Model model) {
        if (userService.existsUserByUsername(userDto.getUsername()) ||
                profileService.existsProfileByEmail(profileDto.getEmail())) {
            model.addAttribute("error", "User already exists!");
            return "register";
        }
        User user = userDtoConverter.fromDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(Collections.singleton(Authority.USER));
        user.setEnabled(true);
        userService.add(user);

        Profile profile = profileDtoConverter.fromDto(profileDto, user);
        profileService.save(profile);

        return "redirect:/login";
    }
}
