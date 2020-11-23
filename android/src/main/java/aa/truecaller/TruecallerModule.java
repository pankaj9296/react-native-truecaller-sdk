package aa.truecaller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;

public class TruecallerModule extends ReactContextBaseJavaModule implements ITrueCallback, ActivityEventListener {
    private final ReactContext mReactContext;

    public TruecallerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "TruecallerModule";
    }

    @ReactMethod
    public void isUsable(Callback boolCallBack) {
        boolCallBack.invoke(TruecallerSDK.getInstance().isUsable());
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

    private int getSdkOptions(String sdkOption) {
        if ("SDK_OPTION_WITH_OTP".equals(sdkOption)) {
            return TruecallerSdkScope.SDK_OPTION_WITH_OTP;
        }
        return TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP;
    }

    @ReactMethod
    public void initializeClient(ReadableMap options, Promise promise) {
        TruecallerSdkScope.Builder trueScopeBuilder = new TruecallerSdkScope.Builder(mReactContext, this)
                .consentMode(this.getConsentMode(options.hasKey("consentMode") ? options.getString("consentMode") : null))
                .privacyPolicyUrl(options.getString("privacyLink"))
                .termsOfServiceUrl(options.getString("tncLink"))
                .loginTextPrefix(this.getLoginPrefix(options.hasKey("loginPrefix") ? options.getString("loginPrefix") : null))
                .loginTextSuffix(this.getLoginSuffix(options.hasKey("loginSuffix") ? options.getString("loginSuffix") : null))
                .ctaTextPrefix(this.getCtaPrefix(options.hasKey("ctaPrefix") ? options.getString("ctaPrefix") : null))
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .footerType(this.getFooterCta(options.hasKey("footerCta") ? options.getString("footerCta") : null))
                .consentTitleOption(this.getConsentTitle(options.hasKey("consentTitle") ? options.getString("consentTitle") : null))
                .sdkOptions(this.getSdkOptions(options.hasKey("sdkOption") ? options.getString("sdkOption") : null));
        if (options.hasKey("buttonColor")) {
            trueScopeBuilder.buttonColor(Color.parseColor(options.getString("buttonColor")));
        }
        if (options.hasKey("buttonTextColor")) {
            trueScopeBuilder.buttonTextColor(Color.parseColor(options.getString("buttonTextColor")));
        }

        TruecallerSDK.init(trueScopeBuilder.build());
        promise.resolve(null);
    }

    @ReactMethod
    public void isUsable(final Promise promise) {
        promise.resolve(TruecallerSDK.getInstance().isUsable());
    }

    @ReactMethod
    public void requestTrueProfile() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            TruecallerSDK.getInstance().getUserProfile((FragmentActivity) activity);
        }
    }

    @Override
    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
        WritableMap params = Arguments.createMap();
        params.putString("firstName", trueProfile.firstName);
        params.putString("lastName", trueProfile.lastName);
        params.putString("phoneNumber", trueProfile.phoneNumber);
        params.putString("gender", trueProfile.gender);
        params.putString("street", trueProfile.street);
        params.putString("city", trueProfile.city);
        params.putString("zipcode", trueProfile.zipcode);
        params.putString("countryCode", trueProfile.countryCode);
        params.putString("facebookId", trueProfile.facebookId);
        params.putString("twitterId", trueProfile.twitterId);
        params.putString("email", trueProfile.email);
        params.putString("url", trueProfile.url);
        params.putString("avatarUrl", trueProfile.avatarUrl);
        params.putBoolean("isTrueName", trueProfile.isTrueName);
        params.putBoolean("isAmbassador", trueProfile.isAmbassador);
        params.putString("companyName", trueProfile.companyName);
        params.putString("jobTitle", trueProfile.jobTitle);
        params.putString("payload", trueProfile.payload);
        params.putString("signature", trueProfile.signature);
        params.putString("signatureAlgorithm", trueProfile.signatureAlgorithm);
        params.putString("requestNonce", trueProfile.requestNonce);
        params.putBoolean("isSimChanged", trueProfile.isSimChanged);
        params.putString("verificationMode", trueProfile.verificationMode);
        sendEvent(mReactContext, "profileSuccessResponse", params);
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        WritableMap params = Arguments.createMap();
        params.putString("profile", "error");
        params.putInt("errorCode", trueError.getErrorType());

        sendEvent(mReactContext, "profileErrorResponse", params);
    }

    @Override
    public void onVerificationRequired(TrueError trueError) {
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity activity = getCurrentActivity();
        if (activity != null && TruecallerSDK.getInstance() != null && TruecallerSDK.getInstance().isUsable()) {
            TruecallerSDK.getInstance().onActivityResultObtained((FragmentActivity) activity, resultCode, data);
        }
    }
}
