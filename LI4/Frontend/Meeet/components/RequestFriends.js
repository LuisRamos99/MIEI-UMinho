import React, { Component , useState, useEffect } from 'react'
import { View, Text, StyleSheet, TextInput, TouchableOpacity, Image,Modal,Alert,ActivityIndicator} from 'react-native'

import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import SearchBar from '../components/SearchBar';
import AuthContext from '../contexts/AuthContext'


function RequestFriends({ navigation }) {

  const [modalVisible, setModalVisible] = React.useState(false);
  const [username, setUsername] = React.useState('');
  const [user, setUser] = useState(null);
  const [isBadUser, setBadUser] = useState(true);
  const [isFriend, setIsFriend] = useState(null);
  const [isLoading, setLoading] = useState(false);
  const [newUser, setNewUser] = useState(false);



async function findUser() {
  setLoading(true);
  fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserByName/' + username)
  .then((response) =>  {if(response.status == 200) return (response.json()); else return null;})
  .then((json) => {
    if( json == null ){ setBadUser(true); setLoading(false);}
    else{
      setUser(json);
      setNewUser(true)
    }
  })
  .catch((error) => {
    console.error(error);
  });
};

async function isFriends() {
  setLoading(true);
  fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getSaoAmigos/' + global.userID + '/' + user.id)
  .then((response) =>  {if(response.status == 200) return (response.json()); else return null;})
  .then((json) => {
      setIsFriend(json);
  })
  .catch((error) => {
    console.error(error);
  });
};

useEffect(() => {
  if(user != null){
    isFriends();
  }
},[user]);

useEffect(() => {
  if(isFriend != null){
    setLoading(false);
    setBadUser(false);
  }
  if(newUser == true) setNewUser(false);
},[isFriend , newUser]);

async function sendRequest(id){
  const data = {
    idUserSend: global.userID,
    utilizadorPedidosAmizade:null
  };
  fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostPedidoAmizade/' + id , {
    method: 'POST',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
}

const createWarning = () => {
    Alert.alert(
      "Request send sucessufly!",
      "",
      [{ text: "OK" }],
      { cancelable: false }
    );
    }

  return (

    <View style={styles.container}>

        <Modal
          animationType="slide"
          transparent={true}
          visible={modalVisible}
        >
        {!isLoading ? ( !isBadUser ? (
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Image style={styles.userImageModal} source={{uri:user.urlFoto}}/>
            <Text style={styles.modalText}>{user.username}</Text>
             <TouchableOpacity
               style={styles.openButton}
               onPress={() => {setModalVisible(!modalVisible);navigation.navigate('FriendProfile',{id: user.id})}}>
                <Text style={styles.textStyle}>View Profile</Text>
              </TouchableOpacity>
              {isFriend ? (<></>) : (<TouchableOpacity
               style={styles.openButton}
               onPress={() => {setModalVisible(!modalVisible);sendRequest(user.id)}}>
                <Text style={styles.textStyle}>Send Request</Text>
              </TouchableOpacity>)}
              <TouchableOpacity
               style={styles.openButtonFinal}
               onPress={() => {setModalVisible(!modalVisible); setIsFriend(null)}}>
                <Text style={styles.textStyle}>Go Back</Text>
              </TouchableOpacity>
           </View>
         </View> ) : (

           <View style={styles.centeredView}>
             <View style={styles.modalView}>
                 <Text> Couldn't find user. </Text>
                 <TouchableOpacity
                  style={styles.openButtonFinal}
                  onPress={() => {setModalVisible(!modalVisible);}}>
                   <Text style={styles.textStyle}>Go Back</Text>
                 </TouchableOpacity>
              </View>
            </View>)

          ) : (

         <ActivityIndicator/>)
       }
        </Modal>

        <View style={styles.body}>
          <View style={styles.inputContainer}>
            <TextInput
              placeholder="Type Here..."
              textAlign={'center'}
              onChangeText={setUsername}
              value={username}
            />
          </View>
          <TouchableOpacity style={styles.button} onPress={() => {findUser(); setModalVisible(!modalVisible); }}>
            <Icon
              name="user-plus"
              size={30}
              color='#2c365d'
            />
          </TouchableOpacity>

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
    flex:1,
  },
  container:{
    flex:1,
    paddingTop: 50,
    flexDirection: 'row',
  },
  inputContainer: {
    width: '65%',
    backgroundColor: '#cbcbcb',
    borderRadius: 40,
  },
  button: {
    paddingTop: 20,
  },
  userImage:{
    height: 40,
    width: 40,
    borderRadius:80,
    backgroundColor: '#fefefe',
    borderColor:"#DCDCDC",
    borderWidth:1.5,
  },
  userImageModal:{
    height: 140,
    width: 140,
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
  }
  });

export default RequestFriends;
