package com.example.chatspring.controller;


import com.example.chatspring.model.*;
import com.example.chatspring.repository.InviteRepository;
import com.example.chatspring.repository.MassageRepository;
import com.example.chatspring.repository.UserRepository;
import com.example.chatspring.security.CurrentUser;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private MassageRepository massageRepository;


    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user, @RequestParam("imgPath") MultipartFile multipartfile) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String filName = System.currentTimeMillis() + " " + multipartfile.getOriginalFilename();
            File file = new File(imageUploadDir + filName);
            try {
                multipartfile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setImagePath(filName);
            userRepository.save(user);
            return "redirect:/";
        }
        return "redirect:/register";
    }


    @GetMapping("/profile")
    public String profile(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        User userById = userRepository.findUserById(currentUser.getUser().getId());
        List<User> friends = userById.getFriends();
        modelMap.addAttribute("user", currentUser.getUser());
        modelMap.addAttribute("friends", friends);
        modelMap.addAttribute("invites", inviteRepository.findAllByTo(currentUser.getUser()));
        modelMap.addAttribute("massages", massageRepository.findAllByTo(currentUser.getUser()));
        modelMap.addAttribute("massages2", massageRepository.findAllByFrom(currentUser.getUser()));
        return "profile";
    }


    @PostMapping("/sendmassage")
    public String sendMassage(@ModelAttribute Massage massage, @RequestParam(value = "toid") int toId, @AuthenticationPrincipal CurrentUser currentUser) {

        User byId = userRepository.findUserById(toId);
        massage.setTo(byId);
        massage.setFrom(currentUser.getUser());
        massage.setDate(new Date());
        massageRepository.save(massage);
        return "redirect:/profile";

    }


    @PostMapping("/sendInvite")
    public String sendInvite(@ModelAttribute Invite invite, @RequestParam(value = "toid") int toId, @AuthenticationPrincipal CurrentUser currentUser) {
        User byId = userRepository.findUserById(toId);
        invite.setTo(byId);
        invite.setFrom(currentUser.getUser());
        invite.setSendDate(new Date());
        invite.setText(currentUser.getUser().getName() + " " + currentUser.getUser().getSurname() + " wants to add you to his friends list");
        invite.setStatus(InviteStatus.PENDING);
        inviteRepository.save(invite);
        return "redirect:/profile";
    }


    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("imagePath") String imageUrl) {
        InputStream in = null;
        try {
            in = new FileInputStream(imageUploadDir + imageUrl);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/acceptInvite")
    public String acceptInvite(@RequestParam(value = "id") int Id, @RequestParam(value = "answer") String answer,
                               @AuthenticationPrincipal CurrentUser currentUser) {


        if (answer.equals("yes")) {
            inviteRepository.changeInviteStatus(InviteStatus.ACCEPT.name(), Id);
            Invite inviteById = inviteRepository.findInviteById(Id);
            User userById = userRepository.findUserById(inviteById.getFrom().getId());
            User userById1 = userRepository.findUserById(currentUser.getUser().getId());
            List<User> friends = new LinkedList<>();
            List<User> friends2 = new LinkedList<>();
            friends.add(userById);
            friends2.add(userById1);
            userById1.setFriendOf(friends);
            userRepository.save(userById1);
            userById.setFriendOf(friends2);
            userRepository.save(userById);
            inviteRepository.deleteById(Id);
        }else{
            inviteRepository.changeInviteStatus(InviteStatus.DENIED.name(),Id);
            inviteRepository.deleteById(Id);
        }


        return "redirect:/profile";
    }

    @GetMapping("/search")
    public String search(ModelMap modelMap, @RequestParam String keyword, @AuthenticationPrincipal CurrentUser currentUser) {
        List<User> users = userRepository.findByName(keyword);
        modelMap.addAttribute("users", users);
        User userById = userRepository.findUserById(currentUser.getUser().getId());
        modelMap.addAttribute("user", currentUser.getUser());
        modelMap.addAttribute("friends", userById.getFriends());
        modelMap.addAttribute("invites", inviteRepository.findAllByTo(currentUser.getUser()));
        modelMap.addAttribute("massages", massageRepository.findAllByTo(currentUser.getUser()));
        modelMap.addAttribute("massages2", massageRepository.findAllByFrom(currentUser.getUser()));
        return "profile";
    }

    @GetMapping("/delete")
    public String deleteUserFriends(@AuthenticationPrincipal CurrentUser currentUser, @RequestParam("id") int id) {
        User userById = userRepository.findUserById(id);
        User userById2 = currentUser.getUser();
        userById.setFriendOf(null);
        userById2.setFriendOf(null);
        userRepository.save(userById);
        userRepository.save(userById2);
        return "redirect:/profile";
    }

}
