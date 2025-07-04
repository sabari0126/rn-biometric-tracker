# rn-biometric-tracker

**rn-biometric-tracker** is a lightweight React Native library used to track biometric changes on the device. It enables you to detect when a user adds or removes biometric data (like fingerprint or face unlock) after tracking is enabled.

## Installation

**NPM:**

```bash
npm install rn-biometric-tracker

```

**Yarn:**

```bash
yarn add rn-biometric-tracker

```

## Usage

Import the rn-biometric-tracker library

```typescript
import BioTrack from 'rn-biometric-tracker';
```

> **Note:** We have tailored the React Native library to provide only promise functions.

We can use the library functions as per below examples:

```typescript
BioTrack.enableBiometric().then((respinse) => /* handle success */)
                        .catch((error) =>
                        /*handle error*/)

```

OR

```typescript
 async function () {
    try {
        let response = await BioTrack.enableBiometric();
        //handle success
    }catch(e) {
        // Handle Errors
    }
 }

```

## API

| Method               | Return Type | iOS | Android |
| -------------------- | ----------- | --- | ------- |
| `enableBiometric`    | `Promise`   | ✅  | ✅      |
| `disableBiometric`   | `Promise`   | ✅  | ✅      |
| `isBiometricEnabled` | `Promise`   | ✅  | ✅      |
| `isBiometricChanged` | `Promise`   | ✅  | ✅      |

### `enableBiometric()`

Enables biometric tracking to detect biometric data changes (addition or removal).

**Usage:**

```typescript
let response = await BioTrack.enableBiometric();
```

**Returns:**

| Code | Message                                        |
| ---- | ---------------------------------------------- |
| 2001 | This device doesn't have biometric features    |
| 2002 | No biometrics have been enrolled on the device |
| 2003 | Biometric registered successfully              |
| 2004 | Biometrics registration failed                 |

### `disableBiometric()`

Disables biometric tracking if it was previously enabled.

**Usage:**

```typescript
let response = await BioTrack.disableBiometric();
```

**Returns:**

| Code | Message                              |
| ---- | ------------------------------------ |
| 2005 | Biometrics deregistered successfully |
| 2006 | Biometrics deregister failed         |
| 2007 | Biometric not enabled                |

### `isBiometricEnabled()`

Checks whether biometric tracking is currently enabled.

**Usage:**

```typescript
let response = await BioTrack.isBiometricEnabled();
```

**Returns:**

- `true` — if enabled
- `false` — if disabled

### `isBiometricChanged()`

Detects whether biometric data has changed since tracking was enabled.

**Usage:**

```typescript
let response = await BioTrack.isBiometricChanged();
```

**Returns:**

| Code | Message                               |
| ---- | ------------------------------------- |
| 2007 | Biometric not enabled                 |
| 2008 | Biometric data has changed            |
| 2009 | No changes detected in biometric data |

## Notes

On Android, biometric removal detection only works if all enrolled biometrics are removed.

- ✅ **Works:** All enrolled fingerprints are deleted.
- ❌ **Doesn’t work:** Only one fingerprint is removed out of multiple.

Addition of any new biometric is fully detectable.

## Contributing

See the [contributing guide]() to learn how to contribute to the repository and the development workflow.

## License

<a href="./LICENSE"><b>MIT</b></a>
