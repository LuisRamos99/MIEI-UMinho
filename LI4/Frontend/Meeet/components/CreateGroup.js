import React, { Component, useState } from 'react'
import { View, Image, Text, StyleSheet, TextInput, Button, TouchableOpacity,Alert} from 'react-native'

import AuthContext from '../contexts/AuthContext'
import TouchableSearchList from '../components/TouchableSearchList'


function CreateGroup({ data }) {

  const [selectedList, setList] = useState([]);
  const [groupName, updateGroupName] = useState('');
  const [group, setGroup] = React.useState(null);
  const [res, setRes] = React.useState(null);

  function nothing(item){
    item.isSelected = !item.isSelected;
    if(item.isSelected){
      setList(oldArray => [...oldArray, item.id]);
      item.selectedClass = styles.selected;
    }
    else{
      let auxArray= selectedList.filter(value => { return value != item.id })
      setList(auxArray);
      item.selectedClass = styles.itemPress;
    }
  }

  React.useEffect(() => {
    setGroup({
      "nome": groupName,
      "utilizadorGrupo": null,
  });
  console.log("nome " + groupName);
  },[groupName]);

    async function createGroup() {
        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostGrupo', {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(group)
      })
      .then((response) => response.json())
      .then((json) => {
        setRes(json);
      })
      .catch((error) => {
        console.error(error);
      });
    };

    React.useEffect(() => {
      if(res != null){
        addAllToGroup();
      }
  },[res]);

    function addAllToGroup() {
      addToGroup(global.userID);
      selectedList.forEach(item => {
        addToGroup(item);});
      createWarning();
    }

    async function addToGroup(id) {
      fetch('https://meeet-projeto.azurewebsites.net/api/meeet/AddToGroup/' + id, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(res)
    })
    .catch((error) => {
      console.error(error);
    });
  };


    const createWarning = () =>
    Alert.alert(
      "Group created sucessufly!",
      "",
      [{ text: "OK" }],
      { cancelable: false }
    );

    return (
    <>

      <View style = {styles.profileInput}>

        <TextInput
          style={styles.textInput}
          textAlign={'center'}
          placeholder={"Group Name"}
          onChangeText={updateGroupName}
          value={groupName}
          />

      </View>

      <View style = {styles.list}>

        <TouchableSearchList
        data={data}
        touchFunction={nothing}
        />

      </View>

      <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => createGroup()}>
            <Text style= {{color: '#fbfbfb'}}>
              Create
              </Text>
            </TouchableOpacity>

      </View>
    </>
    )
}

const styles = StyleSheet.create({
  textInput: {
    margin: 10,
    marginHorizontal: 5,
    backgroundColor: '#cbcbcb',
    height: 40,
    width: '70%',
    fontSize: 16,
    borderRadius: 10,
  },
  profileInput:{
    flexDirection: 'row',
    alignItems: 'center',
  },
  list: {
    height: '68%',
    width: '100%',
  },
  buttons: {
    alignItems: 'center',
    marginTop: 10,
    width: '50%',
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: 50,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  space: {
    marginVertical: 25,
  },
  selected: {
    backgroundColor: "hsl(85, 100%, 50%)"
  },
});


export default CreateGroup;
