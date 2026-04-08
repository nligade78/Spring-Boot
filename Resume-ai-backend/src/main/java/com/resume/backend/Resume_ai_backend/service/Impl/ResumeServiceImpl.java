package com.resume.backend.Resume_ai_backend.service.Impl;

import com.resume.backend.Resume_ai_backend.service.ResumeService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ResumeServiceImpl implements ResumeService {

    private ChatClient chatClient;

    public ResumeServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String generateResumeResponse(String userResumeDescription) {

        String promptText= "xyz";

       Prompt prompt= new Prompt(userResumeDescription);

       String response= chatClient.prompt().call().content();

       //modify
        return response;
    }

   String loadPromptFromFile(String filename)throws IOException
    {
     Path path=   new ClassPathResource(filename).getFile().toPath();
     return Files.readString(path);
    }
}
