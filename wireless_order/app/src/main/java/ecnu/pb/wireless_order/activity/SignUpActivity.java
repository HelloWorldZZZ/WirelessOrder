package ecnu.pb.wireless_order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.database.AccountManager;
import ecnu.pb.wireless_order.presenter.SignUpPresenter;
import ecnu.pb.wireless_order.widget.ToastUtils;

public class SignUpActivity extends AppCompatActivity implements Validator.ValidationListener, SignUpPresenter.View {

    private Validator validator;
    private SignUpPresenter mPresenter;

    @Order(value = 1)
    @NotEmpty(messageResId = R.string.error_mobile_required)
    @Length(min = 11, max = 11, messageResId = R.string.error_invalid_mobile, sequence = 0)
    @Bind(R.id.et_mobile)
    EditText mMobile;

    @Order(value = 2)
    @NotEmpty(messageResId = R.string.error_verify_required)
    @Length(min = 6, max = 6, messageResId = R.string.error_verify_required, sequence = 0)
    @Bind(R.id.et_verify)
    EditText mVerify;

    @Order(value = 3)
    @NotEmpty(messageResId = R.string.error_name_required)
    @Length(min = 0, max = 12, messageResId = R.string.error_invalid_name, sequence = 0)
    @Bind(R.id.et_name)
    EditText mName;

    @Order(value = 4)
    @NotEmpty(messageResId = R.string.error_password_required)
    @Password(min = 6, messageResId = R.string.error_invalid_password, sequence = 0)
    @Bind(R.id.et_password)
    EditText mPassword;

    @Bind(R.id.txt_alert)
    TextView mAlert;

    @OnClick(R.id.txt_btn_sendVerify)
    void OnSendVerify() {

        int messageRes = 0;

        if (TextUtils.isEmpty(mMobile.getText())) {
            messageRes = R.string.error_mobile_required;
        } else if (mMobile.getText().length() != 11) {
            messageRes = R.string.error_invalid_mobile;
        }

        if (messageRes != 0) {
            mAlert.setText(messageRes);
        } else {
            ToastUtils.showToast(this, "123456");
        }
    }

    @OnClick(R.id.txt_btn_signup)
    void OnSignUp() {
        validator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        validator = new Validator(this);
        validator.setValidationListener(this);

        mPresenter = new SignUpPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onValidationSucceeded() {
        mAlert.setText(null);
        checkVerify(mVerify.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        mAlert.setText(null);
        ValidationError error = errors.get(0);
        String message = error.getFailedRules().get(0).getMessage(this);
        mAlert.setText(message);
    }

    private void checkVerify(String verify) {
        String phone = mMobile.getText().toString();
        String name = mName.getText().toString();
        String password = mPassword.getText().toString();

        if (verify.equals("123456")) {
            mPresenter.signUp(phone, name, password);
//            AccountManager.signup(this, mMobile.getText().toString(),
//                    mPassword.getText().toString(), mName.getText().toString());
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
        } else {
            ToastUtils.showToast(this, "验证码不正确");
        }

    }

    @Override
    public void showView() {
        ToastUtils.showToast(this, "注册成功");
        AccountManager.signup(this, mMobile.getText().toString(),
                mPassword.getText().toString(), mName.getText().toString());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void destroyView() {
        finish();
    }
}
