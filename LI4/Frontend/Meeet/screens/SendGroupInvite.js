import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity, Image, ActivityIndicator, FlatList} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'
import AuthContext from '../contexts/AuthContext'
import TouchableSearchList from '../components/TouchableSearchList'

function SendGroupInvite( { route, navigation } ) {
  const { grupo } = route.params;
  const { idEvento } = route.params;

  const [userGroups, setUserGroups] = React.useState(null);
  const [users, setUsers] = React.useState(null);
  const [convite, setConvite] = React.useState(null);
  const [isLoading, setIsLoading] = useState(true);


  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserGruposPerGroup/' + grupo.id, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setUserGroups(json);
    })
    .catch((error) => {
      console.error('ERROR:' + error);
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

  React.useEffect(() => {
      if(users != null) inviteAll();
  },[users]);

  React.useEffect(() => {
      if(convite != null){
         users.forEach(inviteToEvent);
         navigation.navigate('EventMenu');
       }
  },[convite]);

  async function inviteToEvent(user, i){
    if(user.id != global.userID)
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/InviteToEvent/' + user.id, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(convite)
    })
    .catch((error) => {
      console.error(error);
    });
  }

  async function inviteAll(){
    const conv = {
      idConvidador: global.userID  ,
      idEvento: idEvento,
      utilizadorConvites: null,
    }

    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/MakeConvite', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(conv)
    }).then(response => {setConvite(conv)})
    .catch((error) => {
      console.error(error);
    });
  }

  return (
    <View style = {styles.background}>
    <Title title = {'Groups'}/>
    <View style = {styles.body}>
      <ActivityIndicator/>
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
        justifyContent: 'center',
        flex: 1,
      },
  });

export default SendGroupInvite;
