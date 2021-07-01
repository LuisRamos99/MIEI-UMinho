import React, { Component, useState } from 'react'
import { View, Text, StyleSheet, TouchableOpacity} from 'react-native'

import Title from '../components/Title'

function EventMenuScreen({ navigation }) {

  return (
    <View style = {styles.background}>
    <Title title = {'Events'}/>
    <View style = {styles.body}>

        <View style = {styles.buttons}>


          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('CreateEvent')}>
            <Text style= {{color: '#fbfbfb'}}>
              Create event
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('HandleInvite')}>
            <Text style= {{color: '#fbfbfb'}}>
              Event invitations
            </Text>
          </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('EventList')}>
            <Text style= {{color: '#fbfbfb'}}>
              Ongoing events
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


export default EventMenuScreen;
