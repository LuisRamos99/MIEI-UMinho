import React, { Component, useState, useEffect} from 'react'
import { View, Image, Text, StyleSheet, Linking, TextInput, Button, TouchableOpacity} from 'react-native'

import Icon from 'react-native-vector-icons/dist/FontAwesome5'
import moment from "moment";


function EventDetails({ data, navigation }) {

    function formatDate(string){
      return new Date(string).toString().substring(0,15);
  }

  function openCalendar() {
    const dat = moment(new Date(data.dataHora));
    Linking.openURL('content://com.android.calendar/time/' + dat);
  }

  function openMaps() {
    Linking.openURL('google.navigation:q='+data.latitude+'+'+data.longitude);
  }


    return (
       <>

       <View style = {styles.profileInput}>


        <Text style = {styles.nomeEvento}>
          "{data.nome}"
        </Text>
        {(data.idAdmin == global.userID) ?
          (<TouchableOpacity  style= {{paddingLeft: 10}} onPress={() => navigation.navigate('EditEvent', {evento: data})}>
            <Icon
              name="edit"
              size={20}
              color='#2c365d'
            />
          </TouchableOpacity>) : (<></>)
        }
        </View>

       <View style = {styles.textBox}>
         <Text style = {styles.text}>
              {data.descricao}
          </Text>

        </View>


        <View style = {styles.body}>

          <View style = {{flexDirection: 'row'}}>
           <Icon
              name="exclamation-triangle"
              size={30}
              color='#FB2A2A'
            />
           <Text style = {{...styles.textNegrito,marginTop:2}}>
              {data.idadeMinima ? 'Minimum age: ' + data.idadeMinima : 'No minimum age!'}
           </Text>

           </View>

           <View style = {{flexDirection: 'row',marginTop:30,alignItems: 'center'}}>
             <TouchableOpacity onPress={() => openMaps()}>
             <Icon
              name="globe-europe"
              size={35}
              color='#2c365d'
            />
            </TouchableOpacity>
           <Text style = {styles.textNegrito}>
              Location
           </Text>
           </View>


           <View style = {{flexDirection: 'row',marginTop:30,alignItems: 'center'}}>
             <TouchableOpacity onPress={() => openCalendar()}>
             <Icon
              name="calendar-alt"
              size={35}
              color='#2c365d'
            />
            </TouchableOpacity>
           <Text style = {styles.textNegrito}>
              {formatDate(data.dataHora)}
           </Text>
           </View>



           <Text style = {{...styles.textNegrito,marginLeft:30,marginTop:20}}>
              {data.dataHora.substring(11,16)}
           </Text>
           </View>




    </>
    )
}

const styles = StyleSheet.create({
  textNegrito:{
    marginLeft: 15,
    fontSize: 20,
    fontWeight: "bold",
  },
  profileInput:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  textBox: {
    borderColor: 'gray',
    borderWidth: 1,
    marginTop: 40,
    height: '30%',
    width: '80%',
    backgroundColor: '#FFFDFD',
  },
  nomeEvento: {
    marginTop: 20,
    fontSize: 30,
    color: '#2c365d',
  },
  text: {
    marginLeft: 5,
    marginRight: 5,
    height: '100%',
  },
  list: {
    height: '68%',
    width: '100%',
  },
  body: {
    marginTop: 20,
    alignItems: 'center',
  },
});


export default EventDetails;
