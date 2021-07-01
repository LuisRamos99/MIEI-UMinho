import * as React from 'react';
import { AsyncStorage, Button,Alert, Text, TextInput, View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
//import { AsyncStorage } from '@react-native-comunity/async-storage';
import { createStackNavigator } from '@react-navigation/stack';

import SignUpScreen from './screens/SignUpScreen'
import LoginScreen from './screens/LoginScreen'
import HomeScreen from './screens/HomeScreen'
import FriendScreen from './screens/FriendsScreen'
import ProfileScreen from './screens/ProfileScreen'
import FriendProfileScreen from './screens/FriendProfileScreen'
import EventMenuScreen from './screens/EventMenuScreen'
import EventListScreen from './screens/EventListScreen'
import GroupListScreen from './screens/GroupListScreen'
import HandleRequestScreen from './screens/HandleRequestScreen'
import HandleInviteScreen from './screens/HandleInviteScreen'
import EventScreen from './screens/EventScreen'
import ShareLocationScreen from './screens/ShareLocationScreen'
import CreateEventScreen from './screens/CreateEventScreen'
import FriendsMenuScreen from './screens/FriendsMenuScreen'
import InviteFriendsScreen from './screens/InviteFriendsScreen'
import InviteGroupScreen from './screens/InviteGroupScreen'
import InviteScreen from './screens/InviteScreen'
import RequestFriendsScreen from './screens/RequestFriendsScreen'
import CreateGroupScreen from './screens/CreateGroupScreen'
import SendGroupInvite from './screens/SendGroupInvite'
import CheckLocationScreen from './screens/CheckLocationScreen'
import EditEventScreen from './screens/EditEventScreen'
import GroupUsersScreen from './screens/GroupUsersScreen';
import CheckEventScreen from './screens/CheckEventScreen';



import AuthContext from './contexts/AuthContext'


function SplashScreen() {
  return (
    <View>
      <Text>Loading...</Text>
    </View>
  );
}

const Stack = createStackNavigator();

export default function App({ navigation }) {
  const [tempToken, setTempToken] = React.useState(-2);
  const [state, dispatch] = React.useReducer(
    (prevState, action) => {
      switch (action.type) {
        case 'RESTORE_TOKEN':
          return {
            ...prevState,
            userToken: action.token,
            isLoading: false,
          };
        case 'SIGN_IN':
          return {
            ...prevState,
            isSignout: false,
            userToken: action.token,
          };
        case 'SIGN_OUT':
          return {
            ...prevState,
            isSignout: true,
            userToken: null,
          };
      }
    },
    {
      isLoading: true,
      isSignout: false,
      userToken: null,
    }
  );

  React.useEffect(() => {
    // Fetch the token from storage then navigate to our appropriate place
    const bootstrapAsync = async () => {
      let userToken;

      try {
        userToken = await AsyncStorage.getItem('userToken');
      } catch (e) {
        console.log('Restoring from AsyncStorage error: \n' + e)
        // Restoring token failed
      } finally{
        global.userID = Number(userToken);
      }

      // After restoring token, we may need to validate it in production apps

      // This will switch to the App screen or Auth screen and this loading
      // screen will be unmounted and thrown away.
      dispatch({ type: 'RESTORE_TOKEN', token: userToken });
    };

    bootstrapAsync();
  }, []);

  React.useEffect(() => {

    const setTokenAsync = async (tempToken) => {
      try{
        await AsyncStorage.setItem(
        'userToken',
        tempToken.toString())
      } catch(e){
        console.log('Saving to AsyncStorage error: \n' + e)
      }
    }

    if(tempToken >= 0){
      dispatch({ type: 'SIGN_IN', token: {tempToken} });
      setTokenAsync(tempToken);
      global.userID = Number(tempToken);
    }
  },[tempToken]);



  const removeTokenAsync = async () => {
    try {
      await AsyncStorage.removeItem('userToken');
      return true;
    }
    catch(e) {
      console.log('Error removing toke from AsyncStorage' + e);
    }
}

const createWarning = () => {
  Alert.alert(
    "Wrong credentials!",
    "",
    [{ text: "OK" }],
    { cancelable: false }
  );
  }



  const authContext = React.useMemo(
    () => ({
      signIn: async data => {
        // In a production app, we need to send some data (usually username, password) to server and get a token
        // We will also need to handle errors if sign in failed
        // After getting token, we need to persist the token using `AsyncStorage`
        // In the example, we'll use a dummy token
        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/Login/' + data.username + '/' + data.password)
          .then((response) => response.json())
          .then((json) => {
            if (json==-1) createWarning();
            else setTempToken(json);
          })
          .catch((error) => {
            console.error(error);
          });
      },
      signOut: () => {
        dispatch({ type: 'SIGN_OUT' });
        removeTokenAsync();
      },
      signUp: async data => {
        // In a production app, we need to send user data to server and get a token
        // We will also need to handle errors if sign up failed
        // After getting token, we need to persist the token using `AsyncStorage`
        // In the example, we'll use a dummy token

        fetch('https://meeet-projeto.azurewebsites.net/api/meeet/PostUser', {
          method: 'POST',
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        });
      },
    }),
    []
  );

  return (
    <AuthContext.Provider value={authContext}>
      <NavigationContainer>
        <Stack.Navigator headerMode='none'>
          {state.isLoading ? (
            // We haven't finished checking for the token yet
            <Stack.Screen name="Splash" component={SplashScreen} />
          ) : state.userToken == null ? (
             // User is not signed in
            <>
              <Stack.Screen name="Login" component={LoginScreen} />
              <Stack.Screen name="SignUp" component={SignUpScreen} />
            </>
            ) : (
            // User is signed in
            <>
              <Stack.Screen name="Home" component={HomeScreen} />
              <Stack.Screen name="MyFriends" component={FriendScreen} />
              <Stack.Screen name="RequestFriends" component={RequestFriendsScreen} />
              <Stack.Screen name="Profile" component={ProfileScreen} />
              <Stack.Screen name="FriendProfile" component={FriendProfileScreen} />
              <Stack.Screen name="EventMenu" component={EventMenuScreen} />
              <Stack.Screen name="EventList" component={EventListScreen} />
              <Stack.Screen name="GroupList" component={GroupListScreen} />
              <Stack.Screen name="Event" component={EventScreen} />
              <Stack.Screen name="EditEvent" component={EditEventScreen} />
              <Stack.Screen name="CreateEvent" component={CreateEventScreen} />
              <Stack.Screen name="CreateGroup" component={CreateGroupScreen} />
              <Stack.Screen name="FriendsMenu" component={FriendsMenuScreen} />
              <Stack.Screen name="HandleRequest" component={HandleRequestScreen} />
              <Stack.Screen name="HandleInvite" component={HandleInviteScreen} />
              <Stack.Screen name="CheckLocation" component={CheckLocationScreen} />
              <Stack.Screen name="InviteFriends" component={InviteFriendsScreen} />
              <Stack.Screen name="InviteGroup" component={InviteGroupScreen} />
              <Stack.Screen name="Invite" component={InviteScreen} />
              <Stack.Screen name="SendGroupInvite" component={SendGroupInvite} />
              <Stack.Screen name="GroupUsers" component={GroupUsersScreen} />
              <Stack.Screen name="CheckEvent" component={CheckEventScreen} />
            </>
          )}
        </Stack.Navigator>
      </NavigationContainer>
    </AuthContext.Provider>
  );
}
