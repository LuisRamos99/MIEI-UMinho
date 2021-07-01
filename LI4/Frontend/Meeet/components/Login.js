import React, { Component } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity} from 'react-native'
import { useNavigation } from '@react-navigation/native';

import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import AuthContext from '../contexts/AuthContext'

function Login({navigation}) {

  const [usernameText, setUsername] = React.useState('');
  const [passwordText, setPassword] = React.useState('');

  const { signIn } = React.useContext(AuthContext);

  return (

    <View style = {styles.login}>

      <View style = {styles.loginInput}>

        <Icon
          name="user"
          size={20}
          color='#2c365d'
          />
        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          onChangeText={setUsername}
          value={usernameText}
          />

        </View>
      <View style = {styles.loginInput}>

        <Icon
          name="key"
          size={20}
          color='#2c365d'
          />
        <TextInput
          secureTextEntry={true}
          style={styles.textInput}
          textAlign={'center'}
          onChangeText={setPassword}
          value={passwordText}
          />

      </View>

    <View style = {styles.buttons}>

      <TouchableOpacity style={styles.button}  onPress={() => signIn({username: usernameText, password: passwordText})}>
        <Text style= {{color: '#fbfbfb'}}>
          Login
          </Text>
        </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={() => navigation.navigate("SignUp")}>
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
  loginInput:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  login: {
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

export default Login;
