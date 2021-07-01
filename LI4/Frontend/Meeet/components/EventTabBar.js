import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/dist/FontAwesome5'

function EventTabBar({ state, descriptors, navigation }) {
  const focusedOptions = descriptors[state.routes[state.index].key].options;

  if (focusedOptions.tabBarVisible === false) {
    return null;
  }

  return (
    <View style={{ flexDirection: 'row', height:'8%', alignItems: 'center', borderRadius: 20 }}>
      {state.routes.map((route, index) => {
        const { options } = descriptors[route.key];
        const label =
          options.tabBarLabel !== undefined
            ? options.tabBarLabel
            : options.title !== undefined
            ? options.title
            : route.name;

        let iconName;
        let textName;

        switch(route.name) {
          case 'EventDetails':
            iconName = 'calendar-alt';
            textName = 'Event';
            break;
          case 'EventUsers':
            iconName = 'users';
            textName = 'Users';
            break;
          case 'ShareLocation':
            iconName = 'globe-europe';
            textName = 'Localização';
            break;
          case 'Vote':
            iconName = 'check';
            textName = 'Votação';
            break;
          default:
          // code block
        }
        if (route.name === 'EventDetails') {

        } else if (route.name === 'Settings') {
          iconName = focused ? 'ios-list-box' : 'ios-list';
        }

        const isFocused = state.index === index;

        const onPress = () => {
          const event = navigation.emit({
            type: 'tabPress',
            target: route.key,
            canPreventDefault: true,
          });

          if (!isFocused && !event.defaultPrevented) {
            navigation.navigate(route.name);
          }
        };

        const onLongPress = () => {
          navigation.emit({
            type: 'tabLongPress',
            target: route.key,
          });
        };

        return(
          <TouchableOpacity
            accessibilityRole="button"
            accessibilityStates={isFocused ? ['selected'] : []}
            accessibilityLabel={options.tabBarAccessibilityLabel}
            testID={options.tabBarTestID}
            onPress={onPress}
            onLongPress={onLongPress}
            style={styles.singleTab}
            key={iconName}
          >
            <Icon
              name={iconName}
              size={20}
              color={isFocused ? '#d18e57' : '#2c365d'}
              style={{marginTop: 7, marginBottom: 5}}
            />
            <Text style={{ color:'#2c365d' , fontSize: 10}}>
              {textName}
            </Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
}

const styles = StyleSheet.create({
  singleTab: {
    flex: 1,
    alignItems: 'center',
    borderRightWidth: 1,
    borderLeftWidth: 1,
    borderTopWidth: 2,

    borderColor: '#2c365d',
    height: '100%',
  },
});

export default EventTabBar;
