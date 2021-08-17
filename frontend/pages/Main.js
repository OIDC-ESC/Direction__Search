import React from 'react';
import Select from '../components/Select';
import { StyleSheet, View } from 'react-native';

export default class Main extends React.Component {

  render() {
    return (
      <View style={styles.buttons}>
        <Select onPress={ ()=> this.props.navigation.navigate( 'Emergency' ) }>
          응급차량
        </Select>
        <Select onPress={ ()=> this.props.navigation.navigate( 'Driver' ) }>
          일반운전자
        </Select>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  buttons: {
    flexDirection: 'row',
    paddingTop: '50%'
  }
});
