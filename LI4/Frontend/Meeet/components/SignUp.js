import React, { Component } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'
import DateTimePickerModal from "react-native-modal-datetime-picker";

import AuthContext from '../contexts/AuthContext'

function SignUp({navigation}) {

  const [usernameText, setUsername] = React.useState('');
  const [emailText, setEmail] = React.useState('');
  const [passwordText, setPassword] = React.useState('');
  const [cityText, setCity] = React.useState('');
  const [isDatePickerVisible, setDatePickerVisibility] = React.useState(false);

  const [user, setUser] = React.useState(null);

  React.useEffect(() => {

    setUser({
      "username": usernameText,
      "email": emailText,
      "password": passwordText,
      "longitude": 0,
      "latitude": 0,
      "urlFoto": "https://i0.wp.com/ipc.digital/wp-content/uploads/2016/07/icon-user-default.png?fit=462%2C462&ssl=1",
      "morada": cityText,
      "dataNascimento": "2014-01-01",
      "genero": null,
      "bio": null,
      "amigo": null,
      "evento": null,
      "utilizadorConvites": null,
      "utilizadorEvento": null,
      "utilizadorGrupo": null,
      "utilizadorOpcao": null,
      "utilizadorPedidosAmizade": null
  });

  },[usernameText, emailText, passwordText, cityText]);


  const { signUp } = React.useContext(AuthContext);

  const showDatePicker = () => {
    setDatePickerVisibility(true);
  };

  const hideDatePicker = () => {
    setDatePickerVisibility(false);
  };

  const handleConfirm = (date) => {
    hideDatePicker();
  };

  return (

    <View style = {styles.SignUp}>

      <View style = {styles.signupInput}>

        <Icon
          name="user"
          size={20}
          color='#2c365d'
          />
        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder='Username'
          onChangeText={setUsername}
          value={usernameText}
          />

        </View>

        <View style = {styles.signupInput}>

        <Icon
          name="envelope"
          size={20}
          color='#2c365d'
          />
        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder='Email'
          onChangeText={setEmail}
          value={emailText}
          />

        </View>
      <View style = {styles.signupInput}>

        <Icon
          name="key"
          size={20}
          color='#2c365d'
          />
        <TextInput
          secureTextEntry={true}
          style={styles.textInput}
          textAlign={'center'}
          placeholder='Password'
          onChangeText={setPassword}
          value={passwordText}
          />

      </View>
      <View style = {styles.signupInput}>

        <Icon
          name="map"
          size={20}
          color='#2c365d'
          />
        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder='City'
          onChangeText={setCity}
          value={cityText}
          />

        </View>

        <DateTimePickerModal
            isVisible={isDatePickerVisible}
            mode="date"
            onConfirm={handleConfirm}
            onCancel={hideDatePicker}
         />

    <View style = {styles.buttons}>

      <TouchableOpacity style={styles.button} onPress={() => {signUp(user); navigation.navigate('Login');}}>
        <Text style= {{color: '#fbfbfb'}}>
          SignUp
          </Text>
        </TouchableOpacity>

      </View>

    </View>
    )
}

const styles = StyleSheet.create({
  textInput: {
    margin: 10,
    marginHorizontal: 5,
    backgroundColor: '#cbcbcb',
    height: 40,
    width: 200,
    fontSize: 16,
    borderRadius: 10,
  },
  signupInput:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  SignUp: {
    flex: 1,
    alignItems: 'center',
  },
  buttons: {
    flexDirection: 'row',
    marginTop: 20,
  },
  button: {
    marginHorizontal: 10,
    alignItems: 'center',
    justifyContent: 'center',
    width: '30%',
    height: 40,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  }
});

export default SignUp;
