package com.example;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jin on 17. 3. 13.
 */

@EnableAutoConfiguration
@ComponentScan
public class App implements CommandLineRunner{

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    CustomerRepository customerRepository;

    public void run(java.lang.String... strings) throws Exception{
//        String sql = "SELECT :a + :b";
//        SqlParameterSource param = new MapSqlParameterSource()
//                .addValue("a", 100)
//                .addValue("b", 200);
//        Integer result = jdbcTemplate.queryForObject(sql, param, Integer.class);
//
//        System.out.println("result : " + result);

//        String sql = "SELECT id, first_name, last_name FROM customers WHERE id = :id";
//
//        SqlParameterSource param = new MapSqlParameterSource()
//                .addValue("id", 1);

//        Customer result = jdbcTemplate.queryForObject(sql, param, new RowMapper<Customer>() {
//            @Override
//            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new Customer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
//            }
//        });

//        Customer result = jdbcTemplate.queryForObject(sql, param, (rs, rowNum) -> new Customer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name")));
//
//        System.out.println("result = " + result);

        // 데이터 추가
        Customer created = customerRepository.save(new Customer(null, "Hidetoshi", "Dekisugi"));
        System.out.println(created + " is created!");

        // 페이징 처리
        Pageable pageable = new PageRequest(0, 3);
//        Page<Customer> page = customerRepository.findAll(pageable);
        Page<Customer> page = customerRepository.findAllOrderByName(pageable);
        System.out.println("한 페이지당 데이터 수=" + page.getSize());
        System.out.println("현재 페이지="+page.getNumber());
        System.out.println("전체 페이지 수="+page.getTotalPages());
        System.out.println("전체 데이터 수="+page.getTotalElements());
        page.getContent().forEach(System.out::println);

        // 데이터 표시
//        customerRepository.findAll().forEach(System.out::println);
//        customerRepository.findAllOrderByName().forEach(System.out::println);

    }

    public static void main(String [] args){
        SpringApplication.run(App.class, args);
    }

}
