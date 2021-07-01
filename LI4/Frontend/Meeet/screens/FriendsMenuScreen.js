import React, { useState } from 'react'
import { View, Text, StyleSheet, TouchableOpacity} from 'react-native'


import Title from '../components/Title'
import AuthContext from '../contexts/AuthContext'


function FriendsMenuScreen({ navigation }) {

  return (
    <View style = {styles.background}>
    <Title title = {'Friends'}/>
    <View style = {styles.body}>

        <View style = {styles.buttons}>


          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('CreateGroup')}>
            <Text style= {{color: '#fbfbfb'}}>
              Create group
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>


          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('GroupList')}>
            <Text style= {{color: '#fbfbfb'}}>
              Group List
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('RequestFriends')}>
            <Text style= {{color: '#fbfbfb'}}>
              Send Request
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('HandleRequest')}>
            <Text style= {{color: '#fbfbfb'}}>
              Accept Request
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('MyFriends')} >
            <Text style= {{color: '#fbfbfb'}}>
              My friends
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


export default FriendsMenuScreen;
