import React from 'react';
import { Text, View, StyleSheet } from 'react-native';
import { widthPercentageToDP as wp, heightPercentageToDP as hp } from 'react-native-responsive-screen';
import * as Speech from 'expo-speech';

export default class Alarm extends React.Component{
  
  setAlarm = () => {
    const thingToSay = '응급차량이'+this.props.close+'미터 떨어진 거리에 있습니다.';
    Speech.speak(thingToSay);
  }

  render() {
    return(
      <View style={styles.container}>
        <View style={styles.view}>
          <Text style={styles.text}
            onLayout={this.setAlarm}>
            응급차량이 {this.props.close}미터 떨어진 거리에 있습니다
          </Text>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    width: wp('90%'),
    height: hp('10%'),
    backgroundColor: 'transparent'
  },
  view: {
    backgroundColor: 'white',
    borderRadius: 3,
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
  text: {
    fontWeight: 'bold',
    fontSize: 22
  }
});