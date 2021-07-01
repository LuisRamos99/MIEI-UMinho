import * as React from 'react';
import { StyleSheet } from 'react-native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import EventTabBar from '../components/EventTabBar'

import EventDetailsScreen from './EventDetailsScreen'
import VotesScreen from './VotesScreen'
import EventUsersScreen from './EventUsersScreen'
import ShareLocationScreen from './ShareLocationScreen'

import EventContext from '../contexts/EventContext'


const Tab = createBottomTabNavigator();

function EventScreen({ route }) {

    return (
    <EventContext.Provider value={{evento: route.params.evento}}>
      <Tab.Navigator tabBar={props => <EventTabBar {...props} />} >
        <Tab.Screen name="EventDetails" component={EventDetailsScreen} />
        <Tab.Screen name="EventUsers" component={EventUsersScreen} />
        <Tab.Screen name="ShareLocation" component={ShareLocationScreen} />
        <Tab.Screen name="Vote" component={VotesScreen} />
      </Tab.Navigator>
    </EventContext.Provider>
  )
}

const styles = StyleSheet.create({
  profileBox: {
    flex : 1,
    alignItems : 'center',
    backgroundColor:'#ebebeb',
  },
  background: {
    flex : 1,
    backgroundColor:'#ebebeb',
  },
  });

export default EventScreen;
