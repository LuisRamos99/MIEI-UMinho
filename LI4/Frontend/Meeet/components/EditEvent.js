import React, { Component, useState } from 'react'
import { View, Image, Text, StyleSheet, TextInput, Alert, Button, TouchableOpacity} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'
import DateTimePickerModal from "react-native-modal-datetime-picker";

import Geocoder from 'react-native-geocoding'

function EditEvent({ ev , navigation }) {
  const [eventName, updateEventName] = useState(ev.nome);
  const [eventLongitude, updateEventLongitude] = useState(null);
  const [eventLatitude, updateEventLatitude] = useState(null);
  const [eventDateTime, updateEventDateTime] = useState(ev.dataHora);
  const [eventType, updateEventType] = useState(ev.tipoEvento);
  const [eventAge, updateEventAge] = useState(ev.idadeMinima.toString());
  const [eventDescription, updateEventDescription] = useState(ev.descricao);
  const [isDateTimePickerVisible, setDateTimePickerVisibility] = useState(false);
  const [local, updateLocal] = useState('');
  const [bool, setBool] = useState(false);

  const [evento, setEvento] = React.useState(null);

    React.useEffect(() => {
      setEvento({
        nome: eventName,
        dataHora: eventDateTime,
        longitude: eventLongitude,
        latitude: eventLatitude,
        tipoEvento: 0,
        idAdmin: global.userID,
        descricao: eventDescription,
        idadeMinima: Number(eventAge),
        idAdminNavigation: null,
        eventoHasRequests: null,
        utilizadorEvento: null,
        votacao: null
      });
  },[eventName, eventDateTime, eventLongitude, eventLatitude, eventDescription, eventAge]);


    const showDatePicker = () => {
      setDateTimePickerVisibility(true);
    };

    const hideDatePicker = () => {
      setDateTimePickerVisibility(false);
    };

    const handleConfirm = (date) => {
       hideDatePicker();
      updateEventDateTime(date);
    };


    const createAlert = () =>
    Alert.alert(
      "Edit Event",
      "Are you sure?",
      [
        {
          text: "Cancel",
          style: "cancel",
        },
        { text: "OK", onPress: () => {setBool(true);} }
      ],
      { cancelable: false }
    );

    React.useEffect(() => {
      if (bool==true) {
      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/UpdateEvent/' + ev.id, {
        method: 'PUT',
        headers: {
          "Accept": "application/json",
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(evento)
      })
      .then(response => { navigation.navigate('EventMenu') } )
      .catch((error) => {
        console.error('ERROR:' + error);
      });}}, [bool]);




  Geocoder.init("AIzaSyA2BjSqzfdbdvFdzhLkcf0WXNSBiBB3XDI",{language : "pt"});

  function up() {
    if (local!='') {
      Geocoder.from(local)
        .then(json => {
            var location = json.results[0].geometry.location;
            updateEventLatitude(location.lat);
            updateEventLongitude(location.lng);
        })
        .catch(error => console.log(error));
     }
     else{
       updateEventLatitude(ev.latitude);
       updateEventLongitude(ev.longitude);
     }
    };


    return (
    <>

      <View style = {styles.profileInput}>

        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder={"Name"}
          onChangeText={updateEventName}
          value={eventName}
          />

        </View>


        <View style = {styles.profileInput}>

        </View>

        <View style = {styles.profileInput}>

        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder={"Age restriction"}
          onChangeText={updateEventAge}
          value={eventAge}
          />

        </View>


        <View style = {styles.profileInput}>

        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder={"Unchanged"}
          onChangeText={updateLocal}
          value={local}
          />

        </View>


        <View style = {styles.description}>

        <TextInput
          style={styles.input}
          multiline
          placeholder={"Description"}
          onChangeText={updateEventDescription}
          value={eventDescription}
          />

        </View>

        <View style = {styles.icons}>

         <TouchableOpacity onPress={showDatePicker}>
            <Icon
            name="calendar-alt"
            size={70}
            color='#2c365d'
            />
            </TouchableOpacity>

          <DateTimePickerModal
              isVisible={isDateTimePickerVisible}
              mode="datetime"
              onConfirm={handleConfirm}
              onCancel={hideDatePicker}
           />
      </View>



    <View style = {styles.buttons}>

    <TouchableOpacity style={styles.buttonCreate} onPress={ () => {up(); createAlert()}}>
      <Text style= {{color: '#fbfbfb'}}>
        Save
       </Text>
       </TouchableOpacity>

    </View>

    </>
    )
}

const styles = StyleSheet.create({
  textInput: {
    margin: 5,
    marginHorizontal: 5,
    backgroundColor: '#cbcbcb',
    height: 40,
    width: '70%',
    fontSize: 16,
    borderRadius: 10,
  },
  input:{
    marginLeft: 10,
    marginRight: 10,
  },
  profileInput:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  description:{
    margin: 10,
    marginHorizontal: 10,
    backgroundColor: '#cbcbcb',
    height: '20%',
    width: '70%',
    fontSize: 16,
    borderRadius: 10,
  },
  icons: {
    marginTop: 20,
    alignItems: 'center',
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    width: '70%',
  },
  buttons: {
    alignItems: 'center',
    width: '80%',
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: 40,
    margin: 5,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  buttonCreate: {
    alignItems: 'center',
    justifyContent: 'center',
    width: '60%',
    height: 45,
    margin: 40,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  body: {
    alignItems: 'center',
    flex: 1,
  },
  space: {
    marginVertical: 25,
  },
  selected: {
    backgroundColor: "hsl(85, 100%, 50%)"
  },
  list: {
    height: '52%',
    width: '100%',
  },
});


export default EditEvent;
