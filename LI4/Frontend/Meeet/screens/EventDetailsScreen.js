import { View, StyleSheet} from 'react-native'
import * as React from 'react';

import Title from '../components/Title'
import EventDetails from '../components/EventDetails'
import EventContext from '../contexts/EventContext'

function EventDetailsScreen({navigation}) {
  const { evento } = React.useContext(EventContext);

return (
    <View style = {styles.background}>
      <Title title = {'Event Details'}/>
        <View style = {styles.body}>
          <EventDetails data={evento} navigation={navigation}/>
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

export default EventDetailsScreen;
