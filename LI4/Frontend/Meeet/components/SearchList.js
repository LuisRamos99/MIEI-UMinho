import React from 'react'
import {
  StyleSheet,
  View,
  Text,
  FlatList,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5';

import  SearchBar from '../components/SearchBar';


function SearchList({ title , data }) {
  const [state, setState] = React.useState({ text: '' , list: data });

  let arrayholder = data;

  function searchFilterFunction(text){
    const newData = arrayholder.filter(item => {
      const itemData = `${item.username.toUpperCase()}
      ${item.username.toUpperCase()}`;

      const textData = text.toUpperCase();
      return itemData.indexOf(textData) > -1;
   });
    setState({text: state.text, list: newData });
  };


  return (

    <View style={styles.list}>
      <SearchBar
        placeholder="Type Here..."
        filter={searchFilterFunction}
        onChangeText={state.text}
        />
      <FlatList
        data={state.list}
        renderItem={({ item }) => (
          <View style = {styles.friend}>
            <Text>{item.username}</Text>
          </View>
          )}
          keyExtractor={item => item.id}
        />
        </View>
    )
}

const styles = StyleSheet.create({
  list: {
    height: '95%',
    width: '100%',
  },
  friend: {
    backgroundColor: '#fefefe',
    padding: 20,
    marginBottom: 16,
    marginHorizontal: 16,
    borderRadius: 10,
  },
});


export default SearchList;
