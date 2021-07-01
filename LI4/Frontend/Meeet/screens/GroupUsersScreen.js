import { View, StyleSheet, ActivityIndicator} from 'react-native'
import React, { useState, useEffect } from 'react'

import Title from '../components/Title'
import GroupUsers from '../components/GroupUsers'

function GroupUsersScreen({ navigation,route }) {
  const { id } = route.params;
  const [users, setUsers] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [userGroups, setUserGroups] = React.useState(null);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserGruposPerGroup/' + id, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => {return response.json(); } )
    .then(json => {
      setUserGroups(json);
    })
    .catch((error) => {
      console.error(error);
    });
  }, []);

  useEffect(() => {
    if(userGroups != null)
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerGroup', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userGroups)
    })
    .then((response) => {return response.json()} )
    .then((json) => {
      setUsers(json);
      setIsLoading(false);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [userGroups]);

  
  useEffect(() => {
    if(users.length > 0)
      setIsLoading(false);
  }, [users]);

return (
    <View style = {styles.background}>
        <Title title = {'Group Users'}/>
        <View style = {styles.body}>
        { isLoading ? <ActivityIndicator/> : <GroupUsers data={users} navigation={navigation}/> }
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


export default GroupUsersScreen;