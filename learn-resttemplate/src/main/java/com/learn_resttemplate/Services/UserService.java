package com.learn_resttemplate.Services;

import com.learn_resttemplate.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private static final String url="https://jsonplaceholder.typicode.com/users";

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUser()
    {
        User[] allUser = restTemplate.getForObject(url, User[].class);
        assert allUser != null;
        return Arrays.asList(allUser);
    }

    public User getUserById(int id)
    {
        String url="https://jsonplaceholder.typicode.com/users/"+id;

        try
        {
          return   restTemplate.getForObject(url, User.class);
        }
        catch (RestClientException ex)
        {
            throw new RuntimeException("User Not Found : "+id);
        }

    }
}
