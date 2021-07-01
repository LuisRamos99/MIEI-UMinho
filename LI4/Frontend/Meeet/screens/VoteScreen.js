import { View, StyleSheet, SafeAreaView, Text,FlatList, ActivityIndicator, TouchableOpacity} from 'react-native'
import * as React from 'react';

import EventContext from '../contexts/EventContext'

import Title from '../components/Title'
import UserVote from '../components/UserVote'
import DeployVote from '../components/DeployVote'


function VoteScreen({ route }) {
  const { evento } = React.useContext(EventContext);
  const { votacao } = route.params
  const [opcoes, setOpcoes] = React.useState([]);
  const [userEvents, setUserEvents] = React.useState([]);
  const [users, setUsers] = React.useState([]);
  const [isLoading, setIsLoading] = React.useState(true);
  const [opcoesLoading, setOpcoesLoading] = React.useState(true);

  React.useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getOpcaoPerEventVotacao/' + evento.id + '/' + votacao.idVotacao, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setOpcoes(json);
    })
    .catch((error) => {
      console.error('ERROR:' + error);
    });
  }, []);

  React.useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUserEventosPerEvent/' + evento.id, {
        method: 'GET',
        headers: {
        "Accept": "application/json",
        'Content-Type': 'application/json'
        }
    })
    .then(response => { return response.json(); } )
    .then(json => {
      setUserEvents(json);
    })
    .catch((error) => {
      console.error('ERROR:' + error);
    });
  }, []);

  React.useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getUsersPerEvent', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userEvents)
    })
    .then((response) => { return response.json() } )
    .then((json) => {
      setUsers(json);
    })
    .catch((error) => {
      console.error(error);
    });
  }, [userEvents]);

  React.useEffect(() => {
    if(users.length > 0)
      setIsLoading(false);
  }, [users]);

  React.useEffect(() => {
    if(opcoes.length > 0)
      setOpcoesLoading(false);
  }, [opcoes]);

return (
    <View style = {styles.background}>
        <Title title = {'Vote'}/>
        {(isLoading || opcoesLoading) ? <ActivityIndicator/> :
        (<View style = {styles.body}>
        {
          opcoes.map((op, i) => {return (<Text key={op.opcao1}> { 'Opção ' + (i+1) + ' | ' + op.opcao1 } </Text>)})
        }


          <SafeAreaView style={{...styles.container,marginTop:20,height:'65%'}}>

              <FlatList
              data={users}
              renderItem={({ item }) => <UserVote key={item.id} user= {item} votacao={votacao} opcoes={opcoes} />}
              keyExtractor={item => item.id.toString()}
            />
          </SafeAreaView>

          <DeployVote opcoes={opcoes} votacao={votacao}/>

        </View>)}
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
        flex: 1,
      },
    textSide: {
        marginTop:20,
        flexDirection: 'row',
        alignItems:"center",
        justifyContent:"center"
    },
  });




export default VoteScreen;
