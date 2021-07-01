import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, FlatList, Button, TouchableOpacity, Image, Modal, ActivityIndicator} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'


function HandleRequestScreen({navigation}) {
  const [selectedList, setList] = useState([]);
  const [groupName, updateGroupName] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [group, setGroup] = React.useState(null);
  const [idRequests, setIdRequests] = React.useState([]);
  const [requests, setRequests] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [modalUser, setModalUser] = useState({urlFoto: '', username: ''});

  React.useEffect(() => {
    setGroup({
      "nome": groupName,
      "utilizadorGrupo": null,
  });
  },[groupName]);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserPedidosAmizade/' + global.userID, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setIdRequests(json);
    })
    .catch((error) => {
      console.error('ERROR:' + error);
    });
  }, []);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerIDs', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(idRequests)
    })
    .then((response) => response.json())
    .then((json) => {
      setModalUser(json[0])
      setRequests(json);
      setIsLoading(false);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [idRequests]);


  async function acceptRequest() {
    const data = { idUser1: global.userID , idUser2:modalUser.id, idUser1Navigation:null , idUser2Navigation:null }
      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostAmigos', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
    .catch((error) => {
      console.error(error);
    });
    eraseRequest();
  };

  async function eraseRequest() {
    const data = { idReceive: global.userID , idSend:modalUser.id, idReceiveNavigation:null , idSendNavigation:null }
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteUserPedidosAmizade', {
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
    deleteItemById(modalUser.id)
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
          <Image style={styles.userImageModal} source={{uri:modalUser.urlFoto}}/>
          <Text style={styles.modalText}>{modalUser.username}</Text>
           <TouchableOpacity
            style={styles.openButton}
             onPress={() => {setModalVisible(!modalVisible);navigation.navigate('FriendProfile',{id:modalUser.id})}}>
              <Text style={styles.textStyle}>View Profile</Text>
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
                onPress={() => { setModalUser(item); setModalVisible(true); }}>
                  <View style={{flexDirection: "row"}}>
                  <Image style={styles.userImage} source={{uri:item.urlFoto}}/>
                  <Text style={styles.text}>
                    {item.username}
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
      textAlign: "center"
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

  const data = [
    {
      id: '1',
      username: 'Joaquim Silva Silva',
      image:"https://bootdey.com/img/Content/avatar/avatar7.png",
    },
    {
      id: '2',
      username: 'Ricardo Esteves Esteves',
      image:"https://bootdey.com/img/Content/avatar/avatar7.png",
    },
    {
      id: '3',
      username: 'Ricardinho',
    },
    {
      id: '9',
      username: 'Rui Costa',
    },
    {
      id: '4',
      username: 'Rivaldo Esteves Esteves',
    },
    {
      id: '5',
      username: 'Paulo Jorge Jorge',
    },
    {
      id: '6',
      username: 'Joaquim Silva Silva',
    },
    {
      id: '7',
      username: 'Ricardo Esteves Esteves',
    },
    {
      id: '8',
      username: 'Paulo Jorge Jorge',
    },
    {
      id: '10',
      username: 'Joaquim Silva Silva',
    },
    {
      id: '11',
      username: 'Ricardo Esteves Esteves',
    },
    {
      id: '12',
      username: 'Paulo Jorge Jorge',
    },
    {
      id: '13',
      username: 'Joaquim Silva Silva',
    },
    {
      id: '14',
      username: 'Ricardo Esteves Esteves',
    },
    {
      id: '15',
      username: 'Paulo Jorge Jorge',
    },
    {
      id: '16',
      username: 'Joaquim Silva Silva',
    },
    {
      id: '17',
      username: 'Ricardo Esteves Esteves',
    },
    {
      id: '18',
      username: 'Paulo Jorge Jorge',
    },
  ];

export default HandleRequestScreen;
