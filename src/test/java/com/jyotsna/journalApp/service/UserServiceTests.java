package com.jyotsna.journalApp.service;

import com.jyotsna.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Disabled
    @Test
    public void testAdd(){

        assertNotNull(userRepository.findByUsername("Jyo"));
    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "5,5,10"
    })

    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
