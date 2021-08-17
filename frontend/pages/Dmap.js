import React from 'react';
import { Text, View, StyleSheet, Dimensions, Alert, Image } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import * as Location from "expo-location";
import axios from 'axios';
import Container from '../components/Container';
import Alarm from './Alarm';
import * as Speech from 'expo-speech';

export default class Dmap extends React.Component{
    state = {
        loading: true,
        isClose: false,
        polyLoading: true,
        arrive: false,
        lat_delta: 0.005,
        lng_delta: 0.005,
        close_meter: 0,
        start_lat: 0,
        start_lng: 0,
        end_lat: 0,
        end_lng: 0,
        cur_lat: 0,
        cur_lng: 0,
        emer_lat: 37.564102,
        emer_lng: 126.944989,
        polylines: [],
        instructions: []
      }
  getLatLng = async (start, end) => {
    const ID_KEY;
    const SECRET_KEY;
        try{
          axios
            .get(`https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${start}`, {
              headers: {
                'Access-Control-Allow-Origin': '*',
                'X-NCP-APIGW-API-KEY-ID': ID_KEY,
                'X-NCP-APIGW-API-KEY': SECRET_KEY,
              },
            })
            .then(response => {
              this.setState({ start_lat: response.data.addresses[0].y, start_lng: response.data.addresses[0].x });
            });
        } catch (error) {
          Alert.alert("Can't find you in start");
          throw error;
        }

        try{
          axios
            .get(`https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${end}`, {
              headers: {
                'Access-Control-Allow-Origin': '*',
                'X-NCP-APIGW-API-KEY-ID': ID_KEY,
                'X-NCP-APIGW-API-KEY': SECRET_KEY,
              },
            })
            .then(response => {
              this.setState({ end_lat: response.data.addresses[0].y, end_lng: response.data.addresses[0].x });
            })
            .then(() => {
              this.getPolyline( this.state.start_lat, this.state.start_lng, this.state.end_lat, this.state.end_lng);
            });
        } catch (error) {
          Alert.alert("Can't find you in end");
          throw error;
        }
      }
    
      getLocation = async () => {
        try {
          await Location.requestForegroundPermissionsAsync();
          const { coords: { latitude, longitude } } = await Location.getCurrentPositionAsync();
          this.setState({ cur_lat: latitude, cur_lng: longitude, loading: false });
        } catch (error) {
          Alert.alert("Can't find you");
        }
      }

      setPolyline = ( data_arr ) => {
        let polylines = []
        for (let i=0;i<data_arr.length;i++) {
          let lat = data_arr[i][1];
          let lng = data_arr[i][0];
          let poly_dic = {
            latitude: lat,
            longitude: lng
          }
          polylines.push(poly_dic);
        }
        this.setState({ polyLoading: true });
        return polylines;
      }

    getPolyline = async ( start_lat, start_lng, end_lat, end_lng ) => {
        let poly_arr = [];
        let instruction_arr = [];
        try{
          let params = new URLSearchParams();
          params.append("auth", 2);
          params.append("start_lat", start_lat);
          params.append("start_lng", start_lng);
          params.append("end_lat", end_lat);
          params.append("end_lng", end_lng);
      
          axios.post('http://oidcesc.loca.lt/api/drive', params)
            .then(response => {
              poly_arr = response.data.path;
              let res_inst = response.data.instructions;
              for (let i=0;i<res_inst.length;i++) {
                let dict = {
                  instruction: res_inst[i],
                  check: false
                }
                instruction_arr.push(dict);
              }
              this.setState({ 
                polylines: this.setPolyline(poly_arr),
                instructions: instruction_arr
              });
              console.log(poly_arr);
            })
        } catch (error) {
          Alert.alert("Can't get polyline array");
          throw error;
        }
    }
    
      drawPolyline = ( data_arr ) => {
        let return_arr = [];
        let len = data_arr.length;
        for (let i=0;i<len-1;i++) {
          let data = data_arr[i];
          let next_data = data_arr[i+1];
          let arr = [ data, next_data ]
          return_arr.push(
            <MapView.Polyline
              key={i}
              coordinates={ arr }
              strokeWidth={4}
              strokeColor="#12bc00"
              lineDashPattern={[0]}/>
          );
        }
        return return_arr;
      }

