import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, ActivityIndicator} from 'react-native'

import Title from '../components/Title'
import CreateGroup from '../components/CreateGroup'


function CreateGroupScreen() {

  const [friendsList,setFriendsList] = React.useState(null);
  const [isLoading, setLoading] = useState(true);
  const [friendsIdList, setFriendsIdList] = useState(null);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getAmizadesUser/' + global.userID)
    .then((response) => response.json())
    .then((json) => {
      setFriendsIdList(json);
    })
    .catch((error) => {
      console.error(error);
    });
  },[]);

  useEffect(() => {
    if(friendsIdList != null)
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerIDs', {
      method: 'POST',
      headers: {
      Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(friendsIdList)
    })
    .then((response) => response.json())
    .then((json) => {
      setFriendsList(json);
    })
    .catch((error) => {
      console.error(error);
    });
  },[friendsIdList]);

  useEffect(() => {
    if(friendsList != null)
    setLoading(false);
  },[friendsList]);

  return (
    <View style = {styles.background}>
    <Title title = {'Create Group'}/>
    <View style = {styles.body}>
        { isLoading ? <ActivityIndicator/> :  <CreateGroup data={friendsList}/> }
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


export default CreateGroupScreen;
