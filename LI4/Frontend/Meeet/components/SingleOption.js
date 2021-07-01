import React from 'react'
import {
  StyleSheet,
  View,
  Text,
  Image,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'


function SingleOption({ opcao , userOpcoes, index}) {
  var selected = false;
  for(var i = 0; i < userOpcoes.length; i++) {
    if (userOpcoes[i].idOpcao == opcao.idOpcao) {
        selected = true;
        break;
      }
    }
    return (
      <View style = {{backgroundColor: selected ? '#4b6937' : '#9c3d3d', margin: 5, borderRadius:10, overflow:'hidden'}}>
        <View style={{height: 50, width: 50, alignItems: 'center', justifyContent: 'center'}}>
          <Text>{index+1}</Text>
        </View>
      </View>
    )
}


export default SingleOption;