      getDistance = ( start_lat, start_lng, end_lat, end_lng ) => {
        let lat = (end_lat - start_lat) * Math.PI / 180;
        let lng = (end_lng - start_lng) * Math.PI / 180;
        let dist = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(start_lat * Math.PI / 180) * Math.cos(end_lat * Math.PI / 180) * Math.sin(lng / 2) * Math.sin(lng / 2);
        dist = 2 * Math.atan2(Math.sqrt(dist), Math.sqrt(1-dist));
        return dist * 6371 * 1000;
      }
  
      checkEmergency = async () => {
        axios.get('http://oidcesc.loca.lt/api/driver')
            .then(response => {
              console.log(response);
              if(response.data.success){
                let emer = response.data;  
                let meter = this.getDistance(emer.latitude, emer.longitude, this.state.cur_lat, this.state.cur_lng);
                if (meter < 10 && !this.state.isClose){
                    this.setState({ isClose: true, emer_lat: emer.latitude, emer_lng: emer.longitude, close_meter: meter });
                }
                else if (meter > 10 && this.state.isClose) {
                    this.setState({ isClose: false });
                }
              }
          });
      }
      
      checkEnd = ( end_lat, end_lng ) => {
          let meter = this.getDistance(end_lat, end_lng, this.state.cur_lat, this.state.cur_lng);
          if (meter < 10){
            const thingToSay = '목적지 부근에 도착했습니다.';
            Speech.speak(thingToSay);
            this.setState({ arrive: true })
          }
      }
      
      checkAlarm = () => {
        let poly_arr = this.state.polylines;
        let rev_instructions = this.state.instructions;
        for (let i=0;i<poly_arr.length;i++) {
          if (!rev_instructions[i].check && this.getDistance(poly_arr[i].latitude, poly_arr[i].longitude, this.state.cur_lat, this.state.cur_lng) < 10) {
            const thingToSay = this.state.instructions[i]+'입니다.';
            Speech.speak(thingToSay);
            rev_instructions[i].check=true;
            this.setState({ instructions: rev_instructions }); 
            break;
          }
        }
      }

      componentDidMount() {
        this.getLatLng(this.props.route.params.start, this.props.route.params.end);
        this.getLocation();
        setInterval(() => {
            this.getLocation();
            if (this.state.polyLoading) {
                this.checkAlarm();
              }
            if (!this.state.arrive) {
                this.checkEnd(this.state.end_lat, this.state.end_lng);
              }
          }, 2000);
        setInterval(() => {
          this.checkEmergency();
          }, 5000);
      }

      componentWillUnmount() {
        const thingToSay = '경로 탐색을 종료합니다.';
        Speech.speak(thingToSay);
      }

      render() {
        const { 
          loading,
          isClose,
          polyLoading,
          lat_delta,
          lng_delta,
          emer_lat, 
          emer_lng,
          close_meter, 
          cur_lat, 
          cur_lng,
          polylines } = this.state;
        return (
          <Container>
            {loading? (
              <View style={styles.container}>
                <Text styles={styles.loadingText}>Loading...</Text>
              </View>
              ) :
              (<View style={styles.view}>
                <MapView 
                  style={styles.map} 
                  initialRegion={{
                    latitude: cur_lat, 
                    longitude: cur_lng,
                    latitudeDelta: lat_delta,
                    longitudeDelta: lng_delta,
                  }}
                  region={{
                    latitude: cur_lat, 
                    longitude: cur_lng,
                    latitudeDelta: lat_delta,
                    longitudeDelta: lng_delta,
                  }}
                  onRegionChangeComplete={region => {
                    this.setState({
                      lat_delta: region.latitudeDelta,
                      lng_delta: region.longitudeDelta
                    });
                  }}
                >
                  <Marker coordinate={{latitude: cur_lat, longitude: cur_lng}}>
                    <Image source={require('../components/marker_img.png')} style={{ height: 53, width:33 }} />
                  </Marker>
                  {isClose? (
                    <Marker coordinate={{latitude: emer_lat, longitude: emer_lng}}>
                      <Image source={require('../components/emergency_img.png')} style={{ height: 53, width:43, position: 'absolute' }} />
                  </Marker>) : null}
                  { polyLoading? (this.drawPolyline(polylines)):null }
                </MapView>
              </View>)}
              {isClose? (<Alarm close={close_meter}/>) : null}
          </Container>
        );
      }
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  view: {
    flex: 1,
    backgroundColor: '#fff',
    justifyContent: 'center',
    alignItems: 'center',
  },
  map: {
    width: Dimensions.get('window').width,
    height: Dimensions.get('window').height,
  },
  loadingText: {
    fontWeight: 'bold',
    fontSize: 22
  }
});
