import React, { Component, useState, useEffect } from 'react'
import { View, Text, StyleSheet, TouchableOpacity, Image, PermissionsAndroid, ActivityIndicator} from 'react-native'
import Icon from 'react-native-vector-icons/dist/FontAwesome5'
import MapView, { Marker } from 'react-native-maps'
import Geolocation from 'react-native-geolocation-service';

import Title from '../components/Title'
import EventContext from '../contexts/EventContext'


function CheckLocationScreen({ navigation, route }) {
  const { evento } = React.useContext(EventContext);
  const { id } = route.params

  const [latitude, setLatitude] = useState(null);
  const [longitude, setLongitude] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getLongitudeUser/'+ id)
      .then((response) => { return response.json(); })
      .then((json) => {
        setLongitude(json);
      })
      .catch((error) => {
        console.error(error);
    });

    fetch('https://meeet-projeto.azurewebsites.net/api/meeet/getLatitudeUser/'+ id)
      .then((response) => { return response.json(); })
      .then((json) => {
        setLatitude(json);
      })
      .catch((error) => {
        console.error(error);
    });

  },[]);

  useEffect(() => {
    if(latitude != null && longitude != null){
      setIsLoading(false);
    }
  },[latitude, longitude]);



  return (
    <View style = {styles.background}>

      <Title title = {'Location Sharing'}/>

      <View style = {styles.body}>

      {isLoading? <ActivityIndicator/> : (
        <View style={styles.container}>
        <MapView
          style={styles.map}
          region={{
            latitude: latitude,
            longitude: longitude,
            latitudeDelta: 0.015,
            longitudeDelta: 0.0121,
          }}
          customMapStyle={mapStyle}
          >
          <Marker
            coordinate={{  latitude: latitude, longitude: longitude, }}
            title={"ExampleUser"}
            description={"Uni fechada"}
            />
        </MapView>
       </View>)}

        <View style = {styles.buttons}>

          <TouchableOpacity style={styles.button} onPress={() => navigation.goBack()}>
            <Text style= {{color: '#fbfbfb'}}>
              Go Back
              </Text>
          </TouchableOpacity>

        </View>

      </View>

    </View>
  )
}

const styles = StyleSheet.create({
  buttons: {
    flexDirection: 'row',
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    margin: 20,
    alignItems: 'center',
    justifyContent: 'center',
    width: '50%',
    height: 50,
    borderRadius: 10,
    backgroundColor: '#2c365d',
  },
  background: {
    flex : 1,
    backgroundColor:'#ebebeb',
  },
  body: {
    marginTop: 60,
    alignItems: 'center',
    flex: 1,
  },
  container: {
   height: "50%",
   width: "90%",
   borderRadius: 50,
   borderWidth: 2,
   borderColor: "#2c365d",
   justifyContent: 'flex-end',
   alignItems: 'center',
   overflow: 'hidden'
  },
  map: {
   ...StyleSheet.absoluteFillObject,
  },
});

const mapStyle =[
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#ebe3cd"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#523735"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#f5f1e6"
      }
    ]
  },
  {
    "featureType": "administrative",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#c9b2a6"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#dcd2be"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#ae9e90"
      }
    ]
  },
  {
    "featureType": "landscape.natural",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dfd2ae"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dfd2ae"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#93817c"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry.fill",
    "stylers": [
      {
        "color": "#a5b076"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#447530"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#f5f1e6"
      }
    ]
  },
  {
    "featureType": "road.arterial",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#fdfcf8"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#f8c967"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#e9bc62"
      }
    ]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#e98d58"
      }
    ]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#db8555"
      }
    ]
  },
  {
    "featureType": "road.local",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#806b63"
      }
    ]
  },
  {
    "featureType": "transit.line",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dfd2ae"
      }
    ]
  },
  {
    "featureType": "transit.line",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#8f7d77"
      }
    ]
  },
  {
    "featureType": "transit.line",
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#ebe3cd"
      }
    ]
  },
  {
    "featureType": "transit.station",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#dfd2ae"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry.fill",
    "stylers": [
      {
        "color": "#b9d3c2"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#92998d"
      }
    ]
  }
]

export default CheckLocationScreen;
