package com.example.backend.service.implement;

import static com.example.backend.service.DirectionSearchSetting.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.backend.etc.RouteForm;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dao.RouteDao;
import com.example.backend.dao.TrafficLightDao;
import com.example.backend.dto.Route;
import com.example.backend.dto.TrafficLight;
import com.example.backend.etc.Coordinate;

@Service
public class DirectionSearchServiceImpl {
    @Autowired
    TrafficLightDao trafficLightDao;

    @Autowired
    private RouteDao routeDao;

    private HttpURLConnection con;

    // 좌표 a, b, c 사이 각도 계산
    public double cal_ang(Coordinate a, Coordinate b, Coordinate c) {
        double aa = Math.sqrt(Math.pow(a.x - c.x, 2) + Math.pow(a.y - c.y, 2));
        double bb = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        double cc = Math.sqrt(Math.pow(b.x - c.x, 2) + Math.pow(b.y - c.y, 2));

        double tmp = (Math.pow(bb, 2) + Math.pow(cc, 2) - Math.pow(aa, 2)) / (2 * bb * cc);
        double ang = Math.acos(tmp);
        ang = ang * (180 / 3.14159);
        return ang;
    }

    // 가중치 계산
    public long traffic_value (int i, int j, double path[][][], long time_plus, double location[], double goal[]) {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); // 시:분:초
        String cur_time_str = format.format(new Date()); // 현재 시간

        // (인덱스별 값 -> 0 : 시 / 1 : 분 / 2 : 초)
        String[] cur_time_arr = cur_time_str.split(":");
        int[] cur_time = new int[3];
        for (int k = 0; k < 3; k++)
            cur_time[k] = Integer.parseInt(cur_time_arr[k]);

        // DB에서 신호 등화 순서 & 신호 등화 시간 가져오기 (위도: path[i][j][1] / 경도: path[i][j][0])
        List<TrafficLight> traffic_list = trafficLightDao.getTrafficLight(path[i][j][1], path[i][j][0]);
        System.out.println(i + "-" + j + ":\t" + traffic_list.size() + "개의 신호등 정보를 찾았습니다."); // (신호등 정보 찾았는지 확인용)

        if(traffic_list.size() == 0) return -1; // 일치하는 신호등 정보가 없는 경우

        String order =  traffic_list.get(0).getTrafficOrder(); // 임의로 한 개의 신호등 정보만 이용한다고 가정
        String time = traffic_list.get(0).getTrafficTime();

        String[] order_str = order.split("\\+");
        String[] time_str = time.split("\\+");

        int[] time_arr = new int[time_str.length];
        int time_sum = 0;
        for (int k=0;k<time_str.length;k++) {
            time_arr[k] = Integer.parseInt(time_str[k]);
            time_sum += time_arr[k];
        }

        int cur_sec = (cur_time[0] - 5) * 3600 + cur_time[1] * 60 + cur_time[2]; //5시 기준으로 현재 시각까지 second
        long res_sec = (cur_sec + time_plus) % time_sum;

        // 각도 구하기 위한 좌표들
        Coordinate a = new Coordinate();
        Coordinate b = new Coordinate();
        Coordinate c = new Coordinate();

        if (j == 0) { // 현재 가고자하는 방향 확인하기 위한 좌표 설정 (직진인지 좌회전인지)
            a.x = location[1]; a.y = location[0];
            b.x = path[i][j][1]; b.y = path[i][j][0];
            c.x = path[i][j+1][1]; c.y = path[i][j+1][0];
        }
        else if (j == path[i].length-1) {
            a.x = path[i][j-1][1]; a.y = path[i][j-1][0];
            b.x = path[i][j][1]; b.y = path[i][j][0];
            c.x = goal[1]; c.y = goal[0];
        }
        else {
            a.x = path[i][j-1][1]; a.y = path[i][j-1][0];
            b.x = path[i][j][1]; b.y = path[i][j][0];
            c.x = path[i][j+1][1]; c.y = path[i][j+1][0];
        }

        double ang = cal_ang(a, b, c);

