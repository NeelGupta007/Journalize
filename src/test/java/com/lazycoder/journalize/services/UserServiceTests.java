package com.lazycoder.journalize.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Disabled
    @Test
    @ValueSource(strings = {
            "neel",
            "ok"
    })

    @BeforeAll
    public void function() {
        // statements to perform before running all the tests of this class
        return;
    }

//    @BeforeEach
//    @AfterAll
//    @AfterEach
//    @EnumSource()
//    @ArgumentsSource()
    public void ValidateUser(String name) {
        assertEquals(2,1+1);
        assertNotNull(userService.findByUserName(name));
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,5",
            "10,12,22",
            "3,3,9"
    })
    public void test(int a,int b,int expected) {
        assertEquals(expected,a+b);
    }
}
