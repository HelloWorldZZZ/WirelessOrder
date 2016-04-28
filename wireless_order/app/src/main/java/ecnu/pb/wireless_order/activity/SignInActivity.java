package ecnu.pb.wireless_order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.database.AccountManager;
import ecnu.pb.wireless_order.presenter.SignInPresenter;
import ecnu.pb.wireless_order.widget.ToastUtils;

public class SignInActivity extends AppCompatActivity implements Validator.ValidationListener, SignInPresenter.View{

    private Validator validator;
    private SignInPresenter mPresenter;

    @NotEmpty(messageResId = R.string.error_username)
    @Length(min = 0, max = 12, messageResId = R.string.error_invalid_name, sequence = 0)
    @Bind(R.id.et_mobile)
    EditText mMobile;

    @NotEmpty(messageResId = R.string.error_password_required)
    @Password(min = 6, messageResId = R.string.error_invalid_password, sequence = 0)
    @Bind(R.id.et_password)
    EditText mPassword;

    @OnClick(R.id.txt_btn_signin)
    void OnSignIn() {
        validator.validate();
    }

    @OnClick(R.id.txt_btn_signup)
    void OnSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        mPresenter = new SignInPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onValidationSucceeded() {
        mPresenter.signIn( mMobile.getText().toString(), mPassword.getText().toString());
//        ToastUtils.showToast(this, "登陆成功");
//        AccountManager.signin(this, mMobile.getText().toString(), mPassword.getText().toString());
//        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidationError error = errors.get(0);
        String message = error.getFailedRules().get(0).getMessage(this);
        ToastUtils.showToast(this, message);
    }

    @Override
    public void showView() {
        ToastUtils.showToast(this, "登陆成功");
        AccountManager.signin(this, mMobile.getText().toString(), mPassword.getText().toString());
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
