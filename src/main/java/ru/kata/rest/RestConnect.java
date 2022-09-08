package ru.kata.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kata.rest.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestConnect {

    //Класс для подключения к api с помощью RestTemplate
    private static final String URL = "http://94.198.50.185:7081/api/users/";
    private static User user = new User();
    private static RestTemplate restTemplate = new RestTemplate();

    public static void getCode() {
        String cookies = getAllUsers();
        String kataKey = addUser(cookies) + updateUser(cookies) + deleteUser(cookies, 3L);
        System.out.println(kataKey);
    }

    public static String getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        restTemplate.exchange(URL, HttpMethod.GET, httpEntity, String.class);

        return restTemplate.exchange(
                        URL, HttpMethod.GET, httpEntity, String.class)
                .getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    public static String addUser(String cookie) {
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 23);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return restTemplate.exchange(
                URL, HttpMethod.POST, httpEntity, String.class).getBody();
    }

    public static String updateUser(String cookie) {

        user.setName("Thomas");
        user.setLastName("Shelby");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return restTemplate.exchange(
                URL, HttpMethod.PUT, httpEntity, String.class).getBody();
    }

    public static String deleteUser(String cookie, Long id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        return restTemplate.exchange(
                URL + id.toString(), HttpMethod.DELETE, httpEntity, String.class).getBody();
    }

}
