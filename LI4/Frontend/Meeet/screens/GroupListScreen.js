import React, { useState, useEffect } from 'react'
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ActivityIndicator} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'

function GroupListScreen({navigation}) {

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
    .then((response) => { return response.json()} )
    .then((json) => {
      setGroups(json);
      setIsLoading(false);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [userGroups]);



  return (
    <View style = {styles.background}>
    <Title title = {'Groups'}/>
    <View style = {styles.body}>

      {isLoading ? <ActivityIndicator/> : (
        <>
        <View style = {styles.list}>

        <View style={styles.touchlist}>
          <FlatList
            data={groups}
            renderItem={({ item }) => (
              <TouchableOpacity
                style={styles.itemPress}
                onPress={() => navigation.navigate('GroupUsers',{id: item.id})}>
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
      backgroundColor: '#fefefe',
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

export default GroupListScreen;
