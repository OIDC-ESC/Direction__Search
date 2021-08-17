import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import Driver from './pages/Driver';
import Emergency from './pages/Emergency';
import Main from './pages/Main';
import Emap from './pages/Emap';
import Dmap from './pages/Dmap';

const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Main" component={Main} options={{ title: '최적의 경로 탐색' }} />
        <Stack.Screen name="Driver" component={Driver} options={{ title: '' }} />
        <Stack.Screen name="Emergency" component={Emergency} options={{ title: '' }} />
        <Stack.Screen name="Emap" component={Emap} options={{ title:'' }}/>
        <Stack.Screen name="Dmap" component={Dmap} options={{ title:'' }}/>
      </Stack.Navigator>
    </NavigationContainer>
  );
}
