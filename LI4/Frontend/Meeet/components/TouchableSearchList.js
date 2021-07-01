import React from 'react'
import {
  StyleSheet,
  View,
  Text,
  FlatList,
  TouchableOpacity,
  Image,
} from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5';

import  SearchBar from '../components/SearchBar';


function TouchableSearchList({ data , touchFunction }) {
  let arrayholder = data;
  const [state, setState] = React.useState({ text: '' , list: arrayholder });


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
        data={data}
        renderItem={({ item }) => (
          <TouchableOpacity

            style={[styles.itemPress, item.selectedClass]}
            onPress={() => touchFunction(item) }>
              <View style={{flexDirection: "row"}}>
              <Image style={styles.userImage} source={{uri:item.urlFoto}}/>
              <Text style={styles.text}>
                {item.username}
              </Text>
              </View>
            </TouchableOpacity>

          )}
          keyExtractor={item => item.id.toString()}
          extraData={state}
        />
        </View>
    )
}

const styles = StyleSheet.create({
  list: {
    height: '95%',
    width: '100%',
  },
  userImage:{
    height: 40,
    width: 40,
    borderRadius:80,
    backgroundColor: '#fefefe',
    borderColor:"#DCDCDC",
    borderWidth:1.5,
  },
  text: {
    marginTop:7,
    marginLeft: 13,
    fontSize:18,
    flex:1,
    fontWeight:'bold'
  },
  itemPress: {
    backgroundColor: '#fefefe',
    padding: 10,
    marginBottom: 16,
    marginHorizontal: 16,
    borderRadius: 10,
  },
});


export default TouchableSearchList;
