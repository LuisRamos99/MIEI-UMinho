import React, { Component } from 'react'
import { View, Text, StyleSheet, TextInput} from 'react-native'


import Title from '../components/Title'
import CreateEvent from '../components/CreateEvent'


function CreateEventScreen( { navigation } ) {


  return (
    <View style = {styles.background}>
       <Title title = {'Create Event'}/>
          <View style = {styles.body}>
             <CreateEvent navigation={navigation}/>
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

export default CreateEventScreen;
