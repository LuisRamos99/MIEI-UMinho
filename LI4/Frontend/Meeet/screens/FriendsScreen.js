import React, { Component , useState, useEffect } from 'react'
import { View,StyleSheet} from 'react-native'

import Title from '../components/Title'
import MyFriends from '../components/MyFriends';
import AuthContext from '../contexts/AuthContext'


function FriendsScreen({ navigation }) {

return (
  <View style = {styles.background}>
  <Title title = {'My Friends'}/>
  <View style = {styles.body}>
      <MyFriends navigation={navigation}/>
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
  profileBox: {
    flex : 1,
    alignItems : 'center',
    backgroundColor:'#ebebeb',
  },
  });


export default FriendsScreen;
