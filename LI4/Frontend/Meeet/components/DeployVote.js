import React, {useState, useEffect} from 'react'
import {
  StyleSheet,
  View,
  Text,
  Image,
  TouchableOpacity,
  ActivityIndicator,
} from 'react-native';


import SingleVote from './SingleVote'


function DeployVote({ opcoes, votacao }) {

  const [userOpIds, setUserOpIds] = React.useState(null);
  const [userOps, setUserOps] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isMounted, setIsMounted] = useState(false);


  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getOpcoesPerUserVotacao/' + global.userID + '/' + votacao.idVotacao, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setUserOpIds(json);
    })
    .catch((error) => {
      console.error('ERROR:' + error);
    });
  }, []);

  useEffect(() => {
    if(userOpIds != null){
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getOpcoesPerIDs', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userOpIds)
    })
    .then((response) => {return response.json()} )
    .then((json) => {
      setUserOps(json);
    })
    .catch((error) => {
      console.error(error);
    });
    }
  }, [userOpIds]);

  useEffect(() => {
    if(isMounted)
      setIsLoading(false);
    else setIsMounted(true);
  }, [userOps]);

  return (
    <View style={styles.textSide}>
      { isLoading ? <ActivityIndicator/> :
      (opcoes.map((op,i) => { return (
        <SingleVote opcao={op} index={i} userOpcoes={userOps} key={op.idOpcao.toString()}/>
      ); }))
      }
    </View>
  )
}

const styles = StyleSheet.create({
  textSide: {

      flexDirection: 'row',
      alignItems:"center",
      justifyContent:"center"
  },
  });

export default DeployVote;
