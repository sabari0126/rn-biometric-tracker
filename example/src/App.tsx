import { Text, View, StyleSheet, TouchableOpacity } from 'react-native';
import BioTrack from 'rn-biometric-tracker';

export default function App() {
  const enableBiometric = async () => {
    try {
      let res = await BioTrack.enableBiometric();
      console.log('res', res);
    } catch (e) {
      console.log('errr', e);
    }
  };

  const disableBiometric = async () => {
    try {
      let res = await BioTrack.disableBiometric();
      console.log('res', res);
    } catch (e) {
      console.log('errr', e);
    }
  };

  const isBiometricEnabled = async () => {
    try {
      let res = await BioTrack.isBiometricEnabled();
      console.log('res', res);
    } catch (e) {
      console.log('errr', e);
    }
  };

  const isBiometricChanged = async () => {
    try {
      let res = await BioTrack.isBiometricChanged();
      console.log('res', res);
    } catch (e) {
      console.log('errr', e);
    }
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={enableBiometric}>
        <Text style={styles.buttonText}>Enable Biometric</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={disableBiometric}>
        <Text style={styles.buttonText}>Disable Biometric</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={isBiometricEnabled}>
        <Text style={styles.buttonText}>Check Biometric Status</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button} onPress={isBiometricChanged}>
        <Text style={styles.buttonText}>Detect Biometric Changes</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    width: '80%',
    backgroundColor: 'lightblue',
    borderWidth: 1,
    borderColor: 'lightblue',
    borderRadius: 5,
    alignItems: 'center',
    padding: 20,
    marginVertical: 10,
  },
  buttonText: {
    fontSize: 15,
    fontWeight: 'bold',
  },
});
