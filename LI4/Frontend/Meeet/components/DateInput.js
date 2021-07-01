import React from 'react'
import {
  StyleSheet,
  View,
  TextInput,
  Image,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'


function DateInput({ change , date }) {
  return (

    <View style={styles.bar}>
      <View style={styles.input}>
        <TextInput
          placeholder="DD"
          onChangeText={text => change({day: text, month: date.month, year: date.year})}
          value={date.day}
          keyboardType={'phone-pad'}
          />
        </View>
        <Icon
          name="slash"
          size={15}
          color='#2c365d'
          />
      <View style={styles.input}>
        <TextInput
          placeholder="MM"
          onChangeText={text => change({day: date.day, month: text, year: date.year})}
          value={date.month}
          keyboardType={'phone-pad'}
          />
        </View>
        <Icon
          name="slash"
          size={15}
          color='#2c365d'
          />
      <View style={styles.input}>
        <TextInput
          placeholder="YYYY"
          onChangeText={text => change({day: date.day, month: date.month, year: text})}
          value={date.year}
          keyboardType={'phone-pad'}
          />
        </View>
      </View>

    )
}

const styles = StyleSheet.create({
  bar: {
    flexDirection : 'row',
    alignItems: 'center',

  },
  input: {
    alignItems: 'center',
    justifyContent: 'center',
    margin: 10,
    marginHorizontal: 5,
    backgroundColor: '#cbcbcb',
    height: 40,
    width: '18%',
    borderRadius: 10,
  }
  });

export default DateInput;
