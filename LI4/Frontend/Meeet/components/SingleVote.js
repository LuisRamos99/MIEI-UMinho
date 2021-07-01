import React, {useState, useEffect} from 'react'
import {
  StyleSheet,
  View,
  Text,
  Image,
  TouchableOpacity,
  ActivityIndicator
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'


function SingleVote({ opcao, index, userOpcoes }) {
  const [selected, setSelected] = React.useState(false);
  const [mounted, setMounted] = React.useState(false);
  const [isLoading, setIsLoading] = React.useState(true);

  React.useEffect(() => {
    let s = false
    for(var i = 0; i < userOpcoes.length; i++){
      if(opcao.idOpcao == userOpcoes[i].idOpcao){
        s = true;
      }
    }
    setSelected(s);
    setIsLoading(false)
  }, [])

  async function addOption(){
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/UserChooseOption/' + global.userID, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(opcao)
    })
    .catch((error) => {
      console.error(error);
    });
  }

  async function deleteOption(){
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/DeleteSingleUserOpcao/' + global.userID + '/' + opcao.idOpcao, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .catch((error) => {
      console.error(error);
    });
  }


  useEffect(() => {
    if(mounted){
      if(selected == true)
        addOption();
      else
        deleteOption();
    }
    else setMounted(true);
  }, [selected]);

    return (
      <>
      {isLoading ? <ActivityIndicator/> : (<TouchableOpacity onPress={() => selected ? setSelected(false) : setSelected(true)}>
      <View style = {{backgroundColor: selected ? '#4b6937' : '#9c3d3d' , margin: 5, borderRadius:10, overflow:'hidden'}}>
          <View style={{height: 50, width: 70, alignItems: 'center', justifyContent: 'center'}}>
           <Text>{'Opção ' + (index+1)} </Text>


          </View>
       </View>
       </TouchableOpacity>)}
       </>
    )
}

const styles = StyleSheet.create({
  textSide: {

      flexDirection: 'row',
      alignItems:"center",
      justifyContent:"center"
  },
  });

export default SingleVote;
