package com.teachme.teachme.service;

import com.teachme.teachme.entity.Authority;
import com.teachme.teachme.dto.AuthorityDTO;
import com.teachme.teachme.entity.DAOUser;
import com.teachme.teachme.exceptionhandler.CustomException;
import com.teachme.teachme.repository.AuthorityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthorityService {

    private AuthorityRepository authorityRepository;
    private UserService userService;

    public AuthorityService(AuthorityRepository authorityRepository,UserService userService)
    {
        this.authorityRepository = authorityRepository;
        this.userService = userService;
    }

    public Authority createAuthorityService(AuthorityDTO authorityDTO)
    {
        if (authorityRepository.existsByName(authorityDTO.getName()) == true)
        {
            throw new CustomException(
                    "Duplicate Entry For Role",
                    HttpStatus.BAD_REQUEST, "/authority");
        }
        Authority authority = new Authority();

        authority.setName(authorityDTO.getName());
        //System.out.println("Here");
        //System.out.println(authority.toString());
        authorityRepository.save(authority);
        return authority;
    }

    public DAOUser setAuthorityService(AuthorityDTO authorityDTO) {
        Optional<Authority> authority = authorityRepository.findByName(authorityDTO.getName());
        if (authority.isEmpty())
        {
            throw new CustomException(
                    "No Authority By This Name",
                    HttpStatus.BAD_REQUEST, "/assign_authority");
        }

        return userService.setAuthority(authority.get());
    }

    public ResponseEntity deleteAuthorityService(AuthorityDTO authorityDTO) {
        if (authorityRepository.existsByName(authorityDTO.getName()) == false)
        {
            throw new CustomException(
                    "No Authority By This Name",
                    HttpStatus.NOT_FOUND, "/authority");
        }
        //first delete its associations from user_authority table

        //delete this authority from table
        authorityRepository.deleteByName(authorityDTO.getName());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message","Authority deleted");
        body.put("status",200);
        body.put("path","/authority");
        return new ResponseEntity(body,HttpStatus.OK);
    }

    public DAOUser removeAuthorityService(AuthorityDTO authorityDTO) {
        Optional<Authority> authority = authorityRepository.findByName(authorityDTO.getName());
        if (authority.isEmpty())
        {
            throw new CustomException(
                    "No Authority By This Name",
                    HttpStatus.BAD_REQUEST, "/remove_authority");
        }
        return userService.removeAuthority(authority.get());
    }
}