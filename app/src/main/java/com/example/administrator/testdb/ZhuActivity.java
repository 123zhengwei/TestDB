package com.example.administrator.testdb;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhuActivity extends AppCompatActivity {

    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.editText4)
    EditText editText4;
    @BindView(R.id.editText5)
    EditText editText5;
    @BindView(R.id.editText6)
    EditText editText6;
    @BindView(R.id.editText7)
    EditText editText7;
    @BindView(R.id.editText8)
    EditText editText8;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.activity_zhu)
    RelativeLayout activityZhu;
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";//验证密码是否有特殊符号或长度不满6位
    private SQLiteDatabase sdb;
    private Mysqlist mys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);
        ButterKnife.bind(this);
        mys = new Mysqlist(this, "user.db3", null, 1);//使用halper创建数据库
        sdb=mys.getWritableDatabase();
    }

    @OnClick(R.id.button3)
    public void onViewClicked() {
        //获得账号密码
        String userTel= editText3.getText().toString().trim();
        String userPass = editText4.getText().toString().trim();
        String userNikename = editText5.getText().toString().trim();
        String useAge  = editText6.getText().toString().trim();
        String userHight = editText7.getText().toString().trim();
        String userWeight = editText8.getText().toString().trim();
        if (userTel == null || "".equals(userTel) || userPass == null || "".equals(userPass)) {
            Toast.makeText(this, "账号与密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            String number = editText3.getText().toString();
            boolean judge = isMobile(number);
            String pa = editText4.getText().toString();
            boolean judge1 = isPassword(pa);
            if (judge == true && judge1 == true) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                sdb.execSQL("insert into users(userTel,userPass)values('"+userTel+"','"+userPass+"')");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);//启动跳转
            } else {
                Toast.makeText(this, "手机号码不法与密码不能有特殊符号", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    /**
     * 验证手机格式
     */

    public static boolean isMobile(String number) {
 /*
 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
 联通：130、131、132、152、155、156、185、186
 电信：133、153、180、189、（1349卫通）
 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
 */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

}