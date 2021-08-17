import React from 'react';
import { StyleSheet, TextInput, Text, View } from 'react-native';
import Container from '../components/Container';
import Button from '../components/Button';
import * as Speech from 'expo-speech';

export default class Emergency extends React.Component {
  state = {
    start: "",
    end: ""
  }

  doWithSpeak = () => {
    const thingToSay = '응급차량 경로 탐색을 시작합니다.';
    Speech.speak(thingToSay);
    this.props.navigation.navigate('Emap', {
      start: this.state.start,
      end: this.state.end
    })
  }

  render() {
    return(
      <Container>
        <View style={styles.view}>
          <Text style={styles.title}>응급차량 경로 탐색</Text>
          <View style={styles.content}>
            <Text style={styles.label}>출발지</Text>
            <TextInput
              value={ this.state.start }
              style={styles.inputStyle}
              onChangeText={ start => this.setState({ start }) }
            />
          </View>
          <View style={styles.content}>
            <Text style={styles.label}>도착지</Text>
            <TextInput
              value={ this.state.end }
              style={styles.inputStyle}
              onChangeText={ end => this.setState({ end }) }  
            />
          </View>
        </View>
        <Button onPress={ this.doWithSpeak }>
          위치 입력 완료
        </Button>
      </Container>
    );
  }
}

const styles = StyleSheet.create({
  inputStyle: {
    flex: 1,
    width: '100%',
    borderWidth: 1,
    borderColor: '#666666',
    padding: 8,
    fontSize: 20,
    marginBottom: 12
  },
  title: {
    fontSize: 25,
    fontWeight: 'bold',
    margin: 10,
    marginBottom: 30,
    textAlign: 'center',
    color: 'red'
  },
  content: {
    flexDirection: 'row'
  },
  label: {
    fontSize: 20,
    fontWeight: 'bold',
    margin: 10
  },
  view: {
    flex: 1
  }
});