        for (int k = 0; k < time_arr.length; k++) {
            res_sec -= time_arr[k];
            if (res_sec <= 0) {
                if (ang <= 120 && order_str[k].contains("녹색화살표")) // 좌회전
                    return 0;
                else if (ang > 120 && (order_str[k].contains("녹색") || order_str[k].contains("황색"))) // 직진
                    return 0;
                else // 적색
                    return -res_sec;
            }
        }
        return -1;
    }

    // url 연결
    public HttpURLConnection connectUrl(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);     // 클라이언트 아이디
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);    // 클라이언트 비밀키
        con.setDoOutput(true);
        return con;
    }

    // json 코드
    public void analyzeJson(JSONParser parser, int i, String content, String instructions[][], double path[][][], long[][] point_value, double[] start_loc, double[] goal_loc) throws ParseException {

        Object obj = parser.parse(content);
        long code = (Long) ((JSONObject)obj).get("code");

        if(code == 0) { // code : 0인 경우 (정상적으로 정보를 받아온 경우)
            JSONObject route = (JSONObject) ((JSONObject) obj).get("route");
            JSONObject trafast = (JSONObject) ((JSONArray) route.get("trafast")).get(0);
            JSONArray path_arr = (JSONArray) trafast.get("path");
            JSONArray point_arr = (JSONArray) trafast.get("guide");
            point_value[i] = new long[point_arr.size()];
            path[i] = new double[point_arr.size()][2];
            instructions[i] = new String[point_arr.size()];

            for (int j = 0; j < point_arr.size(); j++) { // 해당 도로를 지나가는데 걸리는 시간
                JSONObject tmpObj = (JSONObject) point_arr.get(j);
                long index = (long) tmpObj.get("pointIndex");
                point_value[i][j] = (long) tmpObj.get("duration");
                instructions[i][j] = (String) tmpObj.get("instructions");

                JSONArray tmpArr = (JSONArray) path_arr.get((int) index);
                path[i][j][0] = (double) tmpArr.get(0);
                path[i][j][1] = (double) tmpArr.get(1);
            }

            if (i == 0) { // 처음 한 번만 저장
                JSONObject loc = (JSONObject) trafast.get("summary");
                JSONArray start = (JSONArray) ((JSONObject) loc.get("start")).get("location");
                JSONArray goal = (JSONArray) ((JSONObject) loc.get("goal")).get("location");
                start_loc[0] = (double) start.get(0);
                start_loc[1] = (double) start.get(1);
                goal_loc[0] = (double) goal.get(0);
                goal_loc[1] = (double) goal.get(1);
            }
        }
    }

    // 길찾기
    public RouteForm findRoute(int emergencyCarId, int auth, double src_longitude, double src_latitude, double dst_longitude, double dst_latitude) throws IOException, ParseException {

        String urlSrcDst = "?start=" + src_longitude + "," + src_latitude + "&goal=" + dst_longitude + "," + dst_latitude; // 출발지 및 도착지
        String urlWayPoints[] = new String[3]; // 경유지
        String urlOption = "&option=trafast";  // 옵션

        // 경유지 설정
        double longitude_difference = dst_longitude - src_longitude;
        double latitude_difference = dst_latitude - src_latitude;
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            urlWayPoints[i] = "&waypoints=" + (src_longitude + longitude_difference * (j / 4.0)) + "," + (src_latitude + latitude_difference * (j / 4.0));
        }

        // url 만들기
        String option1 = urlAddr + urlSrcDst + urlOption; // 경유지 없는 경우
        String[] option2 = new String[3];                 // 경유지 있는 경우
        for (int i = 0; i < 3; i++)
            option2[i] = urlAddr + urlSrcDst + urlWayPoints[i] + urlOption;

        URL url = new URL(option1);
        String[] content = new String[4];

        int route_cnt = 4; // (경우의 수)
        double[][][] path = new double[route_cnt][][];
        long[][] point_value = new long[route_cnt][];
        String[][] instructions = new String[route_cnt][];
        double[] start_loc = new double[2];
        double[] goal_loc = new double[2];

        JSONParser parser = new JSONParser();
        for (int i = 0; i < route_cnt; i++) { // (경유지 없는 version 1개, 경유지 있는 version 3개)

            if (i != 0) url = new URL(option2[i - 1]); // url 재설정
            con = connectUrl(url);
            int responseCode = con.getResponseCode(); // 응답코드

            BufferedReader br;
            if (responseCode == 200) br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            else br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) response.append(inputLine);
            br.close();

            content[i] = response.toString();
            analyzeJson(parser, i, content[i], instructions, path, point_value, start_loc, goal_loc);
        }

	boolean valid = true;
	for(int i = 0; i < route_cnt; i++) 
		if(point_value[i] == null) 
			valid = false;

	if(valid == false){
	       	System.out.println("경로를 찾지 못했습니다");	
		return null; 
	}

        int max_size = -1;
        for(int i = 0; i < route_cnt; i++)
            if(max_size < point_value[i].length) max_size = point_value[i].length;

        int weight[][] = new int[route_cnt][max_size];

        // 어떤 경로가 가장 적절한 경로인가
        long minValue = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < route_cnt; i++) {
            long cur_value = 0;
            for (int j = 0; j < point_value[i].length; j++) {
                cur_value += point_value[i][j];
                cur_value += traffic_value(i, j, path, cur_value, start_loc, goal_loc);
                weight[i][j] = (int) cur_value;
            }
            if (minValue > cur_value) {
                minValue = cur_value;
                minIndex = i;
            }
        }

        System.out.println(minIndex + "번째 경로가 최소 가중치 " + minValue + "를 가집니다.");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if(auth==1) { // 응급 차량의 경로만 db에 저장
            int node_cnt = 0;
            Route element = new Route();
            element.setEmergencyCarId(emergencyCarId);
            element.setLatitude(start_loc[1]);
            element.setLongitude(start_loc[0]); // 경로 시작점
            element.setNodeId(node_cnt++);
            element.setTime(format.format(cal.getTime()));
            routeDao.insertRoute(element);

            for (int i = 0; i < path[minIndex].length; i++) {
                element.setLatitude(path[minIndex][i][1]);
                element.setLongitude(path[minIndex][i][0]);
                element.setNodeId(node_cnt++);
                if(i == 0) cal.add(Calendar.MILLISECOND, weight[minIndex][i]);
                else cal.add(Calendar.MILLISECOND, weight[minIndex][i] - weight[minIndex][i-1]);
                element.setTime(format.format(cal.getTime()));
                routeDao.insertRoute(element);
            }
        }

        // 최적 경로 제공 폼
        RouteForm form = new RouteForm();
        form.setPath(path[minIndex]);
        form.setInstructions(instructions[minIndex]);
        return form; // 최적 경로 반환
    }
}
