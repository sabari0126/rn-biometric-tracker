const path = require('path');
const { getDefaultConfig } = require('@react-native/metro-config');

/**
 * Metro configuration
 * https://facebook.github.io/metro/docs/configuration
 *
 * @type {import('metro-config').MetroConfig}
 */
module.exports = (async () => {
  const { withMetroConfig } = await import('react-native-monorepo-config');
  const root = path.resolve(__dirname, '..');

  return withMetroConfig(getDefaultConfig(__dirname), {
    root,
    dirname: __dirname,
  });
})();
