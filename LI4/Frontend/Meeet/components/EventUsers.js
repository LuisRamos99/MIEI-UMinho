import React, { Component, useState, useEffect } from 'react';
import {StyleSheet,Text,View,TouchableOpacity,Image,FlatList,Modal,ActivityIndicator} from 'react-native';
import EventContext from '../contexts/EventContext'
import SearchBar from '../components/SearchBar';



function EventUsers({ data, navigation }) {
    const { evento } = React.useContext(EventContext);

    const [state, setState] = React.useState({ text: '' , list: data });
    const [modalVisible, setModalVisible] = React.useState(false);
    const [itemAtual, setItemAtual] = React.useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [isSharing, setIsSharing] = useState(false);
    const [isLocLoading, setIsLocLoading] = useState(true);

  let arrayholder = data;

  function searchFilterFunction(text){
    const newData = arrayholder.filter(item => {
      const itemData = `${item.username.toUpperCase()}
      ${item.username.toUpperCase()}`;

      const textData = text.toUpperCase();
      return itemData.indexOf(textData) > -1;
   });
    setState({text: state.text, list: newData });
  };

  useEffect(() => {
    if(modalVisible == false){
      setItemAtual(false);
    }
  },[modalVisible]);

  useEffect(() => {
    if(itemAtual){
      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getSharing/' + itemAtual.id + '/' + evento.id)
      .then((response) => { return response.json(); })
      .then((json) => {
         setIsSharing(json);
        setIsLocLoading(false);
      })
      .catch((error) => {
        console.error(error);
      });
    }
  },[itemAtual]);

  return (
        <View style={styles.container}>

        <Modal
          animationType="slide"
          transparent={true}
          visible={modalVisible}
        >
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Image style={styles.userImage} source={{uri:itemAtual.urlFoto}}/>
            <Text style={styles.modalText}>{itemAtual.username}</Text>
             <TouchableOpacity
               style={styles.openButton}
               onPress={() => {navigation.navigate('FriendProfile', {id: itemAtual.id}); setModalVisible(!modalVisible); }}>
                <Text style={styles.textStyle}>View Profile</Text>
              </TouchableOpacity>

              {isLocLoading ? <ActivityIndicator style={styles.loading}/> : (
                (isSharing == 1) ? (<TouchableOpacity
                  style={styles.openButton}
                  onPress={() => { navigation.navigate('CheckLocation',{id: itemAtual.id}); setModalVisible(false)}}>
                    <Text style={styles.textStyle}>Check Location</Text>
                </TouchableOpacity>) : (<></>))}

              <TouchableOpacity
               style={styles.openButtonFinal}
               onPress={() => {setModalVisible(!modalVisible);}}>
                <Text style={styles.textStyle}>Go Back</Text>
              </TouchableOpacity>
           </View>
         </View>
        </Modal>

        <SearchBar
          placeholder="Type Here..."
          filter={searchFilterFunction}
          onChangeText={state.text}
        />

        <FlatList style={styles.list}
          contentContainerStyle={styles.listContainer}
          data={state.list}
          horizontal={false}
          numColumns={2}
          keyExtractor= {(item) => {
            return item.id;
          }}
          renderItem={({item}) => {
            return (

              <TouchableOpacity style={styles.card} onPress={() => {setModalVisible(true);setItemAtual(item);}}>
                <Image style={styles.userImage} source={{uri:item.urlFoto}}/>
                <View style={styles.cardFooter}>
                  <View style={{alignItems:"center", justifyContent:"center"}}>
                    <Text style={styles.name}>{item.username}</Text>
                  </View>
                </View>
              </TouchableOpacity>

            )
          }}/>
      </View>
    );
  }



  const styles = StyleSheet.create({
    container:{
      flex:1,
      marginTop:20,
    },
    list: {
      paddingHorizontal: 5,
      backgroundColor:"#E6E6E6",
    },
    listContainer:{
     alignItems:'center'
    },
    card:{
      shadowColor: '#00000021',
      shadowOffset: {
        width: 0,
        height: 6,
      },
      shadowOpacity: 0.37,
      shadowRadius: 7.49,
      elevation: 12,

      marginVertical: 5,
      backgroundColor:"white",
      flexBasis: '46%',
      marginHorizontal: 5,
    },
    cardFooter: {
      paddingVertical: 17,
      paddingHorizontal: 16,
      borderTopLeftRadius: 1,
      borderTopRightRadius: 1,
      flexDirection: 'row',
      alignItems:"center",
      justifyContent:"center"
    },
    cardContent: {
      paddingVertical: 12.5,
      paddingHorizontal: 16,
    },
    userImage:{
      marginTop: 10,
      height: 120,
      width: 120,
      borderRadius:60,
      alignSelf:'center',
      borderColor:"#DCDCDC",
      borderWidth:3,
    },
    name:{
      fontSize:18,
      flex:1,
      alignSelf:'center',
      color:"#008080",
      fontWeight:'bold'
    },
    followButton: {
      marginTop:10,
      height:35,
      width:100,
      flexDirection: 'row',
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius:30,
      backgroundColor: "#00BFFF",
    },
    followButtonText:{
      color: "#FFFFFF",
      fontSize:20,
    },
    icon:{
      height: 20,
      width: 20,
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
      height: 40,
      width: 140,
      backgroundColor: "#F194FF",
      borderRadius: 20,
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
    loading: {
      marginTop: 15,
    }
  });

  export default EventUsers;
