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
    // config.modResults.contents = config.modResults.contents.replace(
    //   /dependencies {/,
    //   `$&
    //     implementation 'com.truecaller.android.sdk:truecaller-sdk:2.7.0'
    // `
    // );
    return config;
  });
};

const withMetadata: ConfigPlugin<{
  truecaller: { apiKey: string };
  allowOtp: boolean;
}> = (
  config,
  props: {
    allowOtp: boolean;
    truecaller: { apiKey: string };
  }
) => {
  return withAndroidManifest(config, async (config) => {
    const mainApplication = getMainApplicationOrThrow(config.modResults);
    if (props.allowOtp) {
      config.modResults.manifest['uses-permission'] =
        config.modResults.manifest['uses-permission'] || [];
      config.modResults.manifest['uses-permission'].push({
        $: {
          'android:name': 'android.permission.READ_PHONE_STATE',
        },
      });
      config.modResults.manifest['uses-permission'].push({
        $: {
          'android:name': 'android.permission.READ_CALL_LOG',
        },
      });
      config.modResults.manifest['uses-permission'].push({
        $: {
          'android:name': 'android.permission.ANSWER_PHONE_CALLS',
        },
      });
      config.modResults.manifest['uses-permission'].push({
        $: {
          'android:name': 'android.permission.CALL_PHONE',
        },
      });
    }
    addMetaDataItemToMainApplication(
      mainApplication,
      'com.truecaller.android.sdk.PartnerKey',
      props.truecaller.apiKey
    );
    return config;
  });
};

const withTruecallerUtils: ConfigPlugin<{
  truecaller: { apiKey: string };
  allowOtp: boolean;
}> = (
  config,
  props: {
    truecaller: { apiKey: string };
    allowOtp: boolean;
  }
) => {
  return withPlugins(config, [
    withJcenter,
    withTruecallerSdk,
    [withMetadata, props],
  ]);
};

const pak = require('@pankaj9296/react-native-truecaller-sdk/package.json');
export default createRunOncePlugin(withTruecallerUtils, pak.name, pak.version);
