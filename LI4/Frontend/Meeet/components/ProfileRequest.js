import React, { Component , useState, useEffect } from 'react'
import { View, Image, Text, StyleSheet, ActivityIndicator, Button, TouchableOpacity} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import AuthContext from '../contexts/AuthContext'

function ProfileFriend({ id }) {

  const [userData, setUserData] = useState(null);
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    fetch('https://meeet-project.azurewebsites.net/api/meeet/getuser/' + id)
      .then((response) => response.json())
      .then((json) => {
        setUserData(json);
      })
      .catch((error) => {
        console.error(error);
      })
      .finally(() => setLoading(false));
    }, []);
  const { signIn } = React.useContext(AuthContext);

    return (
    <>
      {isLoading ? <ActivityIndicator/> : (
        <>
        <View style = {styles.profilePic}>
          <Image style = {{width: '100%', height:'100%' , borderRadius:20000}}
            source={{
          uri: userData.urlFoto,
        }} />
        </View>

      <View style = {styles.profileRow}>

        <Icon
          name="user"
          size={20}
          color='#2c365d'
          />
        <View style={styles.textInput}>
          <Text> {userData.username} </Text>
          </View>

        </View>

      <View style = {styles.profileRow}>

        <Icon
          name="envelope"
          size={20}
          color='#2c365d'
          />
        <View style={styles.textInput}>
          <Text> {userData.email} </Text>
          </View>

        </View>

      <View style = {styles.profileRow}>

        <Icon
          name="map"
          size={20}
          color='#2c365d'
          />
          <View style={styles.textInput}>
            <Text> {userData.morada} </Text>
            </View>

        </View>

    <View style = {styles.buttons}>

      <TouchableOpacity style={styles.button}>
        <Text style= {{color: '#fbfbfb'}}>
          Send Request
          </Text>
        </TouchableOpacity>

      </View>
      </>
      )}
    </>
  );
}

const styles = StyleSheet.create({
  textInput: {
    alignItems: 'center',
    justifyContent: 'center',
    margin: 10,
    marginHorizontal: 5,
    backgroundColor: '#cbcbcb',
    height: 40,
    width: '70%',
    borderRadius: 10,
  },
  profilePic:{
    width: '40%',
    height: '25%',
    marginVertical: 25,
  },
  profileRow:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  buttons: {
    alignItems: 'center',
    marginTop: 50,
    width: '40%',
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: 40,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  }
});

export default ProfileFriend;
