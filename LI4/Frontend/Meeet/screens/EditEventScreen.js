import { View, StyleSheet} from 'react-native'
import * as React from 'react';

import Title from '../components/Title'
import EditEvent from '../components/EditEvent'


function EditEventScreen({route, navigation}) {
  const { evento } = route.params

return (
    <View style = {styles.background}>
      <Title title = {'Edit Event'}/>
        <View style = {styles.body}>
          <EditEvent ev={evento} navigation={navigation}/>
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
  });

export default EditEventScreen;
