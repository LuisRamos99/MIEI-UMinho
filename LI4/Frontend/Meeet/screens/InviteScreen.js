import React, { Component, useState } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity, Image} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'
import AuthContext from '../contexts/AuthContext'


function InviteScreen({ route, navigation }) {
  const { idEvento } = route.params;


  return (
    <View style = {styles.background}>
    <Title title = {'Event invites'}/>
    <View style = {styles.body}>

        <View style = {styles.buttons}>


          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('InviteFriends', {idEvento:idEvento})}>
            <Text style= {{color: '#fbfbfb'}}>
              Invite Friends
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('InviteGroup', {idEvento:idEvento})}>
            <Text style= {{color: '#fbfbfb'}}>
              Invite Group
            </Text>
          </TouchableOpacity>

          </View>
          </View>

        </View>
  )
}

const styles = StyleSheet.create({
  buttons: {
    flexDirection: 'row',
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    margin: 20,
    alignItems: 'center',
    justifyContent: 'center',
    width: '50%',
    height: 50,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  background: {
      flex : 1,
      backgroundColor:'#ebebeb',
    },
    body: {
      marginTop: 60,
      alignItems: 'center',
      flex: 1,
    },
});


export default InviteScreen;
