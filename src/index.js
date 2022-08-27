import { NativeModules } from 'react-native';
const { TruecallerAuthModule } = NativeModules;

// Constants for configuration
export const TRUECALLER_CONSENT_MODE = {
  Popup: 'CONSENT_MODE_POPUP',
  FullScreen: 'CONSENT_MODE_FULLSCREEN',
  BottomSheet: 'CONSENT_MODE_BOTTOMSHEET',
};

export const TRUECALLER_CONSENT_PREFIX = {
  Continue: 'LOGIN_TEXT_PREFIX_TO_CONTINUE',
  Proceed: 'LOGIN_TEXT_PREFIX_TO_PROCEED',
  GetStarted: LOGIN_TEXT_PREFIX_TO_GET_STARTED,
};

export const TRUECALLER_CONSENT_SUFFIX = {
  Login: 'LOGIN_TEXT_SUFFIX_PLEASE_LOGIN',
  Signup: 'LOGIN_TEXT_SUFFIX_PLEASE_SIGNUP',
  Register: 'LOGIN_TEXT_SUFFIX_PLEASE_REGISTER',
  SignIn: 'LOGIN_TEXT_SUFFIX_PLEASE_SIGN_IN',
  Verify: 'LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO',
};

export const TRUECALLER_CTA = {
  Use: 'CTA_TEXT_PREFIX_USE',
  Proceed: 'CTA_TEXT_PREFIX_PROCEED_WITH',
  Continue: 'CTA_TEXT_PREFIX_CONTINUE_WITH',
};

export const TRUECALLER_CONSENT_TITLE = {
  Login: 'SDK_CONSENT_TITLE_LOG_IN',
  SignUp: 'SDK_CONSENT_TITLE_SIGN_UP',
  SignIn: 'SDK_CONSENT_TITLE_SIGN_IN',
  Verify: 'SDK_CONSENT_TITLE_VERIFY',
  Register: 'SDK_CONSENT_TITLE_REGISTER',
  GetStarted: 'SDK_CONSENT_TITLE_GET_STARTED',
};

export const TRUECALLER_FOOTER_TYPE = {
  Skip: 'FOOTER_TYPE_SKIP',
  Continue: 'FOOTER_TYPE_CONTINUE',
};

export default TruecallerAuthModule;
