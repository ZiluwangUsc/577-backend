package com.tripwise.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripwise.backend.constants.Constants;
import com.tripwise.backend.controller.UserController;
import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.TokenRefreshDto;
import com.tripwise.backend.dto.request.UserLoginDto;
import com.tripwise.backend.dto.request.UserLogoutDto;
import com.tripwise.backend.dto.request.UserRegisterDto;
import com.tripwise.backend.entity.User;
import com.tripwise.backend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User create(UserRegisterDto userRegisterDto) {
        User user = userRepository.findByEmail(userRegisterDto.getEmail()).orElse(null);
        if (user != null) {
            return null; // User already exists
        }

        User newuser = mapRegisterDtoToUser(userRegisterDto);
        newuser.setToken(this.generateToken());
        return userRepository.save(newuser);
    }

    @Override
    public User login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = generateMD5Hash(userLoginDto.getPassword());
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElse(null);
        if (user == null) {
            return null;
        }
        if (!user.getPasswordHash().equals(password) || !user.getEmail().equals(email)) {
            return null;
        }

        if (user.getToken() == null
                || LocalDateTime.now().isAfter(user.getTokenCreatedAt().plusSeconds(Constants.TOKEN_EXPIRE_TIME))) {
            user.setToken(this.generateToken()); // create a new token
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.findByToken(token).orElse(null);
    }

    @Override
    public User refreshToken(TokenRefreshDto tokenRefreshDto) {
        User user = getUserByToken(tokenRefreshDto.getRefreshToken());
        if (user == null) {
            return null;
        }
        user.setToken(this.generateToken());
        userRepository.save(user);
        return user;
    }

    @Override
    public void logout(UserLogoutDto userLogoutDto) {
        logger.info("Logging out User: " + userLogoutDto.toString());
        Integer userId = userLogoutDto.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);
        if (user == null) {
            return;
        }

        deleteToken(user);
    }

    @Override
    public boolean deleteToken(User user) {
        try {
            user.setToken(null);
            user.setTokenCreatedAt(null);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting token for user: " + user.getUserId(), e);
            return false;
        }
    }

    @Override
    public UserDto getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("Fetching User By ID: " + id + ", Found: " + user.toString());
            return new UserDto(user.getUserId(), user.getEmail(), user.getPasswordHash(),
                    user.getdisplayName(), user.getProfilePhoto(),
                    user.getCreatedAt(), user.getUpdatedAt());
        } else {
            logger.warn("User with ID " + id + " not found.");
            // throw new UserNotFoundException("User with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.warn("looking for email: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("Fetching User By Email: " + email + ", Found: " + user.toString());
            return new UserDto(user.getUserId(), user.getEmail(), user.getPasswordHash(),
                    user.getdisplayName(), user.getProfilePhoto(),
                    user.getCreatedAt(), user.getUpdatedAt());
        } else {
            logger.warn("User with email " + email + " not found.");
            // throw new UserNotFoundException("User with email " + email + " not found");
            return null;
        }
    }

    @Override
    public void update(Integer id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = mapUserDtoToUser(userDto);
            user.setUserId(id);
            userRepository.save(user);
            logger.info("Updating User ID: " + id + " with data: " + user.toString());
        } else {
            logger.warn("User with ID " + id + " not found for update.");
            // throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    @Override
    public void delete(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("Deleted User with ID: " + id);
        } else {
            logger.warn("User with ID " + id + " not found for deletion.");
            // throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    private User mapUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userDto.getPasswordHash());
        user.setdisplayName(userDto.getdisplayName());
        user.setProfilePhoto(userDto.getProfilePhoto());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        return user;
    }

    private User mapRegisterDtoToUser(UserRegisterDto dto) {
        User user = new User();

        user.setEmail(dto.getEmail());
        String hashedPassword = generateMD5Hash(dto.getPassword());
        user.setPasswordHash(hashedPassword);

        user.setdisplayName(dto.getDisplayName());
        user.setUsername(dto.getUsername());

        return user;
    }

    public static String generateMD5Hash(String input) {
        try {
            // Create an instance of the MD5 MessageDigest
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash as bytes
            byte[] hashBytes = md.digest(input.getBytes());

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    // 请求修改密码
    @Override
    @Transactional
    public boolean requestPasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setResetToken(this.generateToken()); // 生成随机 Token
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(30)); // 30 分钟后过期
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // 使用 Token 重置密码
    @Override
    @Transactional
    public boolean resetPassword(String resetToken, String newPassword) {
        Optional<User> userOptional = userRepository.findByResetToken(resetToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 检查 Token 是否过期
            if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
                return false;
            }

            user.setPasswordHash(newPassword); // ✅ 直接存储明文密码（不加密）
            user.setResetToken(null); // 清除 Token
            user.setTokenExpiry(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
