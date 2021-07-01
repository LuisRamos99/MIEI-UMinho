import React, { Component , useState, useEffect } from 'react'
import { View,StyleSheet} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

import Title from '../components/Title'
import RequestFriends from '../components/RequestFriends';
import AuthContext from '../contexts/AuthContext'


function RequestFriendsScreen({ navigation }) {

  const [userData, setUserData] = useState(null);

const { signOut } = React.useContext(AuthContext);

return (
  <View style = {styles.background}>
  <Title title = {'Request Friends'}/>
  <View style = {styles.body}>
      <RequestFriends navigation={navigation}/>
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
    flex: 1,
  },
  profileBox: {
    flex : 1,
    alignItems : 'center',
    backgroundColor:'#ebebeb',
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
      image:"https://bootdey.com/img/Content/avatar/avatar5.png",
    },
    {
      id: '3',
      username: 'Ricardinho',
      image:"https://bootdey.com/img/Content/avatar/avatar3.png",
    },
    {
      id: '9',
      username: 'Rui Costa',
      image:"https://bootdey.com/img/Content/avatar/avatar2.png",
    },
    {
      id: '4',
      username: 'Rivaldo Esteves Esteves',
      image:"https://bootdey.com/img/Content/avatar/avatar1.png",
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


export default RequestFriendsScreen;
