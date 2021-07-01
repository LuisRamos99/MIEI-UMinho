import { View, StyleSheet, ActivityIndicator} from 'react-native'
import React, { useState, useEffect } from 'react'

import Title from '../components/Title'
import EventContext from '../contexts/EventContext'
import EventUsers from '../components/EventUsers'

function EventUsersScreen({ navigation }) {

  const { evento } = React.useContext(EventContext);
  const [userEvents, setUserEvents] = React.useState([]);
  const [users, setUsers] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserEventosPerEvent/' + evento.id, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => {return response.json(); } )
    .then(json => {
      setUserEvents(json);
    })
    .catch((error) => {
      console.error(error);
    });
  }, []);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerEvent', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userEvents)
    })
    .then((response) => { return response.json()} )
    .then((json) => {
      setUsers(json);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [userEvents]);

  useEffect(() => {
    if(users.length > 0)
      setIsLoading(false);
  }, [users]);

return (
    <View style = {styles.background}>
        <Title title = {'Event Users'}/>
        <View style = {styles.body}>
        { isLoading ? <ActivityIndicator/> : <EventUsers data={users} navigation={navigation}/> }
        </View>
    </View>
  )
}

const styles = StyleSheet.create({
    background: {
        flex : 1,
        backgroundColor:'#ebebeb',
      },
    body: {
        alignItems: 'center',
        flex: 1,
      },
  });


export default EventUsersScreen;
