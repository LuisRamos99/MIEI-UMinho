import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, TextInput, Button, TouchableOpacity, Image, ActivityIndicator, FlatList} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'
import AuthContext from '../contexts/AuthContext'
import TouchableSearchList from '../components/TouchableSearchList'

function InviteGroupScreen( { route, navigation } ) {
  const [group, setGroup] = React.useState({id: -1});
  const [convite, setConvite] = useState(null);

  const { idEvento } = route.params;

  const [userGroups, setUserGroups] = React.useState([]);
  const [groups, setGroups] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);


  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserGruposPerUser/' + global.userID, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setUserGroups(json);
    })
    .catch((error) => {
      console.error('ERROR:' + error);
    });
  }, []);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getGrupoPerUser', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userGroups)
    })
    .then((response) => {return response.json()} )
    .then((json) => {
      setGroups(json);
      setIsLoading(false);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [userGroups]);

  React.useEffect(() => {
      if(convite != null) selectedList.forEach(inviteToEvent);
  },[convite]);

  async function inviteToEvent(user, i){
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/InviteToEvent/' + user, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(convite)
    })
    .catch((error) => {
      console.error(error);
    });
  }

  return (
    <View style = {styles.background}>
    <Title title = {'Invite Groups'}/>
    <View style = {styles.body}>

      {isLoading ? <ActivityIndicator/> : (
        <>
        <View style = {styles.list}>

        <View style={styles.touchlist}>
          <FlatList
            data={groups}
            renderItem={({ item }) => (
              <TouchableOpacity
                style={(group.id == item.id) ? styles.itemPress : styles.itemNotPress}
                onPress={() => { setGroup(item) }}>
                  <View style={{flexDirection: "row"}}>
                    <Icon
                      name="users"
                      size={35}
                      color='#2c365d'
                    />
                    <Text style={styles.text}>
                      {item.nome}
                    </Text>
                  </View>
                </TouchableOpacity>

              )}
              keyExtractor={item => item.id.toString()}
            />
            </View>
            <View style={styles.buttoncont}>
            <View style = {styles.buttons}>

              <TouchableOpacity style={styles.button} onPress={() => { navigation.navigate('SendGroupInvite', {grupo: group, idEvento: idEvento}); }}>
                <Text style= {{color: '#fbfbfb'}}>
                  Create
                  </Text>
                </TouchableOpacity>

              </View>
            </View>

      </View>
      </>
      )}
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
    selected: {
        backgroundColor: "hsl(85, 100%, 50%)"
      },
    list: {
      height: '80%',
      width: '100%',
    },
    buttons: {
      alignItems: 'center',
      marginTop: 10,
      width: '50%',
    },
    button: {
      alignItems: 'center',
      justifyContent: 'center',
      width: '100%',
      height: 50,
      borderRadius: 10,
      backgroundColor: '#2c365d',
    },
    background: {
        flex : 1,
        backgroundColor:'#ebebeb',
      },
    buttoncont:{
      alignItems: 'center',
    },
    container:{
      flex:1,
      paddingTop: 50,
      flexDirection: 'row',
    },
    body: {
        alignItems: 'center',
        flex: 1,
      },
    centeredView: {
      flex: 1,
      justifyContent: "center",
      alignItems: "center",
      marginTop: 22
    },
    modalView: {
      margin: 20,
      backgroundColor: "white",
      borderRadius: 20,
      padding: 35,
      alignItems: "center",
      shadowColor: "#000",
      shadowOffset: {
        width: 0,
        height: 2
      },
      shadowOpacity: 0.25,
      shadowRadius: 3.84,
      elevation: 5
    },
    openButton: {
      backgroundColor: "#F194FF",
      borderRadius: 20,
      height: 40,
      width: 140,
      padding: 10,
      elevation: 2,
      backgroundColor: "#2196F3" ,
      marginTop: 10,
    },
    openButtonFinal: {
      backgroundColor: "#F194FF",
      borderRadius: 20,
      height: 40,
      width: 90,
      padding: 10,
      elevation: 2,
      backgroundColor: "#2196F3" ,
      marginTop: 30,
    },
    textStyle: {
      color: "white",
      fontWeight: "bold",
      textAlign: "center"
    },
    modalText: {
      marginBottom: 15,
      textAlign: "center",
      fontWeight:'bold'
    },
    list: {
      height: '95%',
      width: '100%',
    },
    userImage:{
      height: 40,
      width: 40,
      borderRadius:80,
      backgroundColor: '#fefefe',
      borderColor:"#DCDCDC",
      borderWidth:1.5,
    },
    text: {
      marginTop:7,
      marginLeft: 13,
      fontSize:18,
      flex:1,
      fontWeight:'bold'
    },
    itemPress: {
      backgroundColor: '#C78B50',
      padding: 10,
      marginBottom: 16,
      marginHorizontal: 16,
      borderRadius: 10,
    },
    itemNotPress: {
      backgroundColor: '#efefef',
      padding: 10,
      marginBottom: 16,
      marginHorizontal: 16,
      borderRadius: 10,
    },
    userImageModal:{
      height: 140,
      width: 140,
      borderRadius:80,
      backgroundColor: '#fefefe',
      borderColor:"#DCDCDC",
      borderWidth:1.5,
    },
  });

export default InviteGroupScreen;
