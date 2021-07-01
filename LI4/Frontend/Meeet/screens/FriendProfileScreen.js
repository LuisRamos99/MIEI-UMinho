import React from 'react'
import {StyleSheet,View} from 'react-native';

import Title from '../components/Title'
import ProfileFriend from '../components/ProfileFriend'


function FriendProfileScreen({ route }) {
    const { id } = route.params;
    return (
    <View style = {styles.background}>
      <Title title = {'Profile'}/>

      <View style = {styles.profileBox}>
        <ProfileFriend id={id} />
      </View>
    </View>
    )
}

const styles = StyleSheet.create({
  profileBox: {
    flex : 1,
    alignItems : 'center',
    backgroundColor:'#ebebeb',
  },
  background: {
    flex : 1,
    backgroundColor:'#ebebeb',
  },
  });


export default FriendProfileScreen;
