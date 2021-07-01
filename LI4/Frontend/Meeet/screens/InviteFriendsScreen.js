import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity, Image} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'
import AuthContext from '../contexts/AuthContext'
import TouchableSearchList from '../components/TouchableSearchList'

function InviteFriendsScreen( { route, navigation } ) {
  const [selectedList, setList] = useState([]);
  const [groupName, updateGroupName] = useState('');
  const [group, setGroup] = React.useState(null);
  const [res, setRes] = React.useState(null);
  const [friendsIdList, setFriendsIdList] = useState([]);
  const [friendsList, setFriendsList] = useState([]);
  const [convite, setConvite] = useState(null);

  const { idEvento } = route.params;

  function nothing(item){
    item.isSelected = !item.isSelected;
    if(item.isSelected){
      setList(oldArray => [...oldArray, item.id]);
      item.selectedClass = styles.selected;
    }
    else{
      let auxArray= selectedList.filter(value => { return value != item.id })
      setList(auxArray);
      item.selectedClass = styles.itemPress;
    }
  }

  React.useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getAmizadesUser/' + global.userID)
    .then((response) => response.json())
    .then((json) => {
      setFriendsIdList(json);
    })
    .catch((error) => {
      console.error(error);
    });
  },[]);

  React.useEffect(() => {
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

  React.useEffect(() => {
      if(convite != null) selectedList.forEach(inviteToEvent);
  },[convite]);

  async function inviteToEvent(user, i){
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/InviteToEvent/' + user, {
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
    <Title title = {'Invite Friends'}/>
    <View style = {styles.body}>

      <View style = {styles.list}>

        <TouchableSearchList
          data={friendsList}
          touchFunction={nothing}
        />

      </View>

      <View style = {styles.buttons}>

        <TouchableOpacity style={styles.button} onPress={() => {inviteAll(); navigation.navigate('EventMenu'); }}>
          <Text style= {{color: '#fbfbfb'}}>
            Create
            </Text>
          </TouchableOpacity>

        </View>
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
    selected: {
        backgroundColor: '#C78B50'
      },
    list: {
      height: '80%',
      width: '100%',
    },
    buttons: {
      alignItems: 'center',
      marginTop: 10,
      width: '50%',
    },
    button: {
      alignItems: 'center',
      justifyContent: 'center',
      width: '100%',
      height: 50,
      borderRadius: 10,
      backgroundColor: '#2c365d',
    },
  });

export default InviteFriendsScreen;
