import React, { Component , useState, useEffect} from 'react'
import { View, Image, TouchableOpacity, Alert, Text, StyleSheet, ActivityIndicator,Modal, TextInput, Button, ActionSheetIOS} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import DateTimePickerModal from "react-native-modal-datetime-picker";
import ImagePicker from 'react-native-image-picker';
import { format } from "date-fns";
import { useSafeArea } from 'react-native-safe-area-context';

function Profile() {


   const [state, setState] = React.useState({resourcePath: {}});
   const [userData, setUserData] = useState(null);
   const [isLoading, setLoading] = useState(true);
   const [user, setUser] = useState(null);
   const [city, setCity] = useState(null);
   const [date, setDate] = useState(null);
   const [bool,setBool] = useState(false);
   const [bool2,setBool2] = useState(true);
   const [bool3,setBool3] = useState(false);
   const [isDatePickerVisible, setDatePickerVisibility] = useState(false);
   const [data,setData] = useState(null);
   const [url,setUrl] = useState(null);
   const [modalVisible, setModalVisible] = React.useState(false);
   const [bool4, setBool4] = useState(false);
   const [bool5, setBool5] = useState(false);




   useEffect(() => {
     fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUser/' + global.userID)
       .then((response) => response.json())
       .then((json) => {
         setUserData(json);
       })
       .catch((error) => {
         console.error(error);
       })
       .finally(() => { setLoading(false) } );
     }, [bool3]);




     React.useEffect(() => {
       bool2 ? setBool2(false) :
      setUser({
        "id": userData.id,
        "username": userData.username,
        "email": userData.email,
        "password": userData.password,
        "longitude": userData.longitude,
        "latitude": userData.latitude,
        "urlFoto": url ? url : userData.urlFoto,
        "morada": city ? city : userData.morada,
        "dataNascimento": date ? date : userData.dataNascimento,
        "genero": userData.genero,
        "bio": userData.bio,
        "amigosIdUser1Navigation":userData.amigosIdUser1Navigation,
        "amigosIdUser2Navigation": userData.amigosIdUser2Navigation,
        "evento": userData.evento,
        "utilizadorConvites":userData.utilizadorConvites,
        "utilizadorEvento": userData.utilizadorEvento,
        "utilizadorGrupo": userData.utilizadorGrupo,
        "utilizadorOpcao": userData.utilizadorOpcao,
        "utilizadorPedidosAmizade": userData.utilizadorPedidosAmizade,
    });
    },[date,city,url]);


   async function updateProfile() {
      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/updateuser/' + global.userID, {
      method: 'PUT',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
      
    })
    .catch((error) => {
      console.error(error);
    });
    createWarning();
    restart();
  };


  function restart() {
    setCity(null);
    setDate(null);
    setBool3(!bool3);
  };

  const createWarning = () =>
    Alert.alert(
      "Save sucessufly!",
      "",
      [{ text: "OK" }],
      { cancelable: false }
    );

  const selectFile = () => {
    var options = {
      title: 'Select Image',
      customButtons: [
        { name: 'customOptionKey', title: 'Choose URL...' },
      ],
      storageOptions: {
        skipBackup: true,
        path: 'images',
      },
    };


    ImagePicker.showImagePicker(options, res => {
      if (res.didCancel) {
        console.log('User cancelled image picker');
      } else if (res.error) {
        console.log('ImagePicker Error: ', res.error);
      }  else if (res.customButton) {
        setModalVisible(true);
      } else {
        let source = res;
        setState({resourcePath: source});
        setBool(true);
      }
    });
  };


    const showDatePicker = () => {
      setDatePickerVisibility(true);
    };

    const hideDatePicker = () => {
      setDatePickerVisibility(false);
    };

    const handleConfirm = (date) => {
      hideDatePicker();
      setDate(date);
    };




    return (
      <>
      {isLoading ? <ActivityIndicator/> : (
        <>

        <View style = {styles.profilePic}>
        <TouchableOpacity onPress={() => selectFile()}>
          <Image style = {{width: '100%', height:'100%' , borderRadius:20000}}
            source={{
                uri: bool4 ? url : bool ? state.resourcePath.uri : userData.urlFoto ,
            }}
            />
            </TouchableOpacity>

        </View>


        <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        >
        <View style={styles.centeredView}>
       <View style={styles.modalView}>
        <Text style={styles.modalText}>Insert URL</Text>

        <View style={styles.profileRow}>
        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          onChangeText={setUrl}
          value={url}
        />
        </View>


        <TouchableOpacity
           style={styles.openButtonFinal}
           onPress={() => {setModalVisible(!modalVisible);setBool4(true);}}>
            <Text style={styles.textStyle}>Save</Text>
           </TouchableOpacity>


          <TouchableOpacity
           style={styles.openButtonFinal}
           onPress={() => {setModalVisible(!modalVisible);}}>
            <Text style={styles.textStyle}>Go Back</Text>
           </TouchableOpacity>
            </View>
           </View>
         </Modal>





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
          <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder={userData.morada}
          onChangeText={setCity}
          value={city}
          />
            </View>
        </View>


        <View style = {styles.profileRow}>

        <Icon
          name="calendar-alt"
          size={20}
          color='#2c365d'
          />

          <View style={styles.textInput}>
          <TouchableOpacity onPress={showDatePicker}>
            <Text style={{color: date ? '#0B0B0B' : '#8E9290'}}>
              {date ? format(date, "yyyy-MM-dd") :  userData.dataNascimento.substring(0,10)}
            </Text>
          </TouchableOpacity>

          <DateTimePickerModal
              isVisible={isDatePickerVisible}
              mode="date"
              onConfirm={handleConfirm}
              onCancel={hideDatePicker}
           />

        </View>

        </View>




        <View style = {styles.buttons}>

      <TouchableOpacity style={styles.button2} onPress={() => updateProfile()}>
        <Text style= {{color: '#fbfbfb'}}>
          Save
          </Text>
        </TouchableOpacity>

      </View>

         </>
      )}
    </>
      );
    };


const styles = StyleSheet.create({
  button: {
    width: 250,
    height: 60,
    backgroundColor: '#3740ff',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 4,
    marginBottom:12
  },
  buttonText: {
    textAlign: 'center',
    fontSize: 15,
    color: '#fff'
  },
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
    width: '35%',
    height: '22%',
    marginVertical: 22,
  },
  profileRow:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  textStyle: {
    color: "white",
    fontWeight: "bold",
    textAlign: "center"
  },
  buttons: {
    alignItems: 'center',
    marginTop: 50,
    width: '40%',
  },
  button2: {
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: 40,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  openButtonFinal: {
    alignItems: 'center',
    borderRadius: 20,
    height: 40,
    width: 90,
    padding: 10,
    elevation: 2,
    backgroundColor: "#2196F3" ,
    marginTop: 30,
  },
  modalText: {
    marginBottom: 15,
    textAlign: "center",
    fontSize: 18,
    fontWeight: "bold",
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
});

export default Profile;
