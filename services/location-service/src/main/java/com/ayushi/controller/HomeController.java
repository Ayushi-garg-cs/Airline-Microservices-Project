package com.ayushi.controller;

import com.ayushi.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Success");
        return apiResponse;
    }
}
