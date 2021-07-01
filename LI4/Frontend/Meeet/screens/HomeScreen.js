import React, { Component, useState } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity, Image} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Login from '../components/Login'
import AuthContext from '../contexts/AuthContext'

function HomeScreen({ navigation }) {
  const { signOut } = React.useContext(AuthContext);


  return (
    <View style = {styles.loginBox}>
      <Image style = {styles.logo} source={require('../assets/meeetIcon.png')} />
      <View style={styles.line}/>
        <View style = {styles.buttons}>

          <Icon
            name="calendar-alt"
            size={30}
            color='#2c365d'
            />
          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('EventMenu')}>
            <Text style= {{color: '#fbfbfb'}}>
              Events
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <Icon
            name="user-friends"
            size={20}
            color='#2c365d'
            />
          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('FriendsMenu')}>
            <Text style= {{color: '#fbfbfb'}}>
              Friends
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <Icon
            name="user"
            size={25}
            color='#2c365d'
            />
          <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Profile')}>
            <Text style= {{color: '#fbfbfb'}}>
              Profile
              </Text>
            </TouchableOpacity>

          </View>
        <View style = {styles.buttons}>

          <Icon
            name="sign-out-alt"
            size={25}
            color='#2c365d'
            />
          <TouchableOpacity style={styles.button} onPress={signOut}>
            <Text style= {{color: '#fbfbfb'}}>
              Sign Out
              </Text>
            </TouchableOpacity>

          </View>

        </View>
  )
}

const styles = StyleSheet.create({
  loginBox: {
    flex : 1,
    alignItems : 'center',
    backgroundColor:'#ebebeb',
  },
  logo: {
    resizeMode: 'contain',
    width: '30%',
    height: '30%',
  },
  line: {
    borderColor: '#2c365d',
    borderWidth: 2,
    width: '70%',
    marginBottom: 30,
  },
  buttons: {
    flexDirection: 'row',
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    margin: 15,
    alignItems: 'center',
    justifyContent: 'center',
    width: '50%',
    height: 40,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  });

export default HomeScreen;
