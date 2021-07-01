import React, {useState, useEffect} from 'react'
import {
  StyleSheet,
  View,
  Text,
  Image,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import SingleOption from '../components/SingleOption'

function UserVote({ user, votacao, opcoes }) {
  const [userOpIds, setUserOpIds] = React.useState([]);
  const [userOps, setUserOps] = React.useState([]);
  const [isLoading, setIsLoading] = useState(true);


  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getOpcoesPerUserVotacao/' + user.id + '/' + votacao.idVotacao, {
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
  }, [userOpIds]);

  useEffect(() => {
    setIsLoading(false);
  }, [userOps]);

    return (
      <View style = {styles.userVote}>
        <Image style = {styles.pic}
          source={{
            uri: user.urlFoto,
          }}
        />

        <View style = {styles.optionsBox}>
          {
            opcoes.map((i, index) => <SingleOption key={i.idOpcao} opcao={i} userOpcoes={userOps} index={index}/>)
          }
        </View>
      </View>
    )
}

const styles = StyleSheet.create({
  userVote: {
    flexDirection : 'row',
    alignItems: 'center',
    margin: 10,
  },
  pic: {
    width: '18%',
    height:'100%' ,
    borderRadius:20000,
    borderColor: '#2c365d',
    borderWidth: 1,
    marginRight: 10,
  },
  optionsBox: {
    flexDirection : 'row',
    height: '100%',
    width: '100%',
  },
  text: {
    fontSize: 25,
    color: '#2c365d',
  },
  line: {
    borderColor: '#2c365d',
    borderWidth: 2,
    width: '70%',
    marginBottom: 20,
  },
  centerLine: {
    alignItems: 'center',
  },
  });

export default UserVote;
