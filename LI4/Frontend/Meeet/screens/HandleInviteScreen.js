import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, FlatList, Button, TouchableOpacity, Image, Modal, ActivityIndicator} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'
import AuthContext from '../contexts/AuthContext'
import TouchableSearchList from '../components/TouchableSearchList'

function HandleRequestScreen({navigation}) {
  const [selectedList, setList] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [idRequests, setIdRequests] = React.useState([]);
  const [requests, setRequests] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [modalEvent, setModalEvent] = useState(null);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getAllUserConvites/' + global.userID, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setIdRequests(json.map((value) => {
          return value.idEvento;
      }));
    })
    .catch((error) => {
      console.error('ERROR:' + error);
    });
  }, []);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getEventsPerIDs', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(idRequests)
    })
    .then((response) => response.json())
    .then((json) => {
      setModalEvent(json[0])
      setRequests(json);
      setIsLoading(false);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [idRequests]);

  async function acceptRequest() {
      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/AddToEvent/' + global.userID, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(modalEvent)
    })
    .catch((error) => {
      console.error(error);
    });
    eraseRequest();
  };

  async function eraseRequest() {
    const data = { idUser:global.userID, idConvidador: modalEvent.idAdmin, idEvento:modalEvent.id, id: null, idUserNavigation:null }
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserSingleConvite', {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
    .catch((error) => {
      console.error(error);
    });
    deleteItemById(modalEvent.id);
  };

  function deleteItemById(id) {
    const filteredData = requests.filter(item => item.id !== id);
    setRequests(filteredData);
  }

  return (
    <View style = {styles.background}>
    <Title title = {'Requests'}/>
    <View style = {styles.body}>
    <View style={styles.container}>

      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
      >
      {requests.length>0 ? (<View style={styles.centeredView}>
        <View style={styles.modalView}>
          <Text style={styles.modalText}>{modalEvent.nome}</Text>
           <TouchableOpacity
            style={styles.openButton}
             onPress={() => {setModalVisible(!modalVisible);navigation.navigate('CheckEvent',{evento:modalEvent})}}>
              <Text style={styles.textStyle}>View Event</Text>
            </TouchableOpacity>
            <TouchableOpacity
             style={styles.openButton}
             onPress={() => {setModalVisible(!modalVisible); acceptRequest();}}>
             <Text style={styles.textStyle}>Accept Request</Text>
            </TouchableOpacity>
            <TouchableOpacity
             style={styles.openButton}
             onPress={() => {setModalVisible(!modalVisible); eraseRequest();}}>
             <Text style={styles.textStyle}>Deny Request</Text>
            </TouchableOpacity>
            <TouchableOpacity
             style={styles.openButtonFinal}
             onPress={() => {setModalVisible(!modalVisible);}}>
              <Text style={styles.textStyle}>Go Back</Text>
            </TouchableOpacity>
          </View>
        </View>) : (<></>)}
      </Modal>

    </View>

      {isLoading ? <ActivityIndicator/> : (
        <>
        <View style = {styles.list}>

        <View style={styles.touchlist}>
          <FlatList
            data={requests}
            renderItem={({ item }) => (
              <TouchableOpacity
                style={styles.itemPress}
                onPress={() => { setModalEvent(item); setModalVisible(true); }}>
                  <View style={{flexDirection: "row"}}>
                    <Icon
                      name="calendar-alt"
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

      <View style = {styles.buttons}>

        <TouchableOpacity style={styles.button} onPress={() => createGroup()}>
          <Text style= {{color: '#fbfbfb'}}>
            Create
            </Text>
          </TouchableOpacity>

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

export default HandleRequestScreen;
