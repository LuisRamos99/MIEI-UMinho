import React from 'react'
import {
  StyleSheet,
  View,
  TextInput,
  Image,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'


function SearchBar({ filter , value }) {
  return (

    <View style={styles.bar}>
      <View style={styles.input}>
        <TextInput
          placeholder="Type Here..."
          onChangeText={text => filter(text)}
          value={value}
          />
        </View>
        <Icon
          name="search"
          size={20}
          color='#2c365d'
          />
      </View>

    )
}

const styles = StyleSheet.create({
  bar: {
    flexDirection : 'row',
    alignItems: 'center',

  },
  input: {
    width: '80%',
    marginHorizontal: 20,
    marginBottom: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#2c365d',
  }
  });

export default SearchBar;
