package in.pankaj9296.android.truecaller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueException;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;
import com.truecaller.android.sdk.clients.VerificationCallback;
import com.truecaller.android.sdk.clients.VerificationDataBundle;

public class TruecallerAuthModule extends ReactContextBaseJavaModule {
  private final ReactContext mReactContext;
  private Promise promise = null;
  private String firstName = null;
  private String lastName = null;
  private String phoneNumber = null;

  public TruecallerAuthModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(mActivityEventListener);
    mReactContext = reactContext;
  }

  private int getConsentMode(String mode) {
    switch (mode) {
      case "CONSENT_MODE_FULLSCREEN":
        return TruecallerSdkScope.CONSENT_MODE_FULLSCREEN;
      case "CONSENT_MODE_BOTTOMSHEET":
        return TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET;
      default:
        return TruecallerSdkScope.CONSENT_MODE_POPUP;
    }
  }

  private int getLoginPrefix(String loginPrefix) {
    switch (loginPrefix) {
      case "LOGIN_TEXT_PREFIX_TO_CONTINUE":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_CONTINUE;
      case "LOGIN_TEXT_PREFIX_TO_PLACE_ORDER":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_PLACE_ORDER;
      case "LOGIN_TEXT_PREFIX_TO_COMPLETE_YOUR_PURCHASE":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_COMPLETE_YOUR_PURCHASE;
      case "LOGIN_TEXT_PREFIX_TO_CHECKOUT":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_CHECKOUT;
      case "LOGIN_TEXT_PREFIX_TO_COMPLETE_YOUR_BOOKING":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_COMPLETE_YOUR_BOOKING;
      case "LOGIN_TEXT_PREFIX_TO_PROCEED_WITH_YOUR_BOOKING":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_PROCEED_WITH_YOUR_BOOKING;
      case "LOGIN_TEXT_PREFIX_TO_CONTINUE_WITH_YOUR_BOOKING":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_CONTINUE_WITH_YOUR_BOOKING;
      case "LOGIN_TEXT_PREFIX_TO_GET_DETAILS":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_DETAILS;
      case "LOGIN_TEXT_PREFIX_TO_VIEW_MORE":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_VIEW_MORE;
      case "LOGIN_TEXT_PREFIX_TO_CONTINUE_READING":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_CONTINUE_READING;
      case "LOGIN_TEXT_PREFIX_TO_PROCEED":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_PROCEED;
      case "LOGIN_TEXT_PREFIX_FOR_NEW_UPDATES":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_FOR_NEW_UPDATES;
      case "LOGIN_TEXT_PREFIX_TO_GET_UPDATES":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_UPDATES;
      case "LOGIN_TEXT_PREFIX_TO_SUBSCRIBE":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_SUBSCRIBE;
      case "LOGIN_TEXT_PREFIX_TO_SUBSCRIBE_AND_GET_UPDATES":
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_SUBSCRIBE_AND_GET_UPDATES;
      default:
        return TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED;
    }
  }

  private int getLoginSuffix(String loginSuffix) {
    switch (loginSuffix) {
      case "LOGIN_TEXT_SUFFIX_PLEASE_LOGIN":
        return TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_LOGIN;
      case "LOGIN_TEXT_SUFFIX_PLEASE_SIGNUP":
        return TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_SIGNUP;
      case "LOGIN_TEXT_SUFFIX_PLEASE_LOGIN_SIGNUP":
        return TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_LOGIN_SIGNUP;
      case "LOGIN_TEXT_SUFFIX_PLEASE_REGISTER":
        return TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_REGISTER;
      case "LOGIN_TEXT_SUFFIX_PLEASE_SIGN_IN":
        return TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_SIGN_IN;
      default:
        return TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO;
    }
  }

  private int getCtaPrefix(String ctaPrefix) {
    switch (ctaPrefix) {
      case "CTA_TEXT_PREFIX_USE":
        return TruecallerSdkScope.CTA_TEXT_PREFIX_USE;
      case "CTA_TEXT_PREFIX_PROCEED_WITH":
        return TruecallerSdkScope.CTA_TEXT_PREFIX_PROCEED_WITH;
      default:
        return TruecallerSdkScope.CTA_TEXT_PREFIX_CONTINUE_WITH;
    }
  }

  private int getFooterCta(String footerCta) {
    switch (footerCta) {
      case "FOOTER_TYPE_SKIP":
        return TruecallerSdkScope.FOOTER_TYPE_SKIP;
      case "FOOTER_TYPE_CONTINUE":
        return TruecallerSdkScope.FOOTER_TYPE_CONTINUE;
      case "FOOTER_TYPE_MANUALLY":
        return TruecallerSdkScope.FOOTER_TYPE_MANUALLY;
      case "FOOTER_TYPE_LATER":
        return TruecallerSdkScope.FOOTER_TYPE_LATER;
      default:
        return TruecallerSdkScope.FOOTER_TYPE_ANOTHER_METHOD;
    }
  }

  private int getConsentTitle(String consentTitle) {
    switch (consentTitle) {
      case "SDK_CONSENT_TITLE_LOG_IN":
        return TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN;
      case "SDK_CONSENT_TITLE_SIGN_UP":
        return TruecallerSdkScope.SDK_CONSENT_TITLE_SIGN_UP;
      case "SDK_CONSENT_TITLE_SIGN_IN":
        return TruecallerSdkScope.SDK_CONSENT_TITLE_SIGN_IN;
      case "SDK_CONSENT_TITLE_VERIFY":
        return TruecallerSdkScope.SDK_CONSENT_TITLE_VERIFY;
      case "SDK_CONSENT_TITLE_REGISTER":
        return TruecallerSdkScope.SDK_CONSENT_TITLE_REGISTER;
      default:
        return TruecallerSdkScope.SDK_CONSENT_TITLE_GET_STARTED;
    }
  }

  private int getSdkOptions(Boolean useOtp) {
    if (useOtp) {
      return TruecallerSdkScope.SDK_OPTION_WITH_OTP;
    }
    return TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP;
  }

  @ReactMethod(isBlockingSynchronousMethod = true)
  public void initializeClient(ReadableMap options) {
    TruecallerSdkScope.Builder trueScopeBuilder = new TruecallerSdkScope.Builder(mReactContext, sdkCallback)
        .consentMode(this.getConsentMode(options.hasKey("consentMode") ? options.getString("consentMode") : ""))
        .privacyPolicyUrl(options.getString("privacyLink"))
        .termsOfServiceUrl(options.getString("tncLink"))
        .loginTextPrefix(this.getLoginPrefix(options.hasKey("loginPrefix") ? options.getString("loginPrefix") : ""))
        .loginTextSuffix(this.getLoginSuffix(options.hasKey("loginSuffix") ? options.getString("loginSuffix") : ""))
        .ctaTextPrefix(this.getCtaPrefix(options.hasKey("ctaPrefix") ? options.getString("ctaPrefix") : ""))
        .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
        .footerType(this.getFooterCta(options.hasKey("footerCta") ? options.getString("footerCta") : ""))
        .consentTitleOption(
            this.getConsentTitle(options.hasKey("consentTitle") ? options.getString("consentTitle") : ""))
        .sdkOptions(this.getSdkOptions(options.hasKey("sdkOption") && options.getBoolean("sdkOption")));
    if (options.hasKey("buttonColor")) {
      trueScopeBuilder.buttonColor(Color.parseColor(options.getString("buttonColor")));
    }
    if (options.hasKey("buttonTextColor")) {
      trueScopeBuilder.buttonTextColor(Color.parseColor(options.getString("buttonTextColor")));
    }
    TruecallerSDK.init(trueScopeBuilder.build());
  }

  private final ITrueCallback sdkCallback = new ITrueCallback() {
    @Override
    public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {

      if (promise != null) {
        WritableMap map = Arguments.createMap();
        map.putBoolean("successful", true);
        map.putString("firstName", trueProfile.firstName);
        map.putString("lastName", trueProfile.lastName);
        map.putString("phoneNumber", trueProfile.phoneNumber);
        map.putString("gender", trueProfile.gender);
        map.putString("street", trueProfile.street);
        map.putString("city", trueProfile.city);
        map.putString("zipcode", trueProfile.zipcode);
        map.putString("countryCode", trueProfile.countryCode);
        map.putString("facebookId", trueProfile.facebookId);
        map.putString("twitterId", trueProfile.twitterId);
        map.putString("email", trueProfile.email);
        map.putString("url", trueProfile.url);
        map.putString("avatarUrl", trueProfile.avatarUrl);
        map.putBoolean("isVerified", trueProfile.isTrueName);
        map.putBoolean("isAmbassador", trueProfile.isAmbassador);
        map.putString("companyName", trueProfile.companyName);
        map.putString("jobTitle", trueProfile.jobTitle);
        map.putString("payload", trueProfile.payload);
        map.putString("signature", trueProfile.signature);
        map.putString("signatureAlgorithm", trueProfile.signatureAlgorithm);
        map.putString("requestNonce", trueProfile.requestNonce);
        map.putBoolean("isBusiness", trueProfile.isBusiness);
        promise.resolve(map);
      }
    }

    @Override
    public void onFailureProfileShared(@NonNull final TrueError trueError) {
      Log.d("TruecallerAuthModule", Integer.toString(trueError.getErrorType()));
      if (promise != null) {
        String errorReason = null;
        switch (trueError.getErrorType()) {
          case TrueError.ERROR_TYPE_CONTINUE_WITH_DIFFERENT_NUMBER:
            errorReason = "ERROR_TYPE_CONTINUE_WITH_DIFFERENT_NUMBER";
            break;
          case TrueError.ERROR_TYPE_PARTNER_INFO_NULL:
            errorReason = "ERROR_TYPE_PARTNER_INFO_NULL";
            break;
          case TrueError.ERROR_TYPE_USER_DENIED_WHILE_LOADING:
            errorReason = "ERROR_TYPE_USER_DENIED_WHILE_LOADING";
            break;
          case TrueError.ERROR_TYPE_INTERNAL:
            errorReason = "ERROR_TYPE_INTERNAL";
            break;
          case TrueError.ERROR_TYPE_NETWORK:
            errorReason = "ERROR_TYPE_NETWORK";
            break;
          case TrueError.ERROR_TYPE_USER_DENIED:
            errorReason = "ERROR_TYPE_USER_DENIED";
            break;
          case TrueError.ERROR_PROFILE_NOT_FOUND:
            errorReason = "ERROR_TYPE_UNAUTHORIZED_PARTNER";
            break;
          case TrueError.ERROR_TYPE_UNAUTHORIZED_USER:
            errorReason = "ERROR_TYPE_UNAUTHORIZED_USER";
            break;
          case TrueError.ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY:
            errorReason = "ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY";
            break;
          case TrueError.ERROR_TYPE_TRUESDK_TOO_OLD:
            errorReason = "ERROR_TYPE_TRUESDK_TOO_OLD";
            break;
          case TrueError.ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION:
            errorReason = "ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION";
            break;
          case TrueError.ERROR_TYPE_RESPONSE_SIGNATURE_MISMATCH:
            errorReason = "ERROR_TYPE_RESPONSE_SIGNATURE_MISSMATCH";
            break;
          case TrueError.ERROR_TYPE_REQUEST_NONCE_MISMATCH:
            errorReason = "ERROR_TYPE_REQUEST_NONCE_MISSMATCH";
            break;
          case TrueError.ERROR_TYPE_INVALID_ACCOUNT_STATE:
            errorReason = "ERROR_TYPE_INVALID_ACCOUNT_STATE";
            break;
          case TrueError.ERROR_TYPE_TC_NOT_INSTALLED:
            errorReason = "ERROR_TYPE_TC_NOT_INSTALLED";
            break;
          case TrueError.ERROR_TYPE_ACTIVITY_NOT_FOUND:
            errorReason = "ERROR_TYPE_ACTIVITY_NOT_FOUND";
            break;
        }
        WritableMap map = Arguments.createMap();
        map.putString("method", "onFailureProfileShared");
        map.putString("error", errorReason != null ? errorReason : "ERROR_TYPE_NULL");
        promise.resolve(map);
      }
    }

    @Override
    public void onVerificationRequired(TrueError trueError) {
      // The statement below can be ignored incase of one-tap flow integration
      if (getCurrentActivity() != null) {
        if (promise != null) {
          String errorReason = null;
          switch (trueError.getErrorType()) {
            case TrueError.ERROR_TYPE_CONTINUE_WITH_DIFFERENT_NUMBER:
              errorReason = "ERROR_TYPE_CONTINUE_WITH_DIFFERENT_NUMBER";
              break;
            case TrueError.ERROR_TYPE_PARTNER_INFO_NULL:
              errorReason = "ERROR_TYPE_PARTNER_INFO_NULL";
              break;
            case TrueError.ERROR_TYPE_USER_DENIED_WHILE_LOADING:
              errorReason = "ERROR_TYPE_USER_DENIED_WHILE_LOADING";
              break;
            case TrueError.ERROR_TYPE_INTERNAL:
              errorReason = "ERROR_TYPE_INTERNAL";
              break;
            case TrueError.ERROR_TYPE_NETWORK:
              errorReason = "ERROR_TYPE_NETWORK";
              break;
            case TrueError.ERROR_TYPE_USER_DENIED:
              errorReason = "ERROR_TYPE_USER_DENIED";
              break;
            case TrueError.ERROR_PROFILE_NOT_FOUND:
              errorReason = "ERROR_TYPE_UNAUTHORIZED_PARTNER";
              break;
            case TrueError.ERROR_TYPE_UNAUTHORIZED_USER:
              errorReason = "ERROR_TYPE_UNAUTHORIZED_USER";
              break;
            case TrueError.ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY:
              errorReason = "ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY";
              break;
            case TrueError.ERROR_TYPE_TRUESDK_TOO_OLD:
              errorReason = "ERROR_TYPE_TRUESDK_TOO_OLD";
              break;
            case TrueError.ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION:
              errorReason = "ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION";
              break;
            case TrueError.ERROR_TYPE_RESPONSE_SIGNATURE_MISMATCH:
              errorReason = "ERROR_TYPE_RESPONSE_SIGNATURE_MISSMATCH";
              break;
            case TrueError.ERROR_TYPE_REQUEST_NONCE_MISMATCH:
              errorReason = "ERROR_TYPE_REQUEST_NONCE_MISSMATCH";
              break;
            case TrueError.ERROR_TYPE_INVALID_ACCOUNT_STATE:
              errorReason = "ERROR_TYPE_INVALID_ACCOUNT_STATE";
              break;
            case TrueError.ERROR_TYPE_TC_NOT_INSTALLED:
              errorReason = "ERROR_TYPE_TC_NOT_INSTALLED";
              break;
            case TrueError.ERROR_TYPE_ACTIVITY_NOT_FOUND:
              errorReason = "ERROR_TYPE_ACTIVITY_NOT_FOUND";
              break;
          }
          WritableMap map = Arguments.createMap();
          map.putString("method", "onVerificationRequired");
          map.putString("error", errorReason != null ? errorReason : "ERROR_TYPE_VERIFICATION_REQUIRED");
          promise.resolve(map);
        }
      }
    }
  };

  // Callback below can be ignored incase of one-tap only integration

  final VerificationCallback apiCallback = new VerificationCallback() {
    @Override
    public void onRequestSuccess(int requestCode, @Nullable VerificationDataBundle extras) {
      if (requestCode == VerificationCallback.TYPE_MISSED_CALL_INITIATED) {

        // Retrieving the TTL for missedcall
        if (extras != null) {
          extras.getString(VerificationDataBundle.KEY_TTL);
        }
        if (promise != null) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("successful", true);
          map.putString("method", "onRequestSuccess");
          map.putString("type", "TYPE_MISSED_CALL_INITIATED");
          promise.resolve(map);
        }
      }
      if (requestCode == VerificationCallback.TYPE_MISSED_CALL_RECEIVED) {
        if (firstName != null && lastName != null) {
          TrueProfile profile = new TrueProfile.Builder(firstName, lastName).build();
          TruecallerSDK.getInstance().verifyMissedCall(profile, apiCallback);
        }
        if (promise != null) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("successful", true);
          map.putString("method", "onRequestSuccess");
          map.putString("type", "TYPE_MISSED_CALL_RECEIVED");
          promise.resolve(map);
        }
      }
      if (requestCode == VerificationCallback.TYPE_OTP_INITIATED) {

        // Retrieving the TTL for otp
        if (extras != null) {
          extras.getString(VerificationDataBundle.KEY_TTL);
        }
        if (promise != null) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("successful", true);
          map.putString("method", "onRequestSuccess");
          map.putString("type", "TYPE_OTP_INITIATED");
          promise.resolve(map);
        }
      }
      if (requestCode == VerificationCallback.TYPE_OTP_RECEIVED) {
        // TODO get name and number in request verification as well
        // TrueProfile profile = new TrueProfile.Builder("Pankaj", "Patidar").build();
        // TruecallerSDK.getInstance().verifyOtp(profile, "123456", apiCallback);
        if (promise != null) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("successful", true);
          map.putString("method", "onRequestSuccess");
          map.putString("type", "TYPE_OTP_RECEIVED");
          promise.resolve(map);
        }
      }
      if (requestCode == VerificationCallback.TYPE_VERIFICATION_COMPLETE) {
        if (promise != null) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("successful", true);
          map.putString("method", "onRequestSuccess");
          map.putString("type", "TYPE_VERIFICATION_COMPLETE");
          promise.resolve(map);
        }
      }
      if (requestCode == VerificationCallback.TYPE_PROFILE_VERIFIED_BEFORE) {
        if (promise != null) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("successful", true);
          map.putString("method", "onRequestSuccess");
          map.putString("type", "TYPE_PROFILE_VERIFIED_BEFORE");
          promise.resolve(map);
        }
      }
    }

    @Override
    public void onRequestFailure(final int requestCode, @NonNull final TrueException e) {
      // Write the Exception Part
      if (promise != null) {
        String errorReason = TrueException.getExceptionMessage();
        WritableMap map = Arguments.createMap();
        map.putString("method", "onRequestFailure");
        map.putString("error", errorReason != null ? errorReason : "ERROR_TYPE_REQUEST_FAILED");
        map.putInt("code", requestCode);
        promise.resolve(map);
      }
    }
  };

  @NonNull
  @Override
  public String getName() {
    return "TruecallerAuthModule";
  }

  @ReactMethod
  public void isUsable(Promise promise) {
    if (TruecallerSDK.getInstance() != null) {
      promise.resolve(TruecallerSDK.getInstance().isUsable());
    } else {
      promise.reject(new Exception("ERROR_TYPE_NOT_SUPPORTED"));
    }
  }
  
  @ReactMethod
  public void requestVerification(String firstName, String lastName, String phoneNumber, Promise promise) {
    this.promise = promise;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
            
    // get first name, last name, phone number 10 digit here
    if (TruecallerSDK.getInstance() != null) {
      try {
        UiThreadUtil.runOnUiThread(new Runnable()
        {
          @Override  public void run()
          {
            TruecallerSDK.getInstance().requestVerification("IN", phoneNumber, apiCallback, (FragmentActivity) getCurrentActivity());
          }
        });
      }catch (RuntimeException e){
        promise.reject(e);
      }
    } else {
      promise.reject(new Exception("ERROR_TYPE_NOT_SUPPORTED"));
    }
  }
  
  @ReactMethod
  public void requestOtpVerification(String Otp, Promise promise) {
    this.promise = promise;
    // get otp here
    if (TruecallerSDK.getInstance() != null) {
      try{
        // get profile from above
        if (firstName != null && lastName != null) {
          UiThreadUtil.runOnUiThread(new Runnable()
          {
            @Override  public void run()
            {
              TrueProfile profile = new TrueProfile.Builder(firstName, lastName).build();
              TruecallerSDK.getInstance().verifyOtp(profile, Otp, apiCallback);
            }
          });
        }
      } catch (RuntimeException e) {
        promise.reject(e);
      }
    } else {
      promise.reject(new Exception("ERROR_TYPE_NOT_SUPPORTED"));
    }
  }
  
  @ReactMethod
  public void authenticate(Promise promise) {
    this.promise = promise;
    try {
      if (getCurrentActivity() != null) {
        if (TruecallerSDK.getInstance() != null) {
          if (TruecallerSDK.getInstance().isUsable()) {
            UiThreadUtil.runOnUiThread(new Runnable()
            {
              @Override  public void run()
              {
                TruecallerSDK.getInstance().getUserProfile((FragmentActivity) getCurrentActivity());
              }
            });
          } else {
            this.promise.reject(new Exception("ERROR_NOT_INSTALLED_NOT_LOGGED_IN"));
          }
          // For One-Tap implementation : The isUsable method would return true incase
          // where the truecaller app is installed and logged in else it will return
          // false.
          // For Full-Stack implementation : The isUsable method would always return true
          // as now the SDK can be used to verify both truecaller and non-truecaller users

        } else {
          this.promise.reject(new Exception("ERROR_TYPE_NOT_SUPPORTED"));
        }
      } else {
        this.promise.reject(new Exception("ERROR_TYPE_NO_ACTIVITY"));
      }
    } catch (Exception e) {
      this.promise.reject(e);
    }
  }

  @ReactMethod
  public void SDKClear() {
    TruecallerSDK.clear();
  }

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
      super.onActivityResult(activity, requestCode, resultCode, intent);

      if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
        TruecallerSDK.getInstance().onActivityResultObtained((FragmentActivity) activity, requestCode, resultCode,
            intent);
      }
    }
  };
}