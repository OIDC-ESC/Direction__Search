package com.example.backend.dao;

import static com.example.backend.dao.TrafficLightSqls.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.backend.dto.TrafficLight;

@Repository
public class TrafficLightDao {
    private NamedParameterJdbcOperations jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<TrafficLight> rowMapper = BeanPropertyRowMapper.newInstance(TrafficLight.class);

    public TrafficLightDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("traffic_light");
    }

    public List<TrafficLight> selectAll(){ // 전체 레코드 조회
        return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
    }

    public List<TrafficLight> getTrafficLight(Double latitude, Double longitude) { // 특정 레코드 조회
        Map<String, Double> params = new HashMap<>();
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        return jdbc.query(SELECT_BY_LOCATION, params, rowMapper);
    }

    public int insertTrafficInfo(TrafficLight trafficLight) { // 레코드 임의로 삽입 (일치하는 신호등 정보가 없어서..)
        int insertCount = 0;
        SqlParameterSource params = new BeanPropertySqlParameterSource(trafficLight);
        insertAction.execute(params);
        return ++insertCount;
    }
}
