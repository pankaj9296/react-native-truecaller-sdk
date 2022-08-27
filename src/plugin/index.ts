import {
  AndroidConfig,
  ConfigPlugin,
  createRunOncePlugin,
  withAndroidManifest,
  withAppBuildGradle,
  withPlugins,
  withProjectBuildGradle,
} from '@expo/config-plugins';

const { addMetaDataItemToMainApplication, getMainApplicationOrThrow } =
  AndroidConfig.Manifest;

const withJcenter: ConfigPlugin = (config) => {
  return withProjectBuildGradle(config, async (config) => {
    config.modResults.contents = config.modResults.contents.replace(
      /google\(\)/g,
      `$&
        jcenter()`
    );
    config.modResults.contents = config.modResults.contents.replace(
      /classpath\('de.undercouch:gradle-download-task.*?$/m,
      `$&
        classpath("com.bugsnag:bugsnag-android-gradle-plugin:7.+")`
    );
    return config;
  });
};

const withTruecallerSdk: ConfigPlugin = (config) => {
  return withAppBuildGradle(config, async (config) => {
    config.modResults.contents = config.modResults.contents.replace(
      /dependencies {/,
      `$&
        implementation 'com.truecaller.android.sdk:truecaller-sdk:2.7.0'
    `
    );
    return config;
  });
};

const withMetadata: ConfigPlugin<{
  truecaller: { apiKey: string };
}> = (
  config,
  props: {
    truecaller: { apiKey: string };
  }
) => {
  return withAndroidManifest(config, async (config) => {
    const mainApplication = getMainApplicationOrThrow(config.modResults);
    addMetaDataItemToMainApplication(
      mainApplication,
      'com.truecaller.android.sdk.PartnerKey',
      props.truecaller.apiKey
    );
    return config;
  });
};

const withGalaxyCardUtils: ConfigPlugin<{
  truecaller: { apiKey: string };
}> = (config, props: { truecaller: { apiKey: string } }) => {
  return withPlugins(config, [
    withJcenter,
    withTruecallerSdk,
    [withMetadata, props],
  ]);
};

const pak = require('@galaxycard/react-native-truecaller-sdk/package.json');
export default createRunOncePlugin(withGalaxyCardUtils, pak.name, pak.version);
