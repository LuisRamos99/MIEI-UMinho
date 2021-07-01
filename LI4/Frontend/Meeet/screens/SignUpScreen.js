import React, { Component } from 'react'
import {
  StyleSheet,
  View,
  Text,
  Button,
  Image,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import SignUp from '../components/SignUp'
import AuthContext from '../contexts/AuthContext'

function SignUpScreen({navigation}) {

    const { signIn } = React.useContext(AuthContext);
    return (

      <View style = {styles.signupBox}>

        <Image style = {styles.logo} source={require('../assets/meeetLogo2.png')} />
        <View
          style={{
            borderColor: '#2c365d',
            borderWidth: 2,
            width: '70%',
            marginBottom: 50,
            }}
          />
        <SignUp navigation={navigation}/>

      </View>

    )
}

const styles = StyleSheet.create({
  signupBox: {
    flex : 1,
    alignItems : 'center',
    backgroundColor:'#ebebeb',
  },
  logo: {
    resizeMode: 'contain',
    width: '100%',
    height: '40%',
  },
  });

export default SignUpScreen;
